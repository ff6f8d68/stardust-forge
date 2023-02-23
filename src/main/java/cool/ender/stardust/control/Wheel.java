package cool.ender.stardust.control;

import cool.ender.stardust.registry.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Wheel {
    public static class Block extends BaseEntityBlock {

        public Block() {
            super(Properties.of(Material.STONE));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            return null;
        }

        @Override
        public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
            //TODO: put ship assemble code here
            return super.use(p_60503_, p_60504_, p_60505_, p_60506_, p_60507_, p_60508_);
        }
    }

    public static class Tile extends BlockEntity {
        public Tile(BlockEntityType<?> tile, BlockPos blockPos, BlockState blockState) {
            super(tile, blockPos, blockState);
        }

        public Tile(BlockPos blockPos, BlockState blockState) {
            this(TileRegistry.WHEEL_TILE.get(), blockPos, blockState);
        }
    }



}
