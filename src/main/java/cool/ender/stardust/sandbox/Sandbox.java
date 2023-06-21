package cool.ender.stardust.sandbox;

import java.util.PriorityQueue;

public class Sandbox {
    public PriorityQueue<Sandbox> events = new PriorityQueue<>();

    /**
     * tick in sandbox, synced with minecraft event tick loop.
     * */
    public void tick() {
    }

//    public static SecurityManager getSecurityManager() {
//        return new SecurityManager();
//    }
}
