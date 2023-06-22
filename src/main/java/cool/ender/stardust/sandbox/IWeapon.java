package cool.ender.stardust.sandbox;

public interface IWeapon extends IScriptControllable{
    public boolean charge();

    public boolean fire();

    public int getAmmo();

}
