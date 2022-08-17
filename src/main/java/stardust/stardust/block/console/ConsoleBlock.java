package stardust.stardust.block.console;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import stardust.stardust.entity.console.ConsoleTileEntity;
import stardust.stardust.entity.thruster.Thruster1TileEnity;

import javax.annotation.Nullable;

public class ConsoleBlock extends AbstractConsoleBlock{
    public ConsoleBlock() {
        super(Properties.create(Material.IRON));
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ConsoleTileEntity();
    }

}
