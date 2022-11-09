package cool.ender.stardust.projectile;

import cool.ender.stardust.projectile.explosion.AbstractExplosion;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderTooltipEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class PlasmaProjectile extends AbstractProjectile{

//    public static class Factory implements IParticleFactory<BasicParticleType> {
//        private final RenderTooltipEvent.Color color;
//        public Factory(RenderTooltipEvent.Color color) {
//            this.color = color;
//        }
//
//        @Nullable
//        @Override
//        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
//            return new SHParticle(worldIn, this.color, x, y, z, xSpeed, ySpeed, zSpeed);
//        }
//    }

    @Override
    public AbstractExplosion getExplosion() {
        return null;
    }

    public abstract class Entity extends AbstractProjectile.Entity {
        public Entity(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
            super(p_36833_, p_36834_);
        }

        @Override
        public void registerControllers(AnimationData data) {

        }

        @Override
        public AnimationFactory getFactory() {
            return null;
        }
    }
}
