package cool.ender.stardust.ship.algorithm;

import cool.ender.stardust.thruster.Thruster;
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;

import java.util.List;

public abstract class ControlAlgorithm {
    public PhysShipImpl physShip;

    public abstract void setThruster(List<Thruster.Tile> thrusters);

    public abstract void disconnectThruster(Thruster.Tile thruster);

    public abstract void connectThruster(Thruster.Tile thruster);

    public abstract double getThrustingPercentage(Thruster.Tile tile);

    public boolean isPhysShipExists() {
        return this.physShip != null;
    }

    public void setPhysShip(PhysShipImpl physShip) {
        this.physShip = physShip;
    }
}
