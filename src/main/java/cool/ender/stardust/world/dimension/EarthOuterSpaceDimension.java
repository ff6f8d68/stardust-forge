package cool.ender.stardust.world.dimension;

import cool.ender.stardust.Stardust;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionDefaults;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EarthOuterSpaceDimension {
    public static final ResourceKey<Level> EARTH_OUTER_SPACE_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(Stardust.MOD_ID, "earth_outer_space"));
    public static final ResourceKey<DimensionType> EARTH_OUTER_SPACE_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, EARTH_OUTER_SPACE_KEY.getRegistryName());


    @SubscribeEvent
    public static void onEntityAboveKarman(EntityEvent.Size event) {
        ServerLevel level = ServerLifecycleHooks.getCurrentServer().getLevel(EARTH_OUTER_SPACE_KEY);
        assert level != null;
        if (event.getEntity().getY() > 100000) {
            event.getEntity().changeDimension(Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer().getLevel(EARTH_OUTER_SPACE_KEY)));
            level.getProfiler().push("portal");
            event.getEntity().changeDimension(level);
            level.getProfiler().pop();
        }
    }

    public static void register() {
        Stardust.LOGGER.info("Registering earth outer space dimension.");
    }


}
