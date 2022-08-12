package stardust.stardust.registry;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import stardust.stardust.entity.CarrierDemoEntity;
import stardust.stardust.entity.projectile.HEProjectileEntity;
import stardust.stardust.entity.projectile.OPProjectileEntity;
import stardust.stardust.entity.projectile.SDProjectileEntity;

import static stardust.stardust.Stardust.MODID;

public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static RegistryObject<EntityType<CarrierDemoEntity>> CARRIER_DEMO_ENTITY = ENTITY_TYPE_DEFERRED_REGISTER.register(
            "carrier_demo_entity",
            () -> EntityType.Builder.create(
                    (EntityType<CarrierDemoEntity> entityType, World world) -> new CarrierDemoEntity(entityType, world),
                    EntityClassification.MISC
            ).build("carrier_demo_entity")
    );

    public static RegistryObject<EntityType<HEProjectileEntity>> HE_PROJECTILE_ENTITY = ENTITY_TYPE_DEFERRED_REGISTER.register(
            "he_projectile_entity",
            () -> EntityType.Builder.create(
                    (EntityType<HEProjectileEntity> entityType, World world) -> new HEProjectileEntity(entityType, world),
                    EntityClassification.MISC
            ).build("he_projectile_entity")
    );

    public static RegistryObject<EntityType<OPProjectileEntity>> OP_PROJECTILE_ENTITY = ENTITY_TYPE_DEFERRED_REGISTER.register(
            "op_projectile_entity",
            () -> EntityType.Builder.create(
                    (EntityType<OPProjectileEntity> entityType, World world) -> new OPProjectileEntity(entityType, world),
                    EntityClassification.MISC
            ).build("op_projectile_entity")
    );

    public static RegistryObject<EntityType<SDProjectileEntity>> SD_PROJECTILE_ENTITY = ENTITY_TYPE_DEFERRED_REGISTER.register(
            "sd_projectile_entity",
            () -> EntityType.Builder.create(
                    (EntityType<SDProjectileEntity> entityType, World world) -> new SDProjectileEntity(entityType, world),
                    EntityClassification.MISC
            ).build("sd_projectile_entity")
    );

    public static void registry() {
        ENTITY_TYPE_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
