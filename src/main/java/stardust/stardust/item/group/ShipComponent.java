package stardust.stardust.item.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import stardust.stardust.registry.ItemRegistry;

public class ShipComponent extends ItemGroup {
    public ShipComponent() {
        super("ShipComponent");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.THRUSTER_1_BLOCK.get());
    }
}
