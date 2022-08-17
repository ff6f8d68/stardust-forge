package stardust.stardust.client.render.gecko.model.block;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import stardust.stardust.Stardust;
import stardust.stardust.entity.base.CannonBaseMediumTileEntity;

public class CannonBaseMediumModel extends AnimatedGeoModel<CannonBaseMediumTileEntity> {
    @Override
    public ResourceLocation getModelLocation(CannonBaseMediumTileEntity object) {
        return new ResourceLocation(Stardust.MODID, "geo/cannon_base_medium.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CannonBaseMediumTileEntity object) {
        return new ResourceLocation(Stardust.MODID, "textures/block/cannon_base_medium.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CannonBaseMediumTileEntity animatable) {
        return null;
    }
}
