package cool.ender.stardust.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import cool.ender.stardust.missile.launcher.VerticalMissileLauncher;
import net.minecraft.client.ProgressOption;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.SliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VerticalLauncherScreen extends Screen {
    EditBox coordinateX;
    EditBox coordinateY;
    EditBox coordinateZ;

    Button controlMode;

    VerticalMissileLauncher.Tile bindedTile;

    public VerticalLauncherScreen(Component p_96550_, VerticalMissileLauncher.Tile tile) {
        super(p_96550_);
        this.bindedTile = tile;
    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int coordinateInputOffsetX = 0;
        this.coordinateX = new EditBox(this.font, this.width / 2 - 100 + coordinateInputOffsetX, 66, 60, 20, new TranslatableComponent("gui.stardust.coordinates_input.x"));
        coordinateInputOffsetX += 70;
        this.coordinateY = new EditBox(this.font, this.width / 2 - 100 + coordinateInputOffsetX, 66, 60, 20, new TranslatableComponent("gui.stardust.coordinates_input.y"));
        coordinateInputOffsetX += 70;
        this.coordinateZ = new EditBox(this.font, this.width / 2 - 100 + coordinateInputOffsetX, 66, 60, 20, new TranslatableComponent("gui.stardust.coordinates_input.z"));

        this.controlMode = new Button(this.width / 2 - 100, 91, 200, 20, new TranslatableComponent("gui.stardust.vertical_launcher.control_mode").append(new TextComponent(":")), (button) -> {
            bindedTile.switchControlMode();
            button.setMessage(new TranslatableComponent("gui.stardust.vertical_launcher.control_mode").append(new TextComponent(":").append(Objects.requireNonNull(bindedTile.controlMode.getComponent()))));
        });

        this.addRenderableWidget(this.coordinateX);
        this.addRenderableWidget(this.coordinateY);
        this.addRenderableWidget(this.coordinateZ);
        this.addRenderableWidget(controlMode);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float particleTick) {

        drawString(poseStack, this.font, new TextComponent("X:"), this.width / 2 - 120 + 10, 71, 16777215);
        drawString(poseStack, this.font, new TextComponent("Y:"), this.width / 2 - 50 + 10, 71, 16777215);
        drawString(poseStack, this.font, new TextComponent("Z:"), this.width / 2 + 20 + 10, 71, 16777215);
        this.coordinateX.render(poseStack, mouseX, mouseY, particleTick);
        this.coordinateY.render(poseStack, mouseX, mouseY, particleTick);
        this.coordinateZ.render(poseStack, mouseX, mouseY, particleTick);
        this.controlMode.render(poseStack, mouseX, mouseY, particleTick);


    }
}
