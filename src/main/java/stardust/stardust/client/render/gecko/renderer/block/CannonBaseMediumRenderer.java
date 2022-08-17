package stardust.stardust.client.render.gecko.renderer.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import stardust.stardust.block.base.CannonBaseMedium;
import stardust.stardust.client.render.gecko.model.block.CannonBaseMediumModel;
import stardust.stardust.entity.base.CannonBaseMediumTileEntity;

public class CannonBaseMediumRenderer extends GeoBlockRenderer<CannonBaseMediumTileEntity> {
    public CannonBaseMediumRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new CannonBaseMediumModel());
    }

    @Override
    public void render(TileEntity tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        if (CannonBaseMedium.isCenterBlock(tile.getBlockState())) {
            super.render(tile, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }



}
