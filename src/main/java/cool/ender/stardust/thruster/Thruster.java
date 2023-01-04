package cool.ender.stardust.thruster;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.tube.Tube;
import cool.ender.stardust.tube.TubeConnectable;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Thruster {
    public static class Block extends BaseEntityBlock {

        public Block() {
            super(Properties.of(Material.STONE).noOcclusion());
        }
        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {

            return new Tile(blockPos, blockState);
        }
    }
    public static class Tile extends BlockEntity implements IAnimatable {
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);
        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.THRUSTER_TILE.get(), p_155229_, p_155230_);
        }
        @Override
        public void registerControllers(AnimationData data) {

        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }
    public static class Model extends AnimatedGeoModel<Tile> {

        @Override
        public ResourceLocation getModelLocation(Tile object) {

            return new ResourceLocation(Stardust.MOD_ID, "geo/thruster.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/block/thruster_on.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(Tile animatable) {
            return null;
        }
    }
    public static class Renderer extends GeoBlockRenderer<Thruster.Tile> {
        public Renderer ( BlockEntityRendererProvider.Context rendererProvider ) {
            super(rendererProvider, new Thruster.Model());
        }
    }
    public abstract static class Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener {
            @SubscribeEvent
            public static void registerRenderers ( final EntityRenderersEvent.RegisterRenderers event ) {
                event.registerBlockEntityRenderer(TileRegistry.THRUSTER_TILE.get(), Thruster.Renderer::new);
            }
        }
    }
}
