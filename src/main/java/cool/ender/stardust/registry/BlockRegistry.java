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
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Stardust.MOD_ID);

    public static final RegistryObject<RailGun1Medium.Block> RAIL_GUN_1_MEDIUM = BLOCKS.register(new RailGun1Medium().getRegisterName(AbstractTurret.REGISTRY_TYPE.BLOCK), RailGun1Medium.Block::new);
    public static final RegistryObject<RailGun1Small.Block> RAIL_GUN_1_SMALL = BLOCKS.register(new RailGun1Small().getRegisterName(AbstractTurret.REGISTRY_TYPE.BLOCK), RailGun1Small.Block::new);
    public static final RegistryObject<Computer.Block> COMPUTER = BLOCKS.register("computer", Computer.Block::new);
    public static final RegistryObject<ShieldGenerator.Block> SHIELD_GENERATOR = BLOCKS.register("shield_generator", ShieldGenerator.Block::new);
    public static final RegistryObject<Shield.Block> SHIELD = BLOCKS.register("shield", Shield.Block::new);

    public static final RegistryObject<Tube.Block> TUBE = BLOCKS.register(new Tube().getRegisterName(), Tube.Block::new);
    public static final RegistryObject<VerticalMissileLauncher.Block> VERTICAL_MISSILE_LAUNCHER = BLOCKS.register("vertical_missile_launcher", VerticalMissileLauncher.Block::new);
    public static final RegistryObject<Thruster.Block> THRUSTER = BLOCKS.register("thruster", Thruster.Block::new);
    public static final RegistryObject<Helm.Block> HELM = BLOCKS.register("helm", Helm.Block::new);

    public static final RegistryObject<FighterSeat.Block> FIGHTER_SEAT = BLOCKS.register("fighter_seat", FighterSeat.Block::new);
}
