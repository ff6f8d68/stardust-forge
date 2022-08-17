package stardust.stardust.client.render.gecko.renderer.block;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import stardust.stardust.client.render.gecko.model.block.ConsoleBlockModel;
import stardust.stardust.entity.console.ConsoleTileEntity;

public class ConsoleBlockRenderer extends GeoBlockRenderer<ConsoleTileEntity> {
    public ConsoleBlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new ConsoleBlockModel());
    }
}
