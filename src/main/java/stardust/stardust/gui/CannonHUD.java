package stardust.stardust.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import stardust.stardust.Stardust;

public class CannonHUD extends AbstractGui {
    private final int width;
    private final int height;

    private final double scaleFactor;
    private final Minecraft minecraft;
    private final ResourceLocation HUD = new ResourceLocation(Stardust.MODID, "textures/gui/cannon_hud.png");
    private MatrixStack matrixStack;

    public CannonHUD(MatrixStack matrixStack) {
        this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        this.scaleFactor = Minecraft.getInstance().getMainWindow().getGuiScaleFactor();
        this.minecraft = Minecraft.getInstance();
        this.matrixStack = matrixStack;
    }

    public void setMatrixStack(MatrixStack stack) {
        this.matrixStack = stack;
    }


    public void render() {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(HUD);
        blit(matrixStack, 0, 0, 0, 0, width, height, (int) (1920 / this.scaleFactor), (int) (1080 / this.scaleFactor));
    }

}
