package cool.ender.stardust.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ComputerGuiScreen extends Screen {
    public ComputerGuiScreen(Component p_96550_) {
        super(p_96550_);
    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float particleTick) {
        AllGuiTextures.COMPUTER_GUI.render(poseStack, (this.width - AllGuiTextures.COMPUTER_GUI.width) / 2, (this.height - AllGuiTextures.COMPUTER_GUI.height) / 2);

    }
}
