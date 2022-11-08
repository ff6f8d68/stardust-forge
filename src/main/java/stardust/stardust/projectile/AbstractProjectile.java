package stardust.stardust.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import stardust.stardust.projectile.explosion.AbstractExplosion;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

abstract public class AbstractProjectile {

    @Nonnull
    abstract AbstractExplosion getExplosion();

    public abstract class Entity extends DamagingProjectileEntity {

        public enum ProjectileType {
            ENERGY_LASER, ENERGY_PLASMA, ENERGY_SUBSTANCE_DECOMPOSER, KINETIC_ARMOR_PIERCING, KINETIC_HIGHLY_EXPLOSIVE, KINETIC_BULLET, KINETIC_ORDNANCE_PENETRATOR
        }

        public double accelerationX = 0.0d;
        public double accelerationY = 0.0d;
        public double accelerationZ = 0.0d;

        public BlockPos shooterPos;

        public long shieldDamage;
        public long energy;
        public float attribute;
        public ProjectileType projectileType;


        public Entity(EntityType<? extends Entity> entityTypeIn, World worldIn) {
            super(entityTypeIn, worldIn);
        }

        public Entity(EntityType<? extends Entity> entityTypeIn, World worldIn, ProjectileType projectileType, long energy, float attribute, TileEntity shootTile, double startX, double startY, double startZ, double accelerationX, double accelerationY, double accelerationZ) {
            super(entityTypeIn, startX, startY, startZ, accelerationX, accelerationY, accelerationZ, worldIn);
            this.projectileType = projectileType;
            this.shooterPos = shootTile.getPos();
            this.addVelocity(accelerationX, accelerationY, accelerationZ);
            this.energy = energy;
            this.attribute = attribute;
        }

        @Override
        @ParametersAreNonnullByDefault
        protected void onImpact(RayTraceResult result) {
            super.onImpact(result);
        }

        @Override
        protected void onEntityHit(@Nonnull EntityRayTraceResult result) {
            super.onEntityHit(result);
        }

        /**
         * OnBlockHit()
         */
        @Override
        protected void func_230299_a_(@Nonnull BlockRayTraceResult result) {
            super.func_230299_a_(result);
        }

        public TileEntity getShooterTile() {
            return this.world.getTileEntity(shooterPos);
        }

        @Override
        public boolean isBurning() {
            return false;
        }

        public long getEnergy() {
            return energy;
        }

        public float getAttribute() {
            return attribute;
        }

        @Override
        public void tick() {
            super.tick();
        }

        @Override
        protected void registerData() {
        }

        @Override
        @ParametersAreNonnullByDefault
        public void readAdditional(CompoundNBT compound) {
        }

        @Override
        @ParametersAreNonnullByDefault
        public void writeAdditional(CompoundNBT compound) {
        }


        @Override
        public @Nonnull IPacket<?> createSpawnPacket() {
            return NetworkHooks.getEntitySpawningPacket(this);
        }
    }

    abstract public class Model {

    }

    abstract public class Renderer {

    }

}
