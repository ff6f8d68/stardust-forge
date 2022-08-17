package stardust.stardust.client.render.gecko.model.block;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import stardust.stardust.Stardust;
import stardust.stardust.entity.console.ConsoleTileEntity;

public class ConsoleBlockModel extends AnimatedGeoModel <ConsoleTileEntity>{

    @Override
    public ResourceLocation getModelLocation(ConsoleTileEntity object) {
        return new ResourceLocation(Stardust.MODID,"geo/ship_computer_4_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ConsoleTileEntity object) {
        return new ResourceLocation(Stardust.MODID,"textures/block/ship_computer_4_block.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ConsoleTileEntity animatable) {
        return null;
    }
}
