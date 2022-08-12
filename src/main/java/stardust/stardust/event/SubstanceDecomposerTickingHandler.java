package stardust.stardust.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftGame;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import stardust.stardust.Stardust;
import stardust.stardust.explosion.SubstanceDecomposing;

import java.util.HashMap;
import java.util.Iterator;

@Mod.EventBusSubscriber
public class SubstanceDecomposerTickingHandler {

    public static final Integer DEFAULT_DELAY_TICKS = 10;

    public static HashMap<SubstanceDecomposing, Integer> SUBSTANCE_DECOMPOSING_TASKS = new HashMap<>();

    public static void addTask(SubstanceDecomposing substanceDecomposing) {
        SUBSTANCE_DECOMPOSING_TASKS.put(substanceDecomposing, DEFAULT_DELAY_TICKS);
    }

    public static void removeTask(SubstanceDecomposing substanceDecomposing) {
        SUBSTANCE_DECOMPOSING_TASKS.remove(substanceDecomposing);
    }

    @SubscribeEvent
    public static void onTicking(TickEvent.ServerTickEvent event) {
        Iterator<SubstanceDecomposing> iterator = SUBSTANCE_DECOMPOSING_TASKS.keySet().iterator();
        while (iterator.hasNext()) {
            SubstanceDecomposing substanceDecomposing = iterator.next();
            if (SUBSTANCE_DECOMPOSING_TASKS.get(substanceDecomposing) <= 0) {
                SUBSTANCE_DECOMPOSING_TASKS.put(substanceDecomposing, DEFAULT_DELAY_TICKS);
                substanceDecomposing.decompose();
                if (substanceDecomposing.isTerminated) {
                    iterator.remove();
                    removeTask(substanceDecomposing);
                }
            } else {
                SUBSTANCE_DECOMPOSING_TASKS.put(substanceDecomposing, SUBSTANCE_DECOMPOSING_TASKS.get(substanceDecomposing) - 1);
            }
        }
    }
}
