package cool.ender.stardust.registry;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.component.tube.Tube;
import cool.ender.stardust.component.turret.small.RailGun1Small;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static cool.ender.stardust.component.turret.AbstractTurret.REGISTRY_TYPE.ITEM;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Stardust.MOD_ID);
    public static final RegistryObject<BlockItem> RAIL_GUN_1_SMALL_ITEM = ITEMS.register(new RailGun1Small().getRegisterName(ITEM), () -> new BlockItem(BlockRegistry.RAIL_GUN_1_SMALL_BLOCK.get(), new Item.Properties().tab(Stardust.stardustCreativeGroup)));
    public static final RegistryObject<BlockItem> COMPUTER_ITEM = ITEMS.register("computer_item", () -> new BlockItem(BlockRegistry.COMPUTER_BLOCK.get(), new Item.Properties().tab(Stardust.stardustCreativeGroup)));
    public static final RegistryObject<BlockItem> SHIELD_GENERATOR_ITEM = ITEMS.register("shield_generator_item", () -> new BlockItem(BlockRegistry.SHIELD_GENERATOR_BLOCK.get(), new Item.Properties().tab(Stardust.stardustCreativeGroup)));
    public static final RegistryObject<BlockItem> TUBE = ITEMS.register(new Tube().getRegisterName(),() -> new BlockItem(BlockRegistry.TUBE_BLOCK.get(), new Item.Properties().tab(Stardust.stardustCreativeGroup)));
    public static final RegistryObject<BlockItem> VERTICAL_MISSILE_LAUNCHER = ITEMS.register("vertical_missile_launcher",() -> new BlockItem(BlockRegistry.VERTICAL_MISSILE_LAUNCHER_BLOCK.get(), new Item.Properties().tab(Stardust.stardustCreativeGroup)));
    public static final RegistryObject<BlockItem> THRUSTER = ITEMS.register("thruster", () -> new BlockItem(BlockRegistry.THRUSTER_BLOCK.get(), new Item.Properties().tab(Stardust.stardustCreativeGroup)));
    public static final RegistryObject<BlockItem> HELM = ITEMS.register("helm", () -> new BlockItem(BlockRegistry.HELM.get(), new Item.Properties().tab(Stardust.stardustCreativeGroup)));
}
