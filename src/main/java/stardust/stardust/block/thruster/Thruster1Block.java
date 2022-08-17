package stardust.stardust.block.thruster;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import stardust.stardust.entity.thruster.Thruster1TileEnity;

import javax.annotation.Nullable;

public class Thruster1Block extends AbstractThrusterBlock{
    public Thruster1Block() {
        super(Properties.create(Material.IRON));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new Thruster1TileEnity();
    }
}
