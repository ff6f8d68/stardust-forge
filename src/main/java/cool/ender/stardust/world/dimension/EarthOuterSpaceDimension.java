package cool.ender.stardust.world.dimension;

import cool.ender.stardust.Stardust;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;


public class EarthOuterSpaceDimension {
    public static final ResourceKey<Level> EARTH_OUTER_SPACE_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(Stardust.MOD_ID, "earth_outer_space"));
    public static final ResourceKey<DimensionType> EARTH_OUTER_SPACE_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, EARTH_OUTER_SPACE_KEY.getRegistryName());

    public static void register() {
        Stardust.LOGGER.info("Registering earth outer space dimension.");
    }

}
