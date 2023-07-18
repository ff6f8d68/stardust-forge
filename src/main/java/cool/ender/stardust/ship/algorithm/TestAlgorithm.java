package cool.ender.stardust.ship.algorithm;

import cool.ender.stardust.component.thruster.Thruster;
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;

import java.util.List;

public class TestAlgorithm extends ControlAlgorithm{


    public TestAlgorithm() {
    }
    @Override
    public void setThruster(List<Thruster.Tile> thrusters) {

    }

    @Override
    public void disconnectThruster(Thruster.Tile thruster) {

    }

    @Override
    public void connectThruster(Thruster.Tile thruster) {

    }

    public void setPhysShip(PhysShipImpl physShip) {
        this.physShip = physShip;
    }

    @Override
    public double getThrustingPercentage(Thruster.Tile tile) {
        return 0.1d;
    }


}
