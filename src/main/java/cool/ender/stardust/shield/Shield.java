package cool.ender.stardust.shield;

import cool.ender.stardust.registry.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.PanelUI;

public class Shield {
    public static class Block extends BarrierBlock implements EntityBlock {

        public Block() {
            super(Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F).noDrops().noOcclusion());
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new Tile(blockPos, blockState);
        }

        @Override
        public RenderShape getRenderShape(BlockState p_49098_) {
            return RenderShape.MODEL;
        }




    }

    public static class Tile extends BlockEntity {
        BlockPos owner;
        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.SHIELD_TILE.get(), p_155229_, p_155230_);
        }

        public void setOwner(BlockPos owner) {
            this.owner = owner;
        }

        public BlockPos getOwner() {
            return this.owner;
        }
    }
}
