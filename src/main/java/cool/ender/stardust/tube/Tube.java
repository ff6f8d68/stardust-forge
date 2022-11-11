package cool.ender.stardust.tube;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Tube {

    public static class Block extends BaseEntityBlock{

        public Block() {
            super(Properties.of(Material.STONE).noOcclusion());
        }

        public BlockEntity newBlockEntity( @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            return new Tile(blockPos, blockState);
        }
    }

    public static class Tile extends BlockEntity implements IAnimatable {

        public AnimationFactory factory = GeckoLibUtil.createFactory(this);
        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.TUBE_TILE.get(), p_155229_, p_155230_);
        }

        public void registerControllers( AnimationData data) {

        }

        @Override
        public AnimationFactory getFactory () {
            return factory;
        }
    }

    public static class Model extends AnimatedGeoModel<Tile> {
        public ResourceLocation getModelLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "geo/tube_1_1.geo.json");
        }

        public ResourceLocation getTextureLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/block/tube_1_1.png");
        }

        public ResourceLocation getAnimationFileLocation(Tile animatable) {
            return null;
        }
    }

    public static class Renderer extends GeoBlockRenderer<Tile> {
        public Renderer(BlockEntityRendererProvider.Context rendererProvider) {
            super(rendererProvider, new Model());
        }
    }

    public abstract static class Listener  {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener  {
            @SubscribeEvent
            public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
                event.registerBlockEntityRenderer(TileRegistry.TUBE_TILE.get(), Renderer::new);
            }
        }
    }
    public String getRegisterName() {
        return "tube";
    }
}
