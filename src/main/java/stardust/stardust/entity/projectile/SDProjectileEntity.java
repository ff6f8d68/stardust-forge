package stardust.stardust.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import stardust.stardust.explosion.SubstanceDecomposing;
import stardust.stardust.registry.EntityTypeRegistry;

import javax.annotation.Nonnull;

public class SDProjectileEntity extends AbstractStardustProjectileEntity{
    public SDProjectileEntity(EntityType<? extends AbstractStardustProjectileEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.projectileType = ProjectileType.ENERGY_SUBSTANCE_DECOMPOSER;
    }

    public SDProjectileEntity(World worldIn, long energy, float attribute, TileEntity shootTile, double startX, double startY, double startZ, double accelerationX, double accelerationY, double accelerationZ) {
        super(EntityTypeRegistry.HE_PROJECTILE_ENTITY.get(), worldIn, ProjectileType.KINETIC_HIGHLY_EXPLOSIVE, energy, attribute, shootTile, startX, startY, startZ, accelerationX, accelerationY, accelerationZ);
        this.projectileType = ProjectileType.KINETIC_HIGHLY_EXPLOSIVE;
    }


    @Override
    protected void onEntityHit(@Nonnull EntityRayTraceResult result) {
        if (result.getEntity().isLiving()) {
            LivingEntity targetEntity = (LivingEntity) result.getEntity();
            targetEntity.remove();
        }
        super.onEntityHit(result);
    }

    @Override
    protected void func_230299_a_(@Nonnull BlockRayTraceResult result) {
        if (!this.world.isRemote()) {
            this.remove();
            new SubstanceDecomposing(this.world, result.getPos(), this.energy).decompose();
        }

        super.func_230299_a_(result);
    }
}
