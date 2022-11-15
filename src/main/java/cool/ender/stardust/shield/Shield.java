package cool.ender.stardust.shield;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.BlockRegistry;
import cool.ender.stardust.registry.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.PanelUI;

public class Shield {
    public static class Block extends GlassBlock implements EntityBlock {

        public Block() {
            super(Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F).noDrops().noOcclusion());
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new Tile(blockPos, blockState);
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
