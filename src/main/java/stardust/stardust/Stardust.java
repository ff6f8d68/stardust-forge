package stardust.stardust;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stardust.stardust.event.CannonBaseBreakEventHandler;
import stardust.stardust.registry.BlockRegistry;
import stardust.stardust.registry.EntityTypeRegistry;
import stardust.stardust.registry.ItemRegistry;
import stardust.stardust.registry.TileEntityTypeRegistry;

import java.awt.*;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("stardust")
public class Stardust {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static String MODID = "stardust";

    public Stardust() {

        MinecraftForge.EVENT_BUS.register(this);

        BlockRegistry.register();

        ItemRegistry.register();

        TileEntityTypeRegistry.registry();

        EntityTypeRegistry.registry();

        MinecraftForge.EVENT_BUS.register(new CannonBaseBreakEventHandler());
        
    }

}
