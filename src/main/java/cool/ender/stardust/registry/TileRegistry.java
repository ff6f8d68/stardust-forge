package cool.ender.stardust.registry;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.component.seat.FighterSeat;
import cool.ender.stardust.control.Computer;
import cool.ender.stardust.control.Helm;
import cool.ender.stardust.component.missile.launcher.VerticalMissileLauncher;
import cool.ender.stardust.component.shield.Shield;
import cool.ender.stardust.component.shield.ShieldGenerator;
import cool.ender.stardust.component.thruster.Thruster;
import cool.ender.stardust.component.tube.Tube;
import cool.ender.stardust.component.turret.AbstractTurret;
import cool.ender.stardust.component.turret.medium.RailGun1Medium;
import cool.ender.stardust.component.turret.small.RailGun1Small;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileRegistry {

    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Stardust.MOD_ID);
    public static final RegistryObject<BlockEntityType<RailGun1Medium.Tile>> RAIL_GUN_1_MEDIUM = TILES.register(new RailGun1Medium().getRegisterName(AbstractTurret.REGISTRY_TYPE.TILE), () -> BlockEntityType.Builder.of(RailGun1Medium.Tile::new, BlockRegistry.RAIL_GUN_1_MEDIUM.get()).build(null));
    public static final RegistryObject<BlockEntityType<RailGun1Small.Tile>> RAIL_GUN_1_SMALL = TILES.register(new RailGun1Small().getRegisterName(AbstractTurret.REGISTRY_TYPE.TILE), () -> BlockEntityType.Builder.of(RailGun1Small.Tile::new, BlockRegistry.RAIL_GUN_1_SMALL.get()).build(null));
    public static final RegistryObject<BlockEntityType<Computer.Tile>> COMPUTER = TILES.register("computer_tile", () -> BlockEntityType.Builder.of(Computer.Tile::new, BlockRegistry.COMPUTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShieldGenerator.Tile>> SHIELD_GENERATOR = TILES.register("shield_generator_tile", () -> BlockEntityType.Builder.of(ShieldGenerator.Tile::new, BlockRegistry.SHIELD_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<Shield.Tile>> SHIELD = TILES.register("shield_tile", () -> BlockEntityType.Builder.of(Shield.Tile::new, BlockRegistry.SHIELD.get()).build(null));
    public static final RegistryObject<BlockEntityType<Tube.Tile>> TUBE = TILES.register("tube_tile", () -> BlockEntityType.Builder.of(Tube.Tile::new, BlockRegistry.TUBE.get()).build(null));
    public static final RegistryObject<BlockEntityType<VerticalMissileLauncher.Tile>> VERTICAL_MISSILE_LAUNCHER = TILES.register("vertical_missile_launcher_tile", () -> BlockEntityType.Builder.of(VerticalMissileLauncher.Tile::new, BlockRegistry.VERTICAL_MISSILE_LAUNCHER.get()).build(null));
    public static final RegistryObject<BlockEntityType<Thruster.Tile>> THRUSTER = TILES.register("thruster_tile", () -> BlockEntityType.Builder.of(Thruster.Tile::new, BlockRegistry.THRUSTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<Helm.Tile>> HELM = TILES.register("helm", () -> BlockEntityType.Builder.of(Helm.Tile::new, BlockRegistry.HELM.get()).build(null));
    public static final RegistryObject<BlockEntityType<FighterSeat.Tile>> FIGHTER_SEAT = TILES.register("fighter_seat", () -> BlockEntityType.Builder.of(FighterSeat.Tile::new, BlockRegistry.FIGHTER_SEAT.get()).build(null));
}
