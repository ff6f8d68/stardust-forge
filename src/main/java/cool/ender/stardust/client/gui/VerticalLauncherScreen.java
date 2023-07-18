package cool.ender.stardust.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import cool.ender.stardust.component.missile.launcher.VerticalMissileLauncher;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VerticalLauncherScreen extends ComputerGuiScreen {
    EditBox coordinateX;
    EditBox coordinateY;
    EditBox coordinateZ;

    Button controlMode;
    Button enterTelevision;

    Button explodeOption;


    VerticalMissileLauncher.Tile bindedTile;

    public VerticalLauncherScreen(Component p_96550_, VerticalMissileLauncher.Tile tile) {
        super(p_96550_);
        this.bindedTile = tile;
    }

    @Override
    protected void init() {
        super.init();
        int topBaseHeight = (this.height - AllGuiTextures.COMPUTER_GUI.height) / 2;
        int coordinateInputOffsetX = 0;
        this.coordinateX = new EditBox(this.font, this.width / 2 - 100 + coordinateInputOffsetX, topBaseHeight + 40, 60, 20, new TranslatableComponent("gui.stardust.coordinates_input.x"));
        coordinateInputOffsetX += 70;
        this.coordinateY = new EditBox(this.font, this.width / 2 - 100 + coordinateInputOffsetX, topBaseHeight + 40, 60, 20, new TranslatableComponent("gui.stardust.coordinates_input.y"));
        coordinateInputOffsetX += 70;
        this.coordinateZ = new EditBox(this.font, this.width / 2 - 100 + coordinateInputOffsetX, topBaseHeight + 40, 60, 20, new TranslatableComponent("gui.stardust.coordinates_input.z"));

        this.controlMode = new Button(this.width / 2 - 100, topBaseHeight + 40 + 30, 200, 20, new TranslatableComponent("gui.stardust.vertical_launcher.control_mode").append(new TextComponent(":").append(Objects.requireNonNull(bindedTile.controlMode.getComponent()))), (button) -> {
            this.bindedTile.switchControlMode();
            if (bindedTile.controlMode != VerticalMissileLauncher.ControlMode.COORDINATE) {
                coordinateX.setEditable(false);
                coordinateY.setEditable(false);
                coordinateZ.setEditable(false);
            } else {
                coordinateX.setEditable(true);
                coordinateY.setEditable(true);
                coordinateZ.setEditable(true);
            }
            button.setMessage(new TranslatableComponent("gui.stardust.vertical_launcher.control_mode").append(new TextComponent(":").append(Objects.requireNonNull(bindedTile.controlMode.getComponent()))));
        });
        this.explodeOption = new Button(this.width / 2 - 100, topBaseHeight + 40 + 30 + 30, 200, 20, new TranslatableComponent("gui.stardust.vertical_launcher.explode_on_discard").append(new TextComponent(":").append(bindedTile.explodeOnDiscard ? new TranslatableComponent("message.stardust.yes") : new TranslatableComponent("message.stardust.no"))), (button) -> {
            this.bindedTile.switchExplodeOption();
            button.setMessage(new TranslatableComponent("gui.stardust.vertical_launcher.explode_on_discard").append(new TextComponent(":").append(bindedTile.explodeOnDiscard ? new TranslatableComponent("message.stardust.yes") : new TranslatableComponent("message.stardust.no"))));
        });
        this.enterTelevision = new Button(this.width / 2 - 100, topBaseHeight + 40, 200, 20, new TranslatableComponent("gui.stardust.vertical_launcher.enter_television"), (button) -> {
            this.onClose();
            // TODO: television code
        });
        this.addRenderableWidget(this.coordinateX);
        this.addRenderableWidget(this.coordinateY);
        this.addRenderableWidget(this.coordinateZ);
        this.addRenderableWidget(this.controlMode);
        this.addRenderableWidget(this.explodeOption);
        this.addRenderableWidget(this.enterTelevision);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float particleTick) {
        super.render(poseStack, mouseX, mouseY, particleTick);
        if (this.bindedTile.controlMode == VerticalMissileLauncher.ControlMode.COORDINATE) {
            drawString(poseStack, this.font, new TextComponent("X:"), this.width / 2 - 120 + 10, (this.height - AllGuiTextures.COMPUTER_GUI.height) / 2 + 45, 16777215);
            drawString(poseStack, this.font, new TextComponent("Y:"), this.width / 2 - 50 + 10, (this.height - AllGuiTextures.COMPUTER_GUI.height) / 2 + 45, 16777215);
            drawString(poseStack, this.font, new TextComponent("Z:"), this.width / 2 + 20 + 10, (this.height - AllGuiTextures.COMPUTER_GUI.height) / 2 + 45, 16777215);

            this.coordinateX.render(poseStack, mouseX, mouseY, particleTick);
            this.coordinateY.render(poseStack, mouseX, mouseY, particleTick);
            this.coordinateZ.render(poseStack, mouseX, mouseY, particleTick);
        }


        if (this.bindedTile.controlMode == VerticalMissileLauncher.ControlMode.TELEVISION) {
            this.enterTelevision.render(poseStack, mouseX, mouseY, particleTick);
        }

        this.controlMode.render(poseStack, mouseX, mouseY, particleTick);
        this.explodeOption.render(poseStack, mouseX, mouseY, particleTick);


    }
}
