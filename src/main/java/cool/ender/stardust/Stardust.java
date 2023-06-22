package cool.ender.stardust;

import cool.ender.stardust.registry.*;
import cool.ender.stardust.sandbox.SandboxManager;
import cool.ender.stardust.world.dimension.EarthOuterSpaceDimension;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
    public static CreativeModeTab stardustCreativeGroup;
    public Stardust() {
        LOGGER.info("Stardust Mod Loaded");

        MinecraftForge.EVENT_BUS.register(this);

        stardustCreativeGroup = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "stardust_items") {
            @Override
            public ItemStack makeIcon() {
                return new ItemStack(Items.TNT);
            }
        };

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockRegistry.BLOCKS.register(bus);
        TileRegistry.TILES.register(bus);
        EntityRegistry.ENTITIES.register(bus);
        ItemRegistry.ITEMS.register(bus);
        SoundRegistry.SOUND_EVENTS.register(bus);
        ParticleRegistry.PARTICLE_TYPES.register(bus);
        EarthOuterSpaceDimension.register();
        MinecraftForge.EVENT_BUS.register(SandboxManager.manager);
    }

}
