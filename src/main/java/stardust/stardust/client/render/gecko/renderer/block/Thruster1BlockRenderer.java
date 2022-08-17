package stardust.stardust.client.render.gecko.renderer.block;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import stardust.stardust.client.render.gecko.model.block.Thruster1BlockModel;
import stardust.stardust.entity.thruster.Thruster1TileEnity;

public class Thruster1BlockRenderer extends GeoBlockRenderer<Thruster1TileEnity> {
    public Thruster1BlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new Thruster1BlockModel());
    }
}
