package cool.ender.stardust.sandbox;

public abstract class SandboxEvent {
    /**
     * get the code to be executed in sandbox
     * */
    public abstract String getCode();

    /**
     * the max executing time limit to execute this event's code.
     * @return a time interval in unix timestamp format.
     * */
    public Long getMaxTime() {
        return 49L;
    }
}
