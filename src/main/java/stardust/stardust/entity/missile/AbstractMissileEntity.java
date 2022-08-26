package stardust.stardust.entity.missile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.world.World;

public class AbstractMissileEntity extends DamagingProjectileEntity {
    protected AbstractMissileEntity(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

}
