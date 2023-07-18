package cool.ender.stardust.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IColorfulItem {
    int getDefaultColor();

    default void setColor(ItemStack itemStack, int color) {
        itemStack.getOrCreateTagElement("display").putInt("color", color);
    }

    default boolean hasCustomColor(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99);
    }

    default int getColor(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : getDefaultColor();
    }

    default void clearColor(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTagElement("display");
        if (compoundtag != null && compoundtag.contains("color")) {
            compoundtag.remove("color");
        }
    }
}
