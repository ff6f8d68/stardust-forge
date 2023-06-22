package cool.ender.stardust.sandbox;

import com.google.common.collect.ConcurrentHashMultiset;
import cool.ender.stardust.control.Helm;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SandboxManager {

    public static final SandboxManager manager = new SandboxManager();

    ConcurrentHashMap<Helm.Tile, Sandbox> sandboxes = new ConcurrentHashMap<>();
    HashSet<Sandbox> mutedSandboxes = new HashSet<>();

    public SandboxManager() {
    }

    public void add(Helm.Tile tile, Sandbox sandbox) {
        sandboxes.put(tile, sandbox);
    }

    public void mute(Sandbox sandbox) {
        this.mutedSandboxes.add(sandbox);
    }

    public void unmute(Sandbox sandbox) {
        this.mutedSandboxes.remove(sandbox);
    }

    public void wake() {
        for (Sandbox sandbox : sandboxes.values()) {
            if (mutedSandboxes.contains(sandbox) && sandbox.isTickAble()) {
                sandbox.executor.interrupt();
            }
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        this.wake();
    }
}
