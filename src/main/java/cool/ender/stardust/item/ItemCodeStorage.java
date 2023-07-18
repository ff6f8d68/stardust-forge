package cool.ender.stardust.item;

import cool.ender.stardust.util.IColorfulItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemCodeStorage extends Item implements IColorfulItem {

    public String code;

    public Boolean readAccessibility;
    public Boolean writeAccessibility;

    public ItemCodeStorage(Properties p_41383_) {
        super(p_41383_);
    }

    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = super.getShareTag(stack);
        if (nbt == null) {
            nbt = new CompoundTag();
        }
        nbt.putString("code", this.code);
        nbt.putBoolean("read", this.readAccessibility);
        nbt.putBoolean("write", this.writeAccessibility);
        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null) {
            this.code = nbt.getString("code");
            this.readAccessibility = nbt.getBoolean("read");
            this.writeAccessibility = nbt.getBoolean("write");
        }
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack instance = super.getDefaultInstance();
        setColor(instance, getDefaultColor());
        return instance;
    }

    @Override
    public int getDefaultColor() {
        return 0;
    }
}
