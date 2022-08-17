package stardust.stardust.entity.console;

import net.minecraft.tileentity.TileEntityType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import stardust.stardust.registry.TileEntityTypeRegistry;

public class ConsoleTileEntity extends AbstractConsoleTileEntity implements IAnimatable {
    final AnimationFactory factory = new AnimationFactory(this);
    public ConsoleTileEntity() {
        super(TileEntityTypeRegistry.CONSOLE_TILE_ENTITY.get());
    }
    @Override
    public void registerControllers(AnimationData data) {
        super.registerControllers(data);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


}
