package cool.ender.stardust.control;

import cool.ender.stardust.client.gui.ComputerGuiScreen;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;

public interface GuiProvider {
    List<ComputerGuiScreen> getGuiList();
}
