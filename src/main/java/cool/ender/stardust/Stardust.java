package cool.ender.stardust;

import cool.ender.stardust.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("stardust")
public class Stardust {

    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "stardust";

    public Stardust() {
        LOGGER.info("Stardust Loaded");

        MinecraftForge.EVENT_BUS.register(this);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockRegistry.BLOCKS.register(bus);
        TileRegistry.TILES.register(bus);
        EntityRegistry.ENTITIES.register(bus);
        ItemRegistry.ITEMS.register(bus);
        SoundRegistry.SOUND_EVENTS.register(bus);
    }

}
