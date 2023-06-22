package cool.ender.stardust.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.datastructures.DenseBlockPosSet;
import org.valkyrienskies.mod.common.assembly.ShipAssemblyKt;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ShipAssembler {

    BlockPos centerPos;
    DenseBlockPosSet blocks;
    ServerLevel level;

    DenseBlockPosSet rangedBlocks = new DenseBlockPosSet();
    private static final long MAX_ASSEMBLED_BLOCKS = 114514;
    private final Queue<BlockPos> blockQueue = new LinkedBlockingQueue<>();

    public ShipAssembler(BlockPos centerPos, Level level) {
        this.centerPos = centerPos;
        this.level = (ServerLevel) level;
        this.blocks = new DenseBlockPosSet();
        this.blockQueue.add(centerPos);
    }

    private boolean isValid(BlockPos blockPos) {
        BlockState blockState = this.level.getBlockState(blockPos);
        return !blockState.isAir() && !(blockState.getBlock() instanceof LiquidBlock) && !(blockState.getBlock() instanceof SnowyDirtBlock);
    }

    private void bfs() {
        BlockPos blockPos = this.blockQueue.poll();
        assert blockPos != null;
        this.blocks.add(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        for (Direction direction : Direction.values()) {
            BlockPos relative = blockPos.relative(direction);
            if (!rangedBlocks.contains(relative.getX(), relative.getY(), relative.getZ())) {
                rangedBlocks.add(relative.getX(), relative.getY(), relative.getZ());
                if (isValid(relative)) {
                    this.blockQueue.add(relative);
                }
            }
        }
    }

    public ServerShip assemble() {
        while (!this.blockQueue.isEmpty()) {
            this.bfs();
            if (this.blocks.size() > MAX_ASSEMBLED_BLOCKS) return null;
        }
        return ShipAssemblyKt.createNewShipWithBlocks(this.centerPos, this.blocks, this.level);
    }
}
