package cool.ender.stardust.control;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.component.tube.ITubeConnectable;
import cool.ender.stardust.component.turret.AbstractTurret;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Objects;
import java.util.Random;

public class Computer {
    public static class Block extends BaseEntityBlock implements ITubeConnectable {

        public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
        public static final BooleanProperty OPEN = BooleanProperty.create("open");

        public Block() {
            super(Properties.of(Material.GLASS).noOcclusion());
            this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.SOUTH).setValue(OPEN, false));
        }

        @NotNull
        public RenderShape getRenderShape(@NotNull BlockState state) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }

        @Override
        public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
            if (level.isClientSide) {
                ((Tile) Objects.requireNonNull(level.getBlockEntity(blockPos))).switching = true;
                return InteractionResult.SUCCESS;
            } else {
                level.scheduleTick(blockPos, this,1);
                return InteractionResult.CONSUME;
            }
        }


        @Override
        public void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, Random p_60465_) {
            super.tick(blockState, level, blockPos, p_60465_);
            level.setBlock(blockPos, blockState.setValue(Block.OPEN, !blockState.getValue(OPEN)), 2);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_48725_) {
            p_48725_.add(FACING, OPEN);
        }

        @Override
        public BlockState rotate(BlockState p_48722_, Rotation p_48723_) {
            return p_48722_.setValue(FACING, p_48723_.rotate(p_48722_.getValue(FACING)));
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
            return this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection().getOpposite());
        }

        @Override
        public VoxelShape getShape(BlockState blockState, BlockGetter p_52808_, BlockPos p_52809_, CollisionContext p_52810_) {

            switch (blockState.getValue(FACING)) {
                case EAST -> {
                    return box(0.0D, 0.0D, 2.0D, 14.0D, 16.0D, 16.0D);
                }
                case WEST -> {
                    return box(2.0D, 0.0D, 2.0D, 16.0D, 16.0D, 16.0D);
                }

                case SOUTH -> {
                    return box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 14.0D);
                }
                case NORTH -> {
                    return box(0.0D, 0.0D, 2.0D, 16.0D, 16.0D, 16.0D);
                }
            }

            return box(0.0D, 0.0D, 2.0D, 16.0D, 16.0D, 16.0D);
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            return new Computer.Tile(blockPos, blockState);
        }

        @Override
        public boolean isConnectable(Direction direction, BlockState self) {
            return true;
        }
    }

    public static class Tile extends BlockEntity implements IAnimatable {

        boolean switching = false;
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Tile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
            super(p_155228_, p_155229_, p_155230_);
        }

        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            this(TileRegistry.COMPUTER.get(), p_155229_, p_155230_);
        }

        @Override
        public void registerControllers(AnimationData data) {
            data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
        }

        private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
            AnimationController<E> controller = event.getController();
            if (switching) {
                if (this.getBlockState().getValue(Block.OPEN)) {
                    controller.setAnimation(new AnimationBuilder().addAnimation("close", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
                    controller.transitionLengthTicks = 0;
                } else {
                    controller.setAnimation(new AnimationBuilder().addAnimation("open", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
                    controller.transitionLengthTicks = 10;
                }
                this.switching = false;
            } else {
                if (this.getBlockState().getValue(Block.OPEN)) {
                    //keep opening
                    controller.setAnimation(new AnimationBuilder().addAnimation("keep", ILoopType.EDefaultLoopTypes.LOOP));
                    controller.transitionLengthTicks = 10;
                }
            }
            return PlayState.CONTINUE;
        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }

    public static class Renderer extends GeoBlockRenderer<Tile> {
        public Renderer(BlockEntityRendererProvider.Context rendererProvider) {
            super(rendererProvider, new Model());
        }
    }

    public static class Model extends AnimatedGeoModel<Tile> {

        @Override
        public ResourceLocation getModelLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "geo/console.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/block/console.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(Tile animatable) {
            return new ResourceLocation(Stardust.MOD_ID, "animations/console_open_and_close.json");
        }
    }

    public abstract static class Listener extends AbstractTurret.Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener extends AbstractTurret.Listener.ClientListener {
            @SubscribeEvent
            public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
                event.registerBlockEntityRenderer(TileRegistry.COMPUTER.get(), Computer.Renderer::new);
            }
        }
    }
}
