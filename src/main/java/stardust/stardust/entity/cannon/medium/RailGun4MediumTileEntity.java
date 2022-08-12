package stardust.stardust.entity.cannon.medium;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import stardust.stardust.entity.projectile.HEProjectileEntity;
import stardust.stardust.entity.projectile.SDProjectileEntity;

public class RailGun4MediumTileEntity extends AbstractCannonMediumTileEntity {
    @Override
    public void shoot() {
        World world = this.getWorld();
        assert world != null;
        if (this.isInCD()) return;
        resetCD();
        if (!world.isRemote()) {
            Vector3d barrelRootPos = getBarrelEndPos();
            double x0 = barrelRootPos.getX();
            double y0 = barrelRootPos.getY();
            double z0 = barrelRootPos.getZ();

            Vector3d barrelDirection = this.getBarrelDirection();

            double x1 = barrelDirection.getX();
            double y1 = barrelDirection.getY();
            double z1 = barrelDirection.getZ();

//            RailGunProjectileEntity projectile = new HEProjectileEntity(this.world, 1000, 10.0f, this, x0, y0, z0, x1, y1, z1);
//            HEProjectileEntity projectile = new HEProjectileEntity(this.world, this.getEnergy(), 10.0f, this, x0, y0, z0, x1, y1, z1);
            SDProjectileEntity projectile = new SDProjectileEntity(this.world, this.getEnergy(), 100.0f, this, x0, y0, z0, x1, y1, z1);
            projectile.setRawPosition(x0, y0, z0);
            world.addEntity(projectile);
        }

        super.shoot();
    }
}
