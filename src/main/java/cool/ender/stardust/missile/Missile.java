package cool.ender.stardust.missile;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.EntityRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
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
        protected void onHit(HitResult p_37260_) {
            super.onHit(p_37260_);

            if (!this.level.isClientSide) {
                this.end();
            }
        }

        @Override
        public void tick() {

            if (!this.level.isClientSide) {
                // self-destruction
                this.age++;
                if (this.getLife() <= this.age) {
                    this.end();
                }
                //custom movement
                //fire
                if (this.age == 20) {
                    this.xPower = 0;
                    this.yPower = 0;
                    this.zPower = 0;
                    this.setDeltaMovement(0, 1, 0);
                }
                //flight
                if (this.age > 20) {
                    // fetch normalized current and target speed vector
                    Vec3 currentSpeedVec = this.getDeltaMovement().normalize();
                    Vec3 targetSpeedVec = this.getTargetPos().subtract(this.position()).normalize();
                    // scale the rotation angle by angular velocity and velocity of the missile
                    double angle = Math.acos(currentSpeedVec.dot(targetSpeedVec) / currentSpeedVec.length() * targetSpeedVec.length());
                    Vec3 rotationAngleVec = targetSpeedVec.subtract(currentSpeedVec);
                    if (angle > this.getAngularVelocity()) {
                        rotationAngleVec = rotationAngleVec.scale(this.getAngularVelocity() / angle);
                    }
                    Vec3 setToVec = currentSpeedVec.add(rotationAngleVec).scale(this.getVelocity());

                    // apply new speed vec
                    this.setDeltaMovement(setToVec);
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
                ProjectileUtil.rotateTowardsMovement(this, 1.0F);

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

        public void explode() {

        }

        public void end() {
            this.explode();
            this.discard();
        }

        public Vec3 getTargetPos() {
            return new Vec3(0, 0, 0);
        }

        public int getLife() {
            return 100;
        }

        public double getVelocity() {
            return 3;
        }

        public double getAngularVelocity() {
            return Math.PI / 32;
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
//            this.getAnimationProcessor().getBone("hell_bringer_nuclear").setRotationY((float) Math.PI / 2);
        }
    }

    public static class Renderer extends GeoProjectilesRenderer<Entity> {
        public Renderer(EntityRendererProvider.Context renderManager) {
            super(renderManager, new Model());
        }
    }
}
