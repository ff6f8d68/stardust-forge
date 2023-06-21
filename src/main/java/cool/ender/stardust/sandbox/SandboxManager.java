package cool.ender.stardust.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SandboxManager {

    public static final SandboxManager manager = new SandboxManager();

    ConcurrentHashMap<String, Sandbox> sandboxes = new ConcurrentHashMap<>();

    public SandboxManager() {
    }

    public void wake() {
        for (Sandbox sandbox : sandboxes.values()) {
            if (sandbox.isTickAble()) {
                sandbox.executor.interrupt();
            }
        }
    }
}
