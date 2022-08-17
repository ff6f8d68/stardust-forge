package stardust.stardust.entity.thruster;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import stardust.stardust.registry.TileEntityTypeRegistry;

public class Thruster1TileEnity extends AbstractThrusterTileEntity implements IAnimatable {
    private  final AnimationFactory factory = new AnimationFactory(this);

    public Thruster1TileEnity(){
        super(TileEntityTypeRegistry.THRUSTER_1_TILE_ENTITY.get());
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
