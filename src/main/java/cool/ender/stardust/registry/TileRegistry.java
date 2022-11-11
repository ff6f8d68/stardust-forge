package cool.ender.stardust.registry;

import com.mojang.datafixers.types.Type;
import cool.ender.stardust.Stardust;
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


}
