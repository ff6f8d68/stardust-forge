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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Missile {
    public static class Entity extends Projectile implements IAnimatable {
        int age = 0;
        public double xPower;
        public double yPower;
        public double zPower;
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Entity(EntityType<? extends Projectile> p_36833_, Level p_36834_) {
            super(p_36833_, p_36834_);
        }

        @Override
        public void addAdditionalSaveData(CompoundTag tag) {
            tag.putInt("age", age);
            super.addAdditionalSaveData(tag);
        }

        @Override
        public void readAdditionalSaveData(@NotNull CompoundTag tag) {
            super.readAdditionalSaveData(tag);
            age = tag.getInt("age");
        }

        public Entity(double x0, double y0, double z0, Level level) {
            this(EntityRegistry.MISSILE_ENTITY.get(), level);
            this.moveTo(x0, y0, z0, 0, 0);
            this.reapplyPosition();
            this.setDeltaMovement(0, 3, 0);
            this.xPower = 0;
            this.yPower = -0.16;
            this.zPower = 0;
        }

        @Override
        protected void defineSynchedData() {

        }

        @Override
        public void tick() {

            if (!this.level.isClientSide) {
                // self-destruction
                this.age++;
                if (this.getLife() <= this.age) {
                    this.discard();
                }
                //custom movement

                if (this.age == 20) {
                    this.xPower = 0;
                    this.yPower = 0;
                    this.zPower = 0;
                }
                if (this.age > 20) {
//                    Vec3 vec_0 = this.getForward();
//                    Vec3 vec_1 = this.position();
//                    Vec3 vec_2 = this.getTargetPos();
//                    Vec3 vec_3 = vec_2.subtract(vec_1);
//                    Vec3 vec_4 = vec_3.subtract(vec_0);
//                    Vec3 vec_5 = vec_4.normalize();
//                    this.xPower += vec_5.x / 20;
//                    this.yPower += vec_5.y / 20;
//                    this.zPower += vec_5.z / 20;

                    float f = 1.0f;
                    Vec3 toward = this.getTargetPos().subtract(this.position()).normalize();
                    this.setDeltaMovement(toward.scale(f));
                }
            }
            net.minecraft.world.entity.Entity entity = this.getOwner();
            if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
                HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
                if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                    this.onHit(hitresult);
                }

                this.checkInsideBlocks();
                Vec3 vec3 = this.getDeltaMovement();
                double d0 = this.getX() + vec3.x;
                double d1 = this.getY() + vec3.y;
                double d2 = this.getZ() + vec3.z;
//                ProjectileUtil.rotateTowardsMovement(this, 1.0F);
                if (this.isInWater()) {
                    for (int i = 0; i < 4; ++i) {
                        this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25D, d1 - vec3.y * 0.25D, d2 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
                    }

                }

                this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower));
                this.setPos(d0, d1, d2);

            } else {
                this.discard();
            }


        }

        public Vec3 getTargetPos() {
            return new Vec3(0, 0, 0);
        }

        public int getLife() {
            return 100;
        }

        @Override
        public void registerControllers(AnimationData data) {

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
