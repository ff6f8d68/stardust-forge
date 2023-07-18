package cool.ender.stardust.component.seat;

import cool.ender.stardust.control.Computer;
import cool.ender.stardust.registry.TileRegistry;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;
import org.valkyrienskies.mod.common.entity.ShipMountingEntity;

import java.util.Objects;

public class FighterSeat {
    public static class Block extends BaseEntityBlock {

        public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

        public Block() {
            super(Properties.of(Material.WOOL).noOcclusion());
            this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.SOUTH));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new FighterSeat.Tile(blockPos, blockState);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_48725_) {
            p_48725_.add(FACING);
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext p_48689_) {
            return this.defaultBlockState().setValue(FACING, p_48689_.getHorizontalDirection().getOpposite());
        }

        @Override
        public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult result) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                Tile tile = (Tile) level.getBlockEntity(blockPos);
                assert tile != null;
                tile.sit(player, false);
                return InteractionResult.CONSUME;
            }
        }
    }

    public static class Tile extends BlockEntity {

        ShipMountingEntity seat;

        public Tile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
            super(p_155228_, p_155229_, p_155230_);
        }

        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.FIGHTER_SEAT.get(), p_155229_, p_155230_);
        }

        public void sit(Player player, Boolean force) {
            player.startRiding(getSeat(), force);
        }

        public ShipMountingEntity getSeat() {
            if (this.seat == null) {
                this.seat = ValkyrienSkiesMod.SHIP_MOUNTING_ENTITY_TYPE.create(Objects.requireNonNull(this.getLevel()));
                Vec3 sitPos = new Vec3(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5);
                this.seat.moveTo(sitPos);
                Vec3i facingVec = this.getBlockState().getValue(Block.FACING).getNormal();
                this.seat.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(facingVec.getX(), facingVec.getY(), facingVec.getZ()).add(this.seat.position()));
            }
            return this.seat;
        }

    }
}
