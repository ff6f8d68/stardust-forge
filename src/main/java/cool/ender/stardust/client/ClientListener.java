package cool.ender.stardust.client;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.particle.ExplosionFrameParticle;
import cool.ender.stardust.particle.ExplosionParticle;
import cool.ender.stardust.registry.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientListener {
    @SubscribeEvent
    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.PLASMA_EXPLOSION_1.get(), ExplosionFrameParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.PLASMA_EXPLOSION_2.get(), ExplosionFrameParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.PLASMA_EXPLOSION_3.get(), ExplosionFrameParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.PLASMA_EXPLOSION_4.get(), ExplosionFrameParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.PLASMA_EXPLOSION_5.get(), ExplosionFrameParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.PLASMA_EXPLOSION_6.get(), ExplosionFrameParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.PLASMA_EXPLOSION.get(), ExplosionParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.PLASMA_EXPLOSION_TEST.get(), ExplosionFrameParticle.Provider::new);
    }
}
