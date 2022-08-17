package stardust.stardust.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stardust.stardust.Stardust;

import java.util.Objects;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/block/Block;shouldSideBeRendered(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;)Z", cancellable = true)
    private static void sheouldSideBeRendered(BlockState adjacentState, IBlockReader blockState, BlockPos blockAccess, Direction pos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockpos = blockAccess.offset(pos);
        BlockState blockstate = blockState.getBlockState(blockpos);
        if (Objects.requireNonNull(blockstate.getBlock().getRegistryName()).getNamespace().equals("stardust"))
            cir.setReturnValue(true);
    }
}
