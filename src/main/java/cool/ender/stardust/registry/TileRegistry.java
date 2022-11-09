package cool.ender.stardust.registry;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.turret.AbstractTurret;
import cool.ender.stardust.turret.medium.KineticCannon;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileRegistry {

    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Stardust.MOD_ID);
    public static final RegistryObject<BlockEntityType<KineticCannon.Tile>> KINETIC_CANNON_TILE = TILES.register(new KineticCannon().getRegisterName(AbstractTurret.REGISTRY_TYPE.TILE), () -> BlockEntityType.Builder.of(KineticCannon.Tile::new, BlockRegistry.KINETIC_CANNON_BLOCK.get()).build(null));
}
