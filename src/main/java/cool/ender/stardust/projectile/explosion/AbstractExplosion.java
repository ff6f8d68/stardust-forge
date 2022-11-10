package cool.ender.stardust.projectile.explosion;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

abstract public class AbstractExplosion {

    public float attribute;
    public float radius;
    public float energy;

    public Level level;


    public AbstractExplosion(float attribute, float radius, float energy, Level level) {
        this.attribute = attribute;
        this.radius = radius;
        this.energy = energy;
        this.level = level;
    }


    public void playSound(Vec3 location) {

    }

    /**
     * Damage block
     */
    public void doDamage(Vec3 location) {
        playSound(location);
    }

    /**
     * Damage entity
     */
    public void doDamage(LivingEntity entity) {
//        playSound(entity.getOnPos());
    }
}
