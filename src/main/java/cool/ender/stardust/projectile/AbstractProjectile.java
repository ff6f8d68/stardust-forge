package cool.ender.stardust.projectile;

import cool.ender.stardust.projectile.explosion.AbstractExplosion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;


abstract public class AbstractProjectile {

    abstract AbstractExplosion getExplosion();

    public abstract static class Entity extends AbstractHurtingProjectile implements IAnimatable {

        public Entity(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
            super(p_36833_, p_36834_);

        }

        public enum ProjectileType {
            ENERGY_LASER, ENERGY_PLASMA, ENERGY_SUBSTANCE_DECOMPOSER, KINETIC_ARMOR_PIERCING, KINETIC_HIGHLY_EXPLOSIVE, KINETIC_BULLET, KINETIC_ORDNANCE_PENETRATOR
        }
    }

    public abstract static class Model {

    }

    public abstract static class Renderer extends GeoProjectilesRenderer<Entity> {

        public Renderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<Entity> modelProvider) {
            super(renderManager, modelProvider);
        }
    }

}
