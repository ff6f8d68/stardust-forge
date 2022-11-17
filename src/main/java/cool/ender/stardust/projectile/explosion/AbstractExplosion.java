package cool.ender.stardust.projectile.explosion;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

abstract public class AbstractExplosion extends Explosion {

    public AbstractExplosion(Level p_151471_, @Nullable Entity p_151472_, double p_151473_, double p_151474_, double p_151475_, float p_151476_) {
        super(p_151471_, p_151472_, p_151473_, p_151474_, p_151475_, p_151476_);
    }
}
