package cool.ender.stardust.sandbox;

import java.util.List;

public interface IScriptControllable {
    public String getTypeName();
    public String getUniqueId();
    public List<String> getCommandList();

    public boolean sendCommand(String command, String data);

    public boolean isConnected();

    public boolean isDisabled();

}
