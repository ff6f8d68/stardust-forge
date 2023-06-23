package cool.ender.stardust.control;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.BlockRegistry;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.sandbox.Sandbox;
import cool.ender.stardust.sandbox.SandboxManager;
import cool.ender.stardust.ship.StardustShipControl;
import cool.ender.stardust.thruster.Thruster;
import cool.ender.stardust.tube.ITubeConnectable;
import cool.ender.stardust.tube.TubeGraph;
import cool.ender.stardust.util.ShipAssembler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.world.ShipWorld;
import org.valkyrienskies.core.impl.datastructures.DenseBlockPosSet;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;
import org.valkyrienskies.mod.common.assembly.ShipAssemblyKt;
import org.valkyrienskies.mod.common.entity.ShipMountingEntity;
import software.bernie.example.block.tile.HabitatTileEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashSet;

public class Helm {
    public static class Block extends BaseEntityBlock implements ITubeConnectable {
        public static final DirectionProperty HORIZONTAL_FACING = HorizontalDirectionalBlock.FACING;
        public Block() {
            super(Properties.of(Material.STONE).noOcclusion());
            registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            return new Tile(blockPos, blockState);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_48725_) {
            p_48725_.add(HORIZONTAL_FACING);
        }
        @Override
        public BlockState rotate(BlockState p_48722_, Rotation p_48723_) {
            return p_48722_.setValue(HORIZONTAL_FACING, p_48723_.rotate(p_48722_.getValue(HORIZONTAL_FACING)));
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
            return this.defaultBlockState().setValue(HORIZONTAL_FACING, p_48689_.getHorizontalDirection().getOpposite());
        }

        @Override
        public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                Tile tile = (Tile) level.getBlockEntity(blockPos);
                if (tile != null) {
                    tile.assemble();
                }

                return InteractionResult.CONSUME;
            }
        }

        @Override
        public boolean isConnectable(Direction direction, BlockState self) {
            return true;
        }

        @Override
        public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }


    }

    public static class Tile extends BlockEntity implements IAnimatable {

        private Sandbox scriptSandbox = null;

        private TubeGraph connected;

        private ServerShip ship = null;

        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Tile(BlockEntityType<?> tile, BlockPos blockPos, BlockState blockState) {
            super(tile, blockPos, blockState);
        }

        public Tile(BlockPos blockPos, BlockState blockState) {
            this(TileRegistry.HELM.get(), blockPos, blockState);
        }

        /**
         * turn to script, this will change gui.
         */
        public void switchToScriptControl() {
            if (this.scriptSandbox == null) {
                this.scriptSandbox = new Sandbox();
                SandboxManager.manager.add(this, this.scriptSandbox);
            } else {
                SandboxManager.manager.unmute(this.scriptSandbox);
            }
        }

        public void switchToCommonControl() {
            SandboxManager.manager.mute(this.scriptSandbox);
        }

        public void openGui() {
        }

        public void assemble() {
            if (ship == null) {
                ShipAssembler assembler = new ShipAssembler(this.getBlockPos(), this.getLevel());
                this.ship = assembler.assemble();
                if (ship != null) {
                    StardustShipControl control = StardustShipControl.getOrCreate(ship);
                    HashSet<BlockEntity> devices = assembler.getDevices();
                    for (BlockEntity entity : devices) {
                        if (entity instanceof Thruster.Tile) {
                            control.thrusters.add((Thruster.Tile) entity);
                        }
                    }
                }
            }
        }

        @Override
        public void registerControllers(AnimationData data) {
        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }

    public static void spawnSitEntity(BlockPos blockPos, BlockState blockState, ServerLevel level) {
        ShipMountingEntity entity = ValkyrienSkiesMod.SHIP_MOUNTING_ENTITY_TYPE.create(level);
        assert entity != null;
        entity.moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        entity.setController(true);
        level.addFreshEntityWithPassengers(entity);
    }

    public static class Renderer extends GeoBlockRenderer<Tile> {
        public Renderer(BlockEntityRendererProvider.Context rendererProvider) {
            super(rendererProvider, new Model());
        }

        @Override
        public RenderType getRenderType(Tile animatable, float partialTick, PoseStack poseStack,
                                        MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight,
                                        ResourceLocation texture) {
            return RenderType.entityTranslucent(getTextureLocation(animatable));
        }

    }

    public static class Model extends AnimatedGeoModel<Tile> {
        @Override
        public ResourceLocation getModelLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "geo/ship_control_wheel_archimedes_space_tech.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/block/ship_control_wheel_archimedes_space_tech.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(Tile animatable) {
            return null;
        }
    }
}
