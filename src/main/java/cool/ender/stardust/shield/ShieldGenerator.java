package cool.ender.stardust.shield;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ShieldGenerator {
    public static class Block extends BaseEntityBlock {
        public Block() {
            super(Properties.of(Material.METAL));
        }

        @Override
        public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                ((Tile) Objects.requireNonNull(level.getBlockEntity(blockPos))).scan();
                level.scheduleTick(blockPos, this,1);
                return InteractionResult.CONSUME;
            }
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new Tile(blockPos, blockState);
        }

        @Override
        public void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, Random random) {
            Stardust.LOGGER.info("tick triggered");

            super.tick(blockState, level, blockPos, random);
            Tile tile = (Tile) level.getBlockEntity(blockPos);
            assert tile != null;
            if (tile.scanningTask != null) {
                if (tile.scanningTask.scan()) {
                    if (!tile.scanningTask.failed) {
                        Stardust.LOGGER.info("success");
                    } else {
                        Stardust.LOGGER.info("fail");
                    }
                    tile.scanningTask = null;
                } else {
                    level.scheduleTick(blockPos, this,1);
                    Stardust.LOGGER.info("scan");
                }
            }
        }
    }

    public static class Tile extends BlockEntity {
        ShipScanningTask scanningTask;
        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.SHIELD_GENERATOR_TILE.get(), p_155229_, p_155230_);
        }

        public void scan() {
            assert this.level != null;
            if (!this.level.isClientSide) {
                if (scanningTask == null) {
                    this.scanningTask = new ShipScanningTask(this.getLevel(), this.getBlockPos());
                } else {
                    Stardust.LOGGER.warn("Try to start another shield ship scanning task while last haven't been processed");
                }
            }
        }


    }

    public static class ShipScanningTask {
        private static final int MAX_SHIP_BLOCK = 4096;
        Queue<BlockPos> queue = new LinkedList<>();
        HashSet<BlockPos> scannedPos = new HashSet<>();
        int blockCounted = 0;
        Level level;

        long max_x = Long.MIN_VALUE;
        long max_y = Long.MIN_VALUE;
        long max_z = Long.MIN_VALUE;

        long min_x = Long.MAX_VALUE;
        long min_y = Long.MAX_VALUE;
        long min_z = Long.MAX_VALUE;

        boolean failed = false;

        public ShipScanningTask(Level level, BlockPos start) {
            this.level = level;
            this.queue.add(start);
            this.scannedPos.add(start);
        }


        public boolean scan() {
            if (blockCounted == MAX_SHIP_BLOCK) {
                this.failed = true;
                return true;
            }
            if (blockCounted != 0 && queue.isEmpty()) {
                return true;
            } else {
                while (!queue.isEmpty()) {

                    Stardust.LOGGER.info("loop");
                    BlockPos pos = queue.poll();
                    //x
                    if (pos.getX() > this.max_x) {
                        this.max_x = pos.getX();
                    }
                    if (pos.getX() < this.min_x) {
                        this.min_x = pos.getX();
                    }
                    //y
                    if (pos.getY() > this.max_y) {
                        this.max_y = pos.getY();
                    }
                    if (pos.getY() < this.min_y) {
                        this.min_y = pos.getY();
                    }
                    //z
                    if (pos.getZ() > this.max_z) {
                        this.max_z = pos.getZ();
                    }
                    if (pos.getZ() < this.min_z) {
                        this.min_z = pos.getZ();
                    }

                    this.blockCounted++;
                    //six directions check
                    if (level.getBlockState(pos.above()).getBlock() != Blocks.AIR && !scannedPos.contains(pos.above())) {
                        this.scannedPos.add(pos.above());
                        queue.add(pos.above());
                    }
                    if (level.getBlockState(pos.below()).getBlock() != Blocks.AIR && !scannedPos.contains(pos.below())) {
                        this.scannedPos.add(pos.below());
                        queue.add(pos.below());
                    }
                    if (level.getBlockState(pos.north()).getBlock() != Blocks.AIR && !scannedPos.contains(pos.north())) {
                        this.scannedPos.add(pos.north());
                        queue.add(pos.north());
                    }
                    if (level.getBlockState(pos.south()).getBlock() != Blocks.AIR && !scannedPos.contains(pos.south())) {
                        this.scannedPos.add(pos.south());
                        queue.add(pos.south());
                    }
                    if (level.getBlockState(pos.east()).getBlock() != Blocks.AIR && !scannedPos.contains(pos.east())) {
                        this.scannedPos.add(pos.east());
                        queue.add(pos.east());
                    }
                    if (level.getBlockState(pos.west()).getBlock() != Blocks.AIR && !scannedPos.contains(pos.west())) {
                        this.scannedPos.add(pos.west());
                        queue.add(pos.west());
                    }
                }
            }
            return false;
        }
    }
}
