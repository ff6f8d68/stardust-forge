package cool.ender.stardust.projectile.explosion;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlasmaExplosion extends AbstractExplosion{

    public PlasmaExplosion(float attribute, float radius, float energy, Level level) {
        super(attribute, radius, energy, level);
    }

    @Override
    public void playSound(Vec3 location) {
        this.level.playLocalSound(location.x, location.y, location.z, SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.HOSTILE, this.radius, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    @Override
    public void doDamage(Vec3 location) {
        super.doDamage(location);
        this.level.explode(null, location.x, location.y, location.z, this.radius, false, Explosion.BlockInteraction.BREAK);
        AreaEffectCloud cloud = new AreaEffectCloud(this.level, location.x, location.y, location.z);
        cloud.setParticle(ParticleTypes.SOUL_FIRE_FLAME);
        cloud.setRadius(radius);
        cloud.setDuration(20);
        cloud.setRadiusPerTick((7.0F - cloud.getRadius()) / (float)cloud.getDuration());
        this.level.addFreshEntity(cloud);
    }


    @Override
    public void doDamage(LivingEntity entity) {
        super.doDamage(entity);
    }
}
