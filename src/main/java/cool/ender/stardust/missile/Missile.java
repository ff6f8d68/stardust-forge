package cool.ender.stardust.missile;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.EntityRegistry;
import cool.ender.stardust.turret.AbstractTurret;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Missile {
    public static class Entity extends AbstractHurtingProjectile implements IAnimatable {
        int age = 0;
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Entity(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
            super(p_36833_, p_36834_);
        }

        @Override
        public void addAdditionalSaveData(CompoundTag tag) {
            tag.putInt("age", age);
            super.addAdditionalSaveData(tag);
        }

        @Override
        public void readAdditionalSaveData(CompoundTag tag) {
            super.readAdditionalSaveData(tag);
            age = tag.getInt("age");
        }

        public Entity(double x0, double y0, double z0, Level level) {
            this(EntityRegistry.MISSILE_ENTITY.get(), level);
            this.moveTo(x0, y0, z0, 0, 0);
            this.reapplyPosition();
            this.xPower = 0;
            this.yPower = 0.5;
            this.zPower = 0;

        }

        @Override
        public void tick() {
            super.tick();

            if (this.getLife() >= this.age) {
                this.remove(RemovalReason.DISCARDED);
                return;
            }

            if (!this.level.isClientSide) {
                this.age++;
                if (this.age < 20) {
                    this.yPower = this.yPower - 0.05;
                } else if (this.age == 20) {
                    this.yPower = 0.1;
                } else {
                    Vec3 vec0 = this.getEyePosition();
                    Vec3 vec1 = this.getTargetPos();
                    Vec3 vec2 = this.getDeltaMovement();
                    Vec3 vec3 = vec2.normalize().subtract(vec1.subtract(vec0).normalize());
                    this.xPower = vec3.x / 10;
                    this.yPower = vec3.y / 10;
                    this.zPower = vec3.z / 10;
                }
            }
        }

        public Vec3 getTargetPos() {
            return new Vec3(0, 0, 0);
        }

        @Override
        protected ParticleOptions getTrailParticle() {
            return this.age <= 10 ? ParticleTypes.ASH : ParticleTypes.DRIPPING_LAVA;
        }

        public int getLife() {
            return 100;
        }

        @Override
        public void registerControllers(AnimationData data) {

        }

        @Override
        public boolean isOnFire() {
            return false;
        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }


    public static class Model extends AnimatedGeoModel<Entity> {

        @Override
        public ResourceLocation getModelLocation(Entity object) {
            return new ResourceLocation(Stardust.MOD_ID, "geo/hell_bringer_bw.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(Entity object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/entity/hell_bringer_biological_warfare_missile.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(Entity animatable) {
            return null;
        }

        @Override
        public void setCustomAnimations(Entity animatable, int instanceId, AnimationEvent animationEvent) {
            super.setCustomAnimations(animatable, instanceId, animationEvent);
            this.getAnimationProcessor().getBone("hell_bringer_nuclear").setRotationZ((float) Math.PI / 2);
        }
    }

    public static class Renderer extends GeoProjectilesRenderer<Entity> {
        public Renderer(EntityRendererProvider.Context renderManager) {
            super(renderManager, new Model());
        }
    }
}
