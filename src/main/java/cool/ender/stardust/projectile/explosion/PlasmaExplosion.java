package cool.ender.stardust.projectile.explosion;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.particle.ExplosionParticle;
import cool.ender.stardust.registry.ParticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
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

    public void addParticle(Vec3 location) {
        this.level.addParticle((ParticleOptions) ParticleRegistry.PLASMA_EXPLOSION_1.get(), location.x, location.y, location.z, 0, 0, 0);
    }

    @Override
    public void doDamage(Vec3 location) {
        super.doDamage(location);
        Explosion explosion = new Explosion(this.level, null, location.x, location.y, location.z, this.radius);
        explosion.explode();
        explosion.finalizeExplosion(false);
    }


    @Override
    public void doDamage(LivingEntity entity) {
        super.doDamage(entity);
    }
}
