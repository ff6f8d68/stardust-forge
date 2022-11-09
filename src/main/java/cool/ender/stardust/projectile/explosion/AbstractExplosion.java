package cool.ender.stardust.projectile.explosion;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

abstract public class AbstractExplosion {

    public float attribute;
    public float radius;
    public float energy;


    public AbstractExplosion(float attribute, float radius, float energy) {
        this.attribute = attribute;
        this.radius = radius;
        this.energy = energy;
    }

    /**
     * Damage block
     * */
    abstract void doDamage(BlockPos pos);

    /**
     * Damage entity
     * */
    abstract void doDamage(LivingEntity entity);
}
