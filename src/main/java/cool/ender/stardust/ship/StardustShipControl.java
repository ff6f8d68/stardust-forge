package cool.ender.stardust.ship;

import cool.ender.stardust.ship.algorithm.ControlAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.PhysShip;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.api.ServerShipUser;
import org.valkyrienskies.core.impl.api.ShipForcesInducer;
import org.valkyrienskies.core.impl.api.Ticked;

public class StardustShipControl implements ShipForcesInducer, ServerShipUser, Ticked {

    ServerShip ship;

    ControlAlgorithm algorithm;



    public StardustShipControl(ServerShip ship) {
        this.ship = ship;
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
    }

    @Override
    public void tick() {
    }
}
