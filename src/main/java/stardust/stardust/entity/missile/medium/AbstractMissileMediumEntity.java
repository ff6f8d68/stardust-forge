package stardust.stardust.entity.missile.medium;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.world.World;
import stardust.stardust.entity.missile.AbstractMissileEntity;

public class AbstractMissileMediumEntity extends AbstractMissileEntity {
    protected AbstractMissileMediumEntity(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }
}
