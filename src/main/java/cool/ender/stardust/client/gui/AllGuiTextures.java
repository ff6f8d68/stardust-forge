package cool.ender.stardust.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import cool.ender.stardust.Stardust;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum AllGuiTextures {

    COMPUTER_GUI("computer_gui", 232, 164);

    public static final int FONT_COLOR = 0x575F7A;

    public final ResourceLocation location;
    public int width, height;
    public int startX, startY;

    private AllGuiTextures(String location, int width, int height) {
        this(location, 0, 0, width, height);
    }

    private AllGuiTextures(int startX, int startY) {
        this("icons", startX * 16, startY * 16, 16, 16);
    }

    private AllGuiTextures(String location, int startX, int startY, int width, int height) {
        this(Stardust.MOD_ID, location, startX, startY, width, height);
    }

    private AllGuiTextures(String namespace, String location, int startX, int startY, int width, int height) {
        this.location = new ResourceLocation(namespace, "textures/gui/" + location + ".png");
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
    }

    @OnlyIn(Dist.CLIENT)
    public void bind() {
        RenderSystem.setShaderTexture(0, location);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack ms, int x, int y) {
        bind();
        GuiComponent.blit(ms, x, y, 0, startX, startY, width, height, 256, 256);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack ms, int x, int y, GuiComponent component) {
        bind();
        component.blit(ms, x, y, startX, startY, width, height);
    }

//    @OnlyIn(Dist.CLIENT)
//    public void render(PoseStack ms, int x, int y, Color c) {
//        bind();
//        UIRenderHelper.drawColoredTexture(ms, c, x, y, startX, startY, width, height);
//    }

}
