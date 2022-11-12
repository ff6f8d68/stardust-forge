package cool.ender.stardust.projectile;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.projectile.explosion.AbstractExplosion;
import cool.ender.stardust.projectile.explosion.PlasmaExplosion;
import cool.ender.stardust.registry.EntityRegistry;
import cool.ender.stardust.registry.ParticleRegistry;
import cool.ender.stardust.registry.SoundRegistry;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationData;

public class PlasmaProjectile extends AbstractProjectile {

    public static class Entity extends AbstractProjectile.Entity {
        int age = 0;

        public Entity(EntityType<? extends AbstractProjectile.Entity> entityType, Level level) {
            super(entityType, level);
            setNoGravity(true);
        }

        public Entity(LivingEntity livingEntity, double x, double y, double z, Level level) {
            super(EntityRegistry.PLASMA_PROJECTILE_ENTITY.get(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), x, y, z, level);
            this.setOwner(livingEntity);
            this.setRot(livingEntity.getYRot(), livingEntity.getXRot());
        }

        public Entity(double x, double y, double z, double to_x, double to_y, double to_z, Level level) {

            this(EntityRegistry.PLASMA_PROJECTILE_ENTITY.get(), level);
            this.moveTo(x, y, z, this.getYRot(), this.getXRot());
            this.reapplyPosition();
            double d0 = Math.sqrt(to_x * to_x + to_y * to_y + to_z * to_z);
            if (d0 != 0.0D) {
                this.xPower = to_x / d0;
                this.yPower = to_y / d0;
                this.zPower = to_z / d0;
            }

        }

        @Override
        public void registerControllers(AnimationData data) {

        }

        @Override
        protected ParticleOptions getTrailParticle() {
            return ParticleTypes.SOUL_FIRE_FLAME;
        }

        @Override
        public void tick() {
            super.tick();
            if (!this.level.isClientSide()) {
                if (++age > 40) {
                    Stardust.LOGGER.info("removed");
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }

        @Override
        protected void onHit(HitResult p_37260_) {

            if (!this.level.isClientSide()) {
                ((ServerLevel) this.level).sendParticles((ParticleOptions) ParticleRegistry.LIGHT_SPARK.get(), p_37260_.getLocation().x, p_37260_.getLocation().y, p_37260_.getLocation().z, 1, 0, 0, 0, 0);
                level.playSound(null, p_37260_.getLocation().x, p_37260_.getLocation().y, p_37260_.getLocation().z, SoundRegistry.PLASMA_EXPLOSION.get(), SoundSource.BLOCKS, 2.0f, 1.0f);
                this.getExplosion().doDamage(p_37260_.getLocation());
                this.remove(RemovalReason.DISCARDED);
            }
            super.onHit(p_37260_);
        }

        @Override
        AbstractExplosion getExplosion() {
            return new PlasmaExplosion(0f, 5f, 100f, this.level);
        }
    }

    public static class Model extends AbstractProjectile.Model {

        @Override
        public ResourceLocation getModelLocation(AbstractProjectile.Entity object) {
            return null;
        }

        @Override
        public ResourceLocation getTextureLocation(AbstractProjectile.Entity object) {
            return null;
        }

        @Override
        public ResourceLocation getAnimationFileLocation(AbstractProjectile.Entity animatable) {
            return null;
        }
    }

    public static class Renderer extends AbstractProjectile.Renderer {

        public Renderer(EntityRendererProvider.Context renderManager) {
            super(renderManager, null);
        }

        @Override
        public boolean shouldRender(AbstractProjectile.@NotNull Entity p_114491_, @NotNull Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
            return false;
        }
    }

    public static class Listener extends AbstractProjectile.Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public static class ClientListener extends AbstractProjectile.Listener.ClientListener {
            @SubscribeEvent
            public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
                event.registerEntityRenderer(EntityRegistry.PLASMA_PROJECTILE_ENTITY.get(), Renderer::new);

            }
        }
    }
}
