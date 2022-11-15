package cool.ender.stardust.registry;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.turret.small.RailGun1Small;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.example.GeckoLibMod;

import static cool.ender.stardust.turret.AbstractTurret.REGISTRY_TYPE.ITEM;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stardust.MOD_ID);
    public static final RegistryObject<BlockItem> RAIL_GUN_1_SMALL_ITEM = ITEMS.register(new RailGun1Small().getRegisterName(ITEM), () -> new BlockItem(BlockRegistry.RAIL_GUN_1_SMALL_BLOCK.get(), new Item.Properties().tab(GeckoLibMod.geckolibItemGroup)));
    public static final RegistryObject<BlockItem> COMPUTER_ITEM = ITEMS.register("computer_item", () -> new BlockItem(BlockRegistry.COMPUTER_BLOCK.get(), new Item.Properties().tab(GeckoLibMod.geckolibItemGroup)));
    public static final RegistryObject<BlockItem> SHIELD_GENERATOR_ITEM = ITEMS.register("shield_generator_item", () -> new BlockItem(BlockRegistry.SHIELD_GENERATOR_BLOCK.get(), new Item.Properties().tab(GeckoLibMod.geckolibItemGroup)));

}
