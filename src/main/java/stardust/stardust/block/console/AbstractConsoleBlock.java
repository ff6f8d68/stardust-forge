package stardust.stardust.block.console;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class AbstractConsoleBlock extends DirectionalBlock {
    protected AbstractConsoleBlock(Properties builder) {
        super(builder);
    }
    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true ;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
