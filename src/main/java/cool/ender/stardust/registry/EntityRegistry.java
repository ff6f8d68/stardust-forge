package cool.ender.stardust.registry;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.projectile.AbstractProjectile;
import cool.ender.stardust.projectile.PlasmaProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Stardust.MOD_ID);
    public static final RegistryObject<EntityType<PlasmaProjectile.Entity>> PLASMA_PROJECTILE_ENTITY = ENTITIES.register("plasma_projectile_entity", () -> EntityType.Builder.of((EntityType<PlasmaProjectile.Entity> entityType, Level level) -> new PlasmaProjectile.Entity(entityType, level), MobCategory.CREATURE).build("plasma_projectile_entity"));

}
