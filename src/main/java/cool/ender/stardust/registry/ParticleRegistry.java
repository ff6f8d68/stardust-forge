package cool.ender.stardust.registry;

import cool.ender.stardust.Stardust;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Stardust.MOD_ID);
    public static final RegistryObject<ParticleType<SimpleParticleType>> PLASMA_EXPLOSION_1 = PARTICLE_TYPES.register("plasma_explosion_1", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> PLASMA_EXPLOSION_2 = PARTICLE_TYPES.register("plasma_explosion_2", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> PLASMA_EXPLOSION_3 = PARTICLE_TYPES.register("plasma_explosion_3", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> PLASMA_EXPLOSION_4 = PARTICLE_TYPES.register("plasma_explosion_4", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> PLASMA_EXPLOSION_5 = PARTICLE_TYPES.register("plasma_explosion_5", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> PLASMA_EXPLOSION_6 = PARTICLE_TYPES.register("plasma_explosion_6", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> PLASMA_EXPLOSION = PARTICLE_TYPES.register("plasma_explosion", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> PLASMA_EXPLOSION_TEST = PARTICLE_TYPES.register("lightspark", () -> new SimpleParticleType(true));

}
