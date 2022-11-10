package cool.ender.stardust.projectile.explosion;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlasmaExplosion extends AbstractExplosion{

    public PlasmaExplosion(float attribute, float radius, float energy, Level level) {
        super(attribute, radius, energy, level);
    }

    @Override
    public void doDamage(Vec3 location) {
        super.doDamage(location);
        this.level.explode(null, location.x, location.y, location.z, this.radius, true, Explosion.BlockInteraction.BREAK);
    }


    @Override
    public void doDamage(LivingEntity entity) {
        super.doDamage(entity);
    }
}
