package cool.ender.stardust.registry;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.control.Computer;
import cool.ender.stardust.shield.Shield;
import cool.ender.stardust.shield.ShieldGenerator;
import cool.ender.stardust.turret.AbstractTurret;
import cool.ender.stardust.turret.medium.RailGun1Medium;
import cool.ender.stardust.turret.small.RailGun1Small;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Stardust.MOD_ID);

    public static final RegistryObject<RailGun1Medium.Block> RAIL_GUN_1_MEDIUM_BLOCK = BLOCKS.register(new RailGun1Medium().getRegisterName(AbstractTurret.REGISTRY_TYPE.BLOCK), RailGun1Medium.Block::new);
    public static final RegistryObject<RailGun1Small.Block> RAIL_GUN_1_SMALL_BLOCK = BLOCKS.register(new RailGun1Small().getRegisterName(AbstractTurret.REGISTRY_TYPE.BLOCK), RailGun1Small.Block::new);
    public static final RegistryObject<Computer.Block> COMPUTER_BLOCK = BLOCKS.register("computer_block", Computer.Block::new);
    public static final RegistryObject<ShieldGenerator.Block> SHIELD_GENERATOR_BLOCK = BLOCKS.register("shield_generator_block", ShieldGenerator.Block::new);
    public static final RegistryObject<Shield.Block> SHIELD_BLOCK = BLOCKS.register("shield_block", Shield.Block::new);

}
