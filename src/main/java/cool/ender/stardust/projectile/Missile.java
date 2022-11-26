package cool.ender.stardust.projectile;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.EntityRegistry;
import cool.ender.stardust.turret.AbstractTurret;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Missile {
    public static class Entity extends AbstractHurtingProjectile implements IAnimatable {
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);
        public Entity(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
            super(p_36833_, p_36834_);
        }


        public Entity(double x0, double y0, double z0, double x1, double y1, double z1, Level level) {
            super(EntityRegistry.MISSILE_ENTITY.get(), x0, y0, z0, x1, y1, z1, level);
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        protected ParticleOptions getTrailParticle() {
            return ParticleTypes.SOUL_FIRE_FLAME;
        }

        @Override
        public void setDeltaMovement(Vec3 p_20257_) {

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
    }

    public static class Renderer extends GeoProjectilesRenderer<Entity> {
        public Renderer(EntityRendererProvider.Context renderManager) {
            super(renderManager, new Model());
        }
    }
}
