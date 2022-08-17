package stardust.stardust.client.render.gecko.model.block;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import stardust.stardust.Stardust;
import stardust.stardust.entity.base.CannonBaseMediumTileEntity;
import stardust.stardust.entity.cannon.medium.AbstractCannonMediumTileEntity;

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

    @Override
    public void setLivingAnimations(CannonBaseMediumTileEntity entity, Integer uniqueID) {
        super.setLivingAnimations(entity, uniqueID);
        this.getAnimationProcessor().getBone("bone").setRotationX(-(float) Math.PI / 2);
//        this.getAnimationProcessor().getBone("barrel_moving_up_down").setRotationZ((float) Math.toRadians(entity.nowRotationX));
    }
}
