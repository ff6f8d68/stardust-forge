package stardust.stardust.client.render.gecko.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import stardust.stardust.client.render.gecko.model.entity.SDProjectileModel;
import stardust.stardust.entity.projectile.HEProjectileEntity;
import stardust.stardust.entity.projectile.SDProjectileEntity;

public class SDProjectileRenderer extends GeoProjectilesRenderer<SDProjectileEntity> {
    public SDProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SDProjectileModel());
    }
}
