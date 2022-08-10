package stardust.stardust.item.cannon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import stardust.stardust.registry.BlockRegistry;

public class RailGun4MediumItem extends BlockItem {
    public RailGun4MediumItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }

    @Override
    public ActionResultType tryPlace(BlockItemUseContext context) {
        World world = context.getWorld();

        BlockState blockStateBottom = world.getBlockState(context.getPos().offset(Direction.DOWN));
        if(blockStateBottom.getBlock() != BlockRegistry.CANNON_BASE_MEDIUM.get()){
            return ActionResultType.FAIL;
        } else {
            return ActionResultType.SUCCESS;
        }
    }
}
