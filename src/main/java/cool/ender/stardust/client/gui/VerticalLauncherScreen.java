package cool.ender.stardust.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class VerticalLauncherScreen extends Screen {
    EditBox text;
    public VerticalLauncherScreen(Component p_96550_) {
        super(p_96550_);
    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.text = new EditBox(this.font, this.width / 2 - 100, 66, 200, 20, new TextComponent("stardust.coordinates_input"));
        this.addRenderableWidget(this.text);

    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float particleTick) {
        this.text.render(poseStack, mouseX, mouseY, particleTick);
    }
}
