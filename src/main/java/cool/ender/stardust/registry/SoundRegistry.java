package cool.ender.stardust.registry;

import cool.ender.stardust.Stardust;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.example.GeckoLibMod;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Stardust.MOD_ID);
    public static final RegistryObject<SoundEvent> PLASMA_BLAST = SOUND_EVENTS.register("plasma_blast", () -> new SoundEvent(new ResourceLocation(Stardust.MOD_ID, "plasma_blast")));
}
