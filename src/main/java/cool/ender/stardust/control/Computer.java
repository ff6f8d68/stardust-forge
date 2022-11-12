package cool.ender.stardust.control;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class Computer {
    public static class Block extends net.minecraft.world.level.block.Block {
        public Block(Properties p_49795_) {
            super(p_49795_);
        }
    }

    public static class Tile extends BlockEntity implements IAnimatable {
        public Tile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
            super(p_155228_, p_155229_, p_155230_);
        }

        @Override
        public void registerControllers(AnimationData data) {

        }

        @Override
        public AnimationFactory getFactory() {
            return null;
        }
    }

    public static class Renderer extends GeoBlockRenderer<Tile> {
        public Renderer(BlockEntityRendererProvider.Context rendererProvider, AnimatedGeoModel<Tile> modelProvider) {
            super(rendererProvider, modelProvider);
        }
    }

    public static class Model extends AnimatedGeoModel<Tile> {

        @Override
        public ResourceLocation getModelLocation(Tile object) {
            return null;
        }

        @Override
        public ResourceLocation getTextureLocation(Tile object) {
            return null;
        }

        @Override
        public ResourceLocation getAnimationFileLocation(Tile animatable) {
            return null;
        }
    }
}
