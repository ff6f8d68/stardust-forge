package cool.ender.stardust.client;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.particle.ExplosionFrameParticle;
import cool.ender.stardust.particle.ExplosionParticle;
import cool.ender.stardust.particle.LightingParticle;
import cool.ender.stardust.projectile.Missile;
import cool.ender.stardust.projectile.PlasmaProjectile;
import cool.ender.stardust.registry.BlockRegistry;
import cool.ender.stardust.registry.EntityRegistry;
import cool.ender.stardust.registry.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.LIGHT_SPARK.get(), LightingParticle.Provider::new);
    }
    @SubscribeEvent
    public static void onRenderTypeSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SHIELD_BLOCK.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.PLASMA_PROJECTILE_ENTITY.get(), PlasmaProjectile.Renderer::new);
        event.registerEntityRenderer(EntityRegistry.MISSILE_ENTITY.get(), Missile.Renderer::new);
    }
}
