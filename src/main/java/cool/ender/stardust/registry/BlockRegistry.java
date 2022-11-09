package cool.ender.stardust.registry;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.turret.AbstractTurret;
import cool.ender.stardust.turret.medium.KineticCannon;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Stardust.MOD_ID);

    public static final RegistryObject<KineticCannon.Block> KINETIC_CANNON_BLOCK = BLOCKS.register(new KineticCannon().getRegisterName(AbstractTurret.REGISTRY_TYPE.BLOCK), KineticCannon.Block::new);

}
