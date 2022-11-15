package cool.ender.stardust.shield;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
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
                level.scheduleTick(blockPos, this, 1);
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
            super.tick(blockState, level, blockPos, random);
            Tile tile = (Tile) level.getBlockEntity(blockPos);
            assert tile != null;
            if (tile.scanningTask != null) {
                if (tile.scanningTask.tick()) {
                    if (!tile.scanningTask.failed) {
                        Stardust.LOGGER.info("success");
                        Stardust.LOGGER.info("x:" + tile.scanningTask.max_x);
                        Stardust.LOGGER.info("y:" + tile.scanningTask.max_y);
                        Stardust.LOGGER.info("z:" + tile.scanningTask.max_z);

                        Stardust.LOGGER.info("x:" + tile.scanningTask.min_x);
                        Stardust.LOGGER.info("y:" + tile.scanningTask.min_y);
                        Stardust.LOGGER.info("z:" + tile.scanningTask.min_z);
                        tile.generatingTask = new ShieldGeneratingTask(tile.scanningTask.max_x, tile.scanningTask.max_y, tile.scanningTask.max_z, tile.scanningTask.min_x, tile.scanningTask.min_y, tile.scanningTask.min_z, 2, level);
                        tile.scanningTask = null;
                        level.scheduleTick(blockPos, this, 1);
                    } else {
                        Stardust.LOGGER.info("fail");
                    }
                    tile.scanningTask = null;
                } else {
                    level.scheduleTick(blockPos, this, 1);
                    Stardust.LOGGER.info("scan");
                }
            }
            if (tile.generatingTask != null) {
                if (!tile.generatingTask.tick()) {
                    level.scheduleTick(blockPos, this, 1);
                }

            }
        }
    }

    public static class Tile extends BlockEntity {
        ShipScanningTask scanningTask;
        ShieldGeneratingTask generatingTask;

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

    public static class ShieldGeneratingTask {
        BlockPos maxCornerBlock;
        BlockPos minCornerBlock;

        Level level;

        Queue<BlockPos> taskQueue = new LinkedList<>();
        HashSet<BlockPos> generatedPos = new HashSet<>();

        public ShieldGeneratingTask(long max_x, long max_y, long max_z, long min_x, long min_y, long min_z, long shieldOffset, Level level) {
            maxCornerBlock = new BlockPos(max_x + shieldOffset, max_y + shieldOffset, max_z + shieldOffset);
            minCornerBlock = new BlockPos(min_x - shieldOffset, min_y - shieldOffset, min_z - shieldOffset);
            this.taskQueue.add(maxCornerBlock);
            this.generatedPos.add(maxCornerBlock);
            this.level = level;
        }

        boolean isShieldPos(BlockPos blockPos) {
            boolean flag_x = blockPos.getX() <= maxCornerBlock.getX() && blockPos.getX() >= minCornerBlock.getX();
            boolean flag_y = blockPos.getY() <= maxCornerBlock.getY() && blockPos.getY() >= minCornerBlock.getY();
            boolean flag_z = blockPos.getZ() <= maxCornerBlock.getZ() && blockPos.getZ() >= minCornerBlock.getZ();

            boolean flag_equal = (blockPos.getX() == maxCornerBlock.getX() || blockPos.getX() == minCornerBlock.getX()) ||
                    (blockPos.getY() == maxCornerBlock.getY() || blockPos.getY() == minCornerBlock.getY()) ||
                    (blockPos.getZ() == maxCornerBlock.getZ() || blockPos.getZ() == minCornerBlock.getZ());
            return flag_equal && flag_x && flag_y && flag_z;
        }

        boolean tick() {
            if (taskQueue.isEmpty()) return true;
            int limit = taskQueue.size();
            int count = 0;
            while (!taskQueue.isEmpty()) {
                if (count == limit) break;
                BlockPos pos = taskQueue.poll();
                this.level.setBlock(pos, Blocks.BARRIER.defaultBlockState(), 2);
                if (!generatedPos.contains(pos.above()) && isShieldPos(pos.above())) {
                    this.generatedPos.add(pos.above());
                    taskQueue.add(pos.above());
                }
                if (!generatedPos.contains(pos.below()) && isShieldPos(pos.below())) {
                    this.generatedPos.add(pos.below());
                    taskQueue.add(pos.below());
                }
                if (!generatedPos.contains(pos.north()) && isShieldPos(pos.north())) {
                    this.generatedPos.add(pos.north());
                    taskQueue.add(pos.north());
                }
                if (!generatedPos.contains(pos.south()) && isShieldPos(pos.south())) {
                    this.generatedPos.add(pos.south());
                    taskQueue.add(pos.south());
                }
                if (!generatedPos.contains(pos.east()) && isShieldPos(pos.east())) {
                    this.generatedPos.add(pos.east());
                    taskQueue.add(pos.east());
                }
                if (!generatedPos.contains(pos.west()) && isShieldPos(pos.west())) {
                    this.generatedPos.add(pos.west());
                    taskQueue.add(pos.west());
                }
                count++;
            }
            return false;
        }
    }

    public static class ShipScanningTask {
        private static final long MAX_SHIP_BLOCK = 32768;
        Queue<BlockPos> queue = new LinkedList<>();
        HashSet<BlockPos> scannedPos = new HashSet<>();
        long blockCounted = 0;
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


        public boolean tick() {
            if (blockCounted >= MAX_SHIP_BLOCK) {
                this.failed = true;
                return true;
            }
            if (blockCounted != 0 && queue.isEmpty()) {
                return true;
            } else {
                int limit = queue.size();
                int count = 0;
                while (!queue.isEmpty()) {
                    if (count == limit) break;
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
                    count++;
                }
            }
            return false;
        }
    }
}
