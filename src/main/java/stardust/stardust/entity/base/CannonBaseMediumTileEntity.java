package stardust.stardust.entity.base;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import stardust.stardust.registry.TileEntityTypeRegistry;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.print.DocFlavor;
import java.util.Objects;

public class CannonBaseMediumTileEntity extends TileEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);
    public CannonBaseMediumTileEntity() {
        super(TileEntityTypeRegistry.CANNON_BASE_MEDIUM_TILE_ENTITY.get());
    }

    @ParametersAreNonnullByDefault
    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);
    }

    @Override
    public @ParametersAreNonnullByDefault CompoundNBT write(CompoundNBT compound) {
        return super.write(compound);
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
