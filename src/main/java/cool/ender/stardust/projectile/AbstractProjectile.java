package cool.ender.stardust.projectile;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.projectile.explosion.AbstractExplosion;
import cool.ender.stardust.registry.EntityRegistry;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.turret.small.RailGun1Small;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;


abstract public class AbstractProjectile {

    public abstract static class Entity extends AbstractHurtingProjectile implements IAnimatable {
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);
        public Entity(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
            super(p_36833_, p_36834_);

        }

        public enum ProjectileType {
            ENERGY_LASER, ENERGY_PLASMA, ENERGY_SUBSTANCE_DECOMPOSER, KINETIC_ARMOR_PIERCING, KINETIC_HIGHLY_EXPLOSIVE, KINETIC_BULLET, KINETIC_ORDNANCE_PENETRATOR
        }

        abstract AbstractExplosion getExplosion();


        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }

    public abstract static class Model extends AnimatedGeoModel<Entity>{

    }

    public abstract static class Renderer extends GeoProjectilesRenderer<Entity> {

        public Renderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<Entity> modelProvider) {
            super(renderManager, modelProvider);
        }
    }

    public abstract static class Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener {

        }

        public abstract static class ServerListener {

        }
    }
}
