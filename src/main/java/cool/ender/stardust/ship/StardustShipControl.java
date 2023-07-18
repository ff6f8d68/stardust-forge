package cool.ender.stardust.ship;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import cool.ender.stardust.ship.algorithm.ControlAlgorithm;
import cool.ender.stardust.ship.algorithm.TestAlgorithm;
import cool.ender.stardust.component.thruster.Thruster;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.api.ServerShipUser;
import org.valkyrienskies.core.impl.api.ShipForcesInducer;
import org.valkyrienskies.core.impl.api.Ticked;
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;
import org.valkyrienskies.mod.common.util.VectorConversionsMCKt;

import java.util.HashSet;


@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StardustShipControl implements ShipForcesInducer, ServerShipUser, Ticked {

    @JsonIgnore
    ServerShip ship;



    @JsonIgnore
    /**
     * 记得给这玩意写序列化，然后改@JsonProperty("algorithm")
     * **/
    ControlAlgorithm algorithm;

    @JsonIgnore
    /**
     * 记得给这玩意写加载 建议开多个HashSet<BlockPos>来存，然后在applyForce里面判断这俩谁的的size大，大的push到小的里面，add的时候两个一起加
     * 这东西应该是没法反序列化的
     * **/
    public HashSet<Thruster.Tile> thrusters = new HashSet<>();

    public StardustShipControl() {
        this.algorithm = new TestAlgorithm();
    }

    public static StardustShipControl getOrCreate(ServerShip ship) {
        StardustShipControl control = ship.getAttachment(StardustShipControl.class);
        if (control != null) return control;
        control = new StardustShipControl();
        control.setShip(ship);
        ship.saveAttachment(StardustShipControl.class, control);
        return control;
    }

    public void setAlgorithm(ControlAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Nullable
    @Override
    public ServerShip getShip() {
        return this.ship;
    }

    @Override
    public void setShip(@Nullable ServerShip serverShip) {
        this.ship = serverShip;
    }

    @Override
    public void applyForces(@NotNull PhysShip physShip) {
        PhysShipImpl impl = (PhysShipImpl) physShip;
        if (!this.algorithm.isPhysShipExists()) {
            algorithm.setPhysShip(impl);
        }

        Vector3dc shipCoords = ship.getTransform().getPositionInShip();
        if (algorithm != null) {
            for (Thruster.Tile tile : thrusters) {
                BlockPos blockPos = tile.getBlockPos();
                physShip.applyInvariantForceToPos(
                        impl.getTransform().getShipToWorldRotation().transform(tile.getMaxForceVec().mul(algorithm.getThrustingPercentage(tile))),
                        VectorConversionsMCKt.toJOML(Vec3.atCenterOf(blockPos)).sub(shipCoords));
            }
        }
    }

    @Override
    public void tick() {
    }
}
