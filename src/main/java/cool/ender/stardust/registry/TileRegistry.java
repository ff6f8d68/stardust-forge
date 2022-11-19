package cool.ender.stardust.registry;

import com.mojang.datafixers.types.Type;
import cool.ender.stardust.Stardust;
import cool.ender.stardust.control.Computer;
import cool.ender.stardust.missile.launcher.VerticalMissileLauncher;
import cool.ender.stardust.shield.Shield;
import cool.ender.stardust.shield.ShieldGenerator;
import cool.ender.stardust.tube.Tube;
import cool.ender.stardust.turret.AbstractTurret;
import cool.ender.stardust.turret.medium.RailGun1Medium;
import cool.ender.stardust.turret.small.RailGun1Small;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileRegistry {

    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Stardust.MOD_ID);
    public static final RegistryObject<BlockEntityType<RailGun1Medium.Tile>> RAIL_GUN_1_MEDIUM_TILE = TILES.register(new RailGun1Medium().getRegisterName(AbstractTurret.REGISTRY_TYPE.TILE), () -> BlockEntityType.Builder.of(RailGun1Medium.Tile::new, BlockRegistry.RAIL_GUN_1_MEDIUM_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<RailGun1Small.Tile>> RAIL_GUN_1_SMALL_TILE = TILES.register(new RailGun1Small().getRegisterName(AbstractTurret.REGISTRY_TYPE.TILE), () -> BlockEntityType.Builder.of(RailGun1Small.Tile::new, BlockRegistry.RAIL_GUN_1_SMALL_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<Computer.Tile>> COMPUTER_TILE = TILES.register("computer_tile", () -> BlockEntityType.Builder.of(Computer.Tile::new, BlockRegistry.COMPUTER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShieldGenerator.Tile>> SHIELD_GENERATOR_TILE = TILES.register("shield_generator_tile", () -> BlockEntityType.Builder.of(ShieldGenerator.Tile::new, BlockRegistry.SHIELD_GENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<Shield.Tile>> SHIELD_TILE = TILES.register("shield_tile", () -> BlockEntityType.Builder.of(Shield.Tile::new, BlockRegistry.SHIELD_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<Tube.Tile>> TUBE_TILE = TILES.register("tube_tile", () -> BlockEntityType.Builder.of(Tube.Tile::new, BlockRegistry.TUBE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<VerticalMissileLauncher.Tile>> VERTICAL_MISSILE_LAUNCHER_TILE = TILES.register("vertical_missile_launcher_tile", () -> BlockEntityType.Builder.of(VerticalMissileLauncher.Tile::new, BlockRegistry.VERTICAL_MISSILE_LAUNCHER_BLOCK.get()).build(null));

}
