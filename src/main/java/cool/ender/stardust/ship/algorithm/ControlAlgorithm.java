package cool.ender.stardust.ship.algorithm;

import cool.ender.stardust.thruster.Thruster;

import java.util.List;

public abstract class ControlAlgorithm {

    public abstract void setThruster(List<Thruster.Tile> thrusters);

    public abstract void disconnectThruster(Thruster.Tile thruster);

    public abstract void connectThruster(Thruster.Tile thruster);

    public abstract double getThrustingPercentage(Thruster.Tile tile);

}
