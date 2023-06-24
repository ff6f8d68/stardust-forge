package cool.ender.stardust.thruster;

import com.mojang.blaze3d.vertex.PoseStack;
import cool.ender.stardust.Stardust;
import cool.ender.stardust.control.Helm;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.tube.ITubeConnectable;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3i;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Thruster {
    public static class Block extends BaseEntityBlock implements ITubeConnectable {
        public static final DirectionProperty FACING = DirectionalBlock.FACING;

        public Block() {
            super(Properties.of(Material.STONE).noOcclusion());
            this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.SOUTH));
        }
        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {

            return new Tile(blockPos, blockState);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_48725_) {
            p_48725_.add(FACING);
        }
//        @Override
//        public BlockState rotate(BlockState p_48722_, Rotation p_48723_) {
//            return p_48722_.setValue(FACING, p_48723_.rotate(p_48722_.getValue(FACING)));
//        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
            return this.defaultBlockState().setValue(FACING, p_48689_.getNearestLookingDirection().getOpposite());
        }

        @Override
        public boolean isConnectable(Direction direction, BlockState self) {
            return !self.getValue(FACING).equals(direction);
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

        public Vector3d getMaxForceVec() {
            Vec3i vec = this.getBlockState().getValue(Block.FACING).getOpposite().getNormal().multiply(100000);
            return new Vector3d(vec.getX(), vec.getY(), vec.getZ());
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
