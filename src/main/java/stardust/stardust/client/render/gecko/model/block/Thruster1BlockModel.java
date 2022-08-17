package stardust.stardust.client.render.gecko.model.block;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import stardust.stardust.Stardust;
import stardust.stardust.entity.thruster.Thruster1TileEnity;

public class Thruster1BlockModel extends AnimatedGeoModel<Thruster1TileEnity> {
    @Override
    public ResourceLocation getModelLocation(Thruster1TileEnity object) {
        return new ResourceLocation(Stardust.MODID,"geo/thruster_1.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Thruster1TileEnity object) {
        return new ResourceLocation(Stardust.MODID,"textures/block/thruster_1_block_on.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Thruster1TileEnity animatable) {
        return null;
    }
}
