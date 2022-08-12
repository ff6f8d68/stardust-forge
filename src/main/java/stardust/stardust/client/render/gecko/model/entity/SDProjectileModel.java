package stardust.stardust.client.render.gecko.model.entity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import stardust.stardust.entity.projectile.SDProjectileEntity;

import static stardust.stardust.Stardust.MODID;

public class SDProjectileModel extends AnimatedGeoModel<SDProjectileEntity> {
    @Override
    public ResourceLocation getModelLocation(SDProjectileEntity object) {
        return new ResourceLocation(MODID, "geo/sd_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SDProjectileEntity object) {
        return new ResourceLocation(MODID, "textures/entity/sd_projectile.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SDProjectileEntity animatable) {
        return null;
    }
}
