package cool.ender.stardust.ship;

import cool.ender.stardust.ship.algorithm.ControlAlgorithm;
import cool.ender.stardust.ship.algorithm.TestAlgorithm;
import cool.ender.stardust.thruster.Thruster;
import net.minecraft.core.BlockPos;
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
import java.util.HashMap;
import java.util.HashSet;

public class StardustShipControl implements ShipForcesInducer, ServerShipUser, Ticked {

    ServerShip ship;

    ControlAlgorithm algorithm;

    public HashSet<Thruster.Tile> thrusters = new HashSet<>();

    public StardustShipControl(ServerShip ship) {
        this.ship = ship;
        this.algorithm = new TestAlgorithm();
    }

    public static StardustShipControl getOrCreate(ServerShip ship) {
        StardustShipControl control = ship.getAttachment(StardustShipControl.class);
        if (control != null) return control;
        control = new StardustShipControl(ship);
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
        if (!this.algorithm.isPhysShipExists()) {
            algorithm.setPhysShip((PhysShipImpl) physShip);
        }
        if (algorithm != null) {
            for (Thruster.Tile tile : thrusters) {
                BlockPos blockPos = tile.getBlockPos();
                physShip.applyInvariantForceToPos(tile.getMaxForceVec().mul(algorithm.getThrustingPercentage(tile)), new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
            }
        }
    }

    @Override
    public void tick() {
    }
}
