package cool.ender.stardust.missile.launcher;

import com.google.common.collect.ImmutableList;
import cool.ender.stardust.Stardust;
import cool.ender.stardust.client.gui.VerticalLauncherScreen;
import cool.ender.stardust.missile.Missile;
import cool.ender.stardust.registry.BlockRegistry;
import cool.ender.stardust.registry.SoundRegistry;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.tube.TubeConnectable;
import cool.ender.stardust.turret.AbstractTurret;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
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

import java.util.*;

public class VerticalMissileLauncher {
    public enum ControlMode {
        PROGRAMMABLE, TELEVISION, COORDINATE, LASER, RADAR;

        public TranslatableComponent getComponent() {
            switch (this) {
                case PROGRAMMABLE -> {
                    return new TranslatableComponent("gui.stardust.vertical_launcher.control_mode.programmable");
                }
                case TELEVISION -> {
                    return new TranslatableComponent("gui.stardust.vertical_launcher.control_mode.television");
                }
                case COORDINATE -> {
                    return new TranslatableComponent("gui.stardust.vertical_launcher.control_mode.coordinate");
                }

                case RADAR -> {
                    return new TranslatableComponent("gui.stardust.vertical_launcher.control_mode.radar");
                }
                case LASER -> {
                    return new TranslatableComponent("gui.stardust.vertical_launcher.control_mode.laser");
                }
            }
            return null;
        }

        public ControlMode getNext() {
            switch (this) {
                case PROGRAMMABLE -> {
                    return TELEVISION;
                }
                case TELEVISION -> {
                    return COORDINATE;
                }
                case COORDINATE -> {
                    return LASER;
                }
                case LASER -> {
                    return RADAR;
                }
                case RADAR -> {
                    return PROGRAMMABLE;
                }

            }
            return null;
        }

        public static final ImmutableList<ControlMode> CONTROL_MODES = ImmutableList.of(PROGRAMMABLE, TELEVISION, COORDINATE, LASER, RADAR);

    }

    public static class Block extends BaseEntityBlock implements TubeConnectable {

        @Override
        public boolean getConnectable(Direction direction) {
            return true;
        }

        enum ShapeType implements StringRepresentable {
            NO_COLLISION, TOP, BOTTOM, FULL;

            @Override
            public @NotNull String getSerializedName() {
                return this.toString().toLowerCase();
            }
        }

        public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");
        public static final BooleanProperty CENTERED = BooleanProperty.create("centered");
        public static final EnumProperty<ShapeType> SHAPE_TYPE = new EnumProperty<>("shape_type", ShapeType.class, List.of(ShapeType.values()));
        public static final BooleanProperty OPEN = BooleanProperty.create("open");

        public static final BooleanProperty IDLE = BooleanProperty.create("idle");

        public Block() {
            super(Properties.of(Material.METAL).emissiveRendering((BlockState p_61036_, BlockGetter p_61037_, BlockPos p_61038_) -> true));
            this.registerDefaultState(this.stateDefinition.any().setValue(ASSEMBLED, false).setValue(CENTERED, false).setValue(SHAPE_TYPE, ShapeType.FULL).setValue(OPEN, false).setValue(IDLE, false));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new Tile(blockPos, blockState);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_52719_) {
            p_52719_.add(ASSEMBLED).add(CENTERED).add(SHAPE_TYPE).add(IDLE).add(OPEN);
        }

        @Override
        public void neighborChanged(BlockState blockState, Level level, BlockPos selfBlock, net.minecraft.world.level.block.Block block, BlockPos neighborBlock, boolean p_60514_) {
            if (level.hasNeighborSignal(selfBlock)) {
                Tile tile = (Tile) level.getBlockEntity(selfBlock);
                if (tile != null) {
                    Tile centerTile = tile.getCenterTile();
                    if (centerTile != null) {
                        centerTile.shoot();
                    }
                }
            }
        }

        @Override
        public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
            if (!blockState.getValue(ASSEMBLED)) {
                return super.use(blockState, level, blockPos, player, hand, result);
            }
            Tile tile = (Tile) level.getBlockEntity(blockPos);
            if (level.isClientSide) {
                if (tile != null) {
                    Tile centerTile = tile.getCenterTile();
                    if (centerTile != null) {
                        Minecraft.getInstance().setScreen(new VerticalLauncherScreen(new TranslatableComponent("gui.stardust.vertical_launcher"), centerTile));
                    }
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }

        @Override
        public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
            return false;
        }

        @Override
        public RenderShape getRenderShape(BlockState blockState) {
            return blockState.getValue(ASSEMBLED) ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
        }

        @Override
        public boolean hasDynamicShape() {
            return true;
        }

        @Override
        public VoxelShape getShape(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
            if (blockState.getBlock() == BlockRegistry.VERTICAL_MISSILE_LAUNCHER_BLOCK.get()) {
                if (blockState.getValue(ASSEMBLED)) {
                    if (blockState.getValue(SHAPE_TYPE) == ShapeType.FULL) {
                        return box(0, 0, 0, 16, 16, 16);
                    }

                    if (blockState.getValue(SHAPE_TYPE) == ShapeType.TOP) {
                        BlockState centerState = blockGetter.getBlockState(blockPos.offset(0, -4, 0));
                        if (centerState.getBlock() == BlockRegistry.VERTICAL_MISSILE_LAUNCHER_BLOCK.get()) {
                            if (centerState.getValue(OPEN)) {
                                return box(0, 0, 0, 0, 0, 0);
                            } else {
                                return box(0, 8, 0, 16, 16, 16);
                            }
                        }
                    }
                    if (blockState.getValue(SHAPE_TYPE) == ShapeType.BOTTOM) {
                        return box(0, 0, 0, 16, 8, 16);
                    }

                    if (blockState.getValue(SHAPE_TYPE) == ShapeType.NO_COLLISION) {
                        return box(0, 0, 0, 0, 0, 0);
                    }
                }
            }
            return box(0, 0, 0, 16, 16, 16);
        }

        @Override
        public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState p_60569_, boolean p_60570_) {
            if (!level.isClientSide) {
                AssembleTask1 task1 = new AssembleTask1(blockPos, level);
                if (task1.fastVerify()) {
                    AssembleTask2 task2 = new AssembleTask2(task1.getCenterPos(), level);
                    if (task2.searchAndSet()) {
                        BlockState centerState = level.getBlockState(task1.getCenterPos());
                        level.setBlock(task1.getCenterPos(), centerState.setValue(CENTERED, true), 2);
                    }
                }
            }
            super.onPlace(blockState, level, blockPos, p_60569_, p_60570_);
        }
    }

    public static class Tile extends BlockEntity implements IAnimatable {

        BlockPos centerPos = null;
        long coolDownTick = 0;

        public ControlMode controlMode = ControlMode.COORDINATE;

        public boolean explodeOnDiscard = false;
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.VERTICAL_MISSILE_LAUNCHER_TILE.get(), p_155229_, p_155230_);
        }

        @Override
        protected void saveAdditional(@NotNull CompoundTag tag) {
            super.saveAdditional(tag);
            if (this.centerPos != null) {
                tag.putInt("center_x", centerPos.getX());
                tag.putInt("center_y", centerPos.getY());
                tag.putInt("center_z", centerPos.getZ());
            }
            tag.putBoolean("explode_on_discard", this.explodeOnDiscard);
            tag.putString("control_mode", this.controlMode.toString());
            tag.putLong("cd", this.coolDownTick);
        }

        @Override
        public void load(@NotNull CompoundTag tag) {
            super.load(tag);
            this.centerPos = new BlockPos(tag.getInt("center_x"), tag.getInt("center_y"), tag.getInt("center_z"));
            this.coolDownTick = tag.getLong("cd");
            this.controlMode = ControlMode.valueOf(tag.getString("control_mode"));
            this.explodeOnDiscard = tag.getBoolean("explode_on_discard");
        }

        @Override
        public CompoundTag getUpdateTag() {
            CompoundTag tag =  super.getUpdateTag();
            if (this.centerPos != null) {
                tag.putInt("center_x", centerPos.getX());
                tag.putInt("center_y", centerPos.getY());
                tag.putInt("center_z", centerPos.getZ());
            }
            tag.putBoolean("explode_on_discard", this.explodeOnDiscard);
            tag.putString("control_mode", this.controlMode.toString());
            return tag;
        }

        @Override
        public void handleUpdateTag(CompoundTag tag) {
            super.handleUpdateTag(tag);
            this.centerPos = new BlockPos(tag.getInt("center_x"), tag.getInt("center_y"), tag.getInt("center_z"));
            this.controlMode = ControlMode.valueOf(tag.getString("control_mode"));
            this.explodeOnDiscard = tag.getBoolean("explode_on_discard");
        }

        void shoot() {
            if (this.centerPos != null && this.centerPos.equals(this.getBlockPos()) && this.getBlockState().getValue(Block.OPEN) && this.getCoolDown() <= 0) {
                assert this.level != null;
                this.level.addFreshEntity(new Missile.Entity(this.centerPos.getX() + 0.5, this.centerPos.getY() + 1, this.centerPos.getZ() + 0.5, level));
            }
        }

        public Tile getCenterTile() {
            if (this.centerPos == null) return null;
            assert level != null;
            return (Tile) level.getBlockEntity(centerPos);
        }

        public void setCoolDown(int coolDown) {
            this.coolDownTick = this.level.getGameTime() + coolDown;
        }

        public long getCoolDown() {
            return coolDownTick - this.level.getGameTime();
        }

        @Override
        public AABB getRenderBoundingBox() {
            return new AABB(this.getBlockPos().offset(-1, 0, -1), this.getBlockPos().offset(1, 5, 1));
        }

        @Override
        public void setRemoved() {
            super.setRemoved();
            assert this.level != null;
            if (!this.level.isClientSide) {
                if (this.level.isLoaded(this.getBlockPos())) {
                    if (!(level.getBlockState(getBlockPos()).getBlock() instanceof Block)) {
                        if (this.centerPos != null) {
                            new DisassembleTask(this.centerPos, level).searchAndSet();
                        }
                    }
                }
            }
        }

        public void setCenterPos(BlockPos blockPos) {
            this.centerPos = blockPos;
        }

        @Override
        public void registerControllers(AnimationData data) {
            data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
        }

        private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
            AnimationController<E> controller = event.getController();
            if (this.getBlockState().getValue(Block.IDLE)) return PlayState.CONTINUE;
            if (this.getBlockState().getValue(Block.OPEN)) {
                controller.setAnimation(new AnimationBuilder().addAnimation("open", ILoopType.EDefaultLoopTypes.PLAY_ONCE).addAnimation("keep", ILoopType.EDefaultLoopTypes.LOOP));
            } else {
                controller.setAnimation(new AnimationBuilder().addAnimation("close", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
                this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(Block.IDLE, true), 2);
            }
            return PlayState.CONTINUE;
        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }

        public void switchState() {
            level.playSound(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), SoundRegistry.MISSILE_DOOR.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            if (this.getBlockState().getValue(Block.OPEN)) {
                this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(Block.OPEN, false), 2);
                this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(Block.IDLE, false), 2);
            } else {
                this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(Block.OPEN, true), 2);
                this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(Block.IDLE, false), 2);
            }
        }

        public void switchControlMode() {
            this.controlMode = this.controlMode.getNext();
            assert level != null;
            level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState());
        }

        public void switchExplodeOption() {
            this.explodeOnDiscard = !this.explodeOnDiscard;
            assert level != null;
            level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState());
        }
    }

    public static class Renderer extends GeoBlockRenderer<Tile> {
        public Renderer(BlockEntityRendererProvider.Context rendererProvider) {
            super(rendererProvider, new Model());
        }

        @Override
        public boolean shouldRender(BlockEntity blockEntity, @NotNull Vec3 vec3) {
            BlockState blockstate = blockEntity.getBlockState();
            return blockstate.getValue(Block.CENTERED);
        }

        @Override
        public boolean shouldRenderOffScreen(BlockEntity p_112306_) {
            return true;
        }
    }

    public static class Model extends AnimatedGeoModel<Tile> {
        @Override
        public ResourceLocation getModelLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "geo/vertical_missile_launcher.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/block/vertical_missile_launcher.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(Tile animatable) {
            return new ResourceLocation(Stardust.MOD_ID, "animations/vertical_missile_launcher.json");
        }

        @Override
        public void setCustomAnimations(Tile animatable, int instanceId) {
            super.setCustomAnimations(animatable, instanceId);
        }
    }

    public static class AssembleTask1 {
        Level level;
        BlockPos placePos;
        int north = 0;//-Z
        int south = 0;//+Z
        int west = 0;//-X
        int east = 0;//+X
        int up = 0;//+Y
        int down = 0;//-Y

        public AssembleTask1(BlockPos placePos, Level level) {
            this.level = level;
            this.placePos = placePos;
        }

        public boolean fastVerify() {

            //north & south
            BlockPos temPos = placePos.north();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED)) {
                north++;
                temPos = temPos.north();
            }
            temPos = placePos.south();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED) && north + south < 3) {
                south++;
                temPos = temPos.south();
            }

            if (south + north < 2) {
                return false;
            }

            //east & west
            temPos = placePos.east();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED)) {
                east++;
                temPos = temPos.east();
            }
            temPos = placePos.west();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED) && east + west < 3) {
                west++;
                temPos = temPos.west();
            }
            if (east + west < 2) {
                return false;
            }

            //up & down
            temPos = placePos.above();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED)) {
                up++;
                temPos = temPos.above();
            }
            temPos = placePos.below();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED) && up + down < 5) {
                down++;
                temPos = temPos.below();
            }
            return up + down >= 4;
        }

        public BlockPos getCenterPos() {
            return placePos.offset(east - 1, -down, south - 1);
        }
    }

    public static class AssembleTask2 {

        BlockPos centerPos;
        Level level;

        public AssembleTask2(BlockPos centerPos, Level level) {
            this.centerPos = centerPos;
            this.level = level;
        }

        public boolean searchAndSet() {
            for (int x = centerPos.getX() - 1; x <= centerPos.getX() + 1; x++) {
                for (int y = centerPos.getY(); y <= centerPos.getY() + 4; y++) {
                    for (int z = centerPos.getZ() - 1; z <= centerPos.getZ() + 1; z++) {
                        BlockState current = level.getBlockState(new BlockPos(x, y, z));
                        if (!(current.getBlock() instanceof Block && !current.getValue(Block.ASSEMBLED))) {
                            return false;
                        }
                    }
                }
            }
            for (int x = centerPos.getX() - 1; x <= centerPos.getX() + 1; x++) {
                for (int y = centerPos.getY(); y <= centerPos.getY() + 4; y++) {
                    for (int z = centerPos.getZ() - 1; z <= centerPos.getZ() + 1; z++) {
                        BlockPos currentPos = new BlockPos(x, y, z);
                        BlockState currentState = level.getBlockState(currentPos);
                        Tile currentTile = (Tile) level.getBlockEntity(currentPos);
                        assert currentTile != null;
                        currentTile.setCenterPos(centerPos);
                        BlockState newState;
                        if (centerPos.equals(currentPos)) {
                            newState = currentState.setValue(Block.ASSEMBLED, true).setValue(Block.SHAPE_TYPE, Block.ShapeType.BOTTOM);
                        } else if (centerPos.offset(0, 1, 0).equals(currentPos)) {
                            newState = currentState.setValue(Block.ASSEMBLED, true).setValue(Block.SHAPE_TYPE, Block.ShapeType.NO_COLLISION);
                        } else if (centerPos.offset(0, 2, 0).equals(currentPos)) {
                            newState = currentState.setValue(Block.ASSEMBLED, true).setValue(Block.SHAPE_TYPE, Block.ShapeType.NO_COLLISION);
                        } else if (centerPos.offset(0, 3, 0).equals(currentPos)) {
                            newState = currentState.setValue(Block.ASSEMBLED, true).setValue(Block.SHAPE_TYPE, Block.ShapeType.NO_COLLISION);
                        } else if (centerPos.offset(0, 4, 0).equals(currentPos)) {
                            newState = currentState.setValue(Block.ASSEMBLED, true).setValue(Block.SHAPE_TYPE, Block.ShapeType.TOP);
                        } else {
                            newState = currentState.setValue(Block.ASSEMBLED, true).setValue(Block.SHAPE_TYPE, Block.ShapeType.FULL);
                        }
                        level.setBlockAndUpdate(currentPos, newState);
                    }
                }
            }
            return true;
        }
    }

    public static class DisassembleTask {
        BlockPos centerPos;
        Level level;

        public DisassembleTask(BlockPos centerPos, Level level) {
            this.centerPos = centerPos;
            this.level = level;
        }

        public boolean searchAndSet() {
            for (int x = centerPos.getX() - 1; x <= centerPos.getX() + 1; x++) {
                for (int y = centerPos.getY(); y <= centerPos.getY() + 4; y++) {
                    for (int z = centerPos.getZ() - 1; z <= centerPos.getZ() + 1; z++) {
                        BlockPos currentPos = new BlockPos(x, y, z);
                        BlockState currentState = level.getBlockState(currentPos);
                        if (currentState.getBlock() instanceof Block) {
                            level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 2);
                        }
                    }
                }
            }
            return true;
        }
    }

    public abstract static class Listener extends AbstractTurret.Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener extends AbstractTurret.Listener.ClientListener {
            @SubscribeEvent
            public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
                event.registerBlockEntityRenderer(TileRegistry.VERTICAL_MISSILE_LAUNCHER_TILE.get(), VerticalMissileLauncher.Renderer::new);
            }
        }
    }

}
