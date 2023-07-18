package cool.ender.stardust.sandbox;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.PriorityQueue;

public class Sandbox {
    public PriorityQueue<SandboxEvent> events = new PriorityQueue<>();

    public Executor executor = new Executor();

    public final ScriptSystem scriptSystem = new ScriptSystem();

    public Sandbox() {
        this.executor.start();
    }

    /**
     * tick in sandbox, synced with minecraft event tick loop.
     * */
    public void tick() {
        while (!events.isEmpty()) {
            try {
                scriptSystem.execute(events.poll().getCode());
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        }
        updateConnectivity();
        pollCommands();
    }

    /**
     * update block connectivity data
     * */
    private void updateConnectivity() {
    }

    /**
     * Read commands from script sandbox and execute them using api provided by tiles.
     * */
    private void pollCommands() {
    }

    public void addEvent(SandboxEvent event) {
        this.events.add(event);
    }

    public boolean isTickAble() {
        return true;
    }


//    public static SecurityManager getSecurityManager() {
//        return new SecurityManager();
//    }

    static class Executor extends Thread {
        Sandbox sandbox;
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.sandbox.tick();
            }
        }
    }
}
