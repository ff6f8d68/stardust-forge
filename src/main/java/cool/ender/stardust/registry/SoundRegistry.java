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
    public static final RegistryObject<SoundEvent> PLASMA_EXPLOSION = SOUND_EVENTS.register("plasma_explosion", () -> new SoundEvent(new ResourceLocation(Stardust.MOD_ID, "plasma_explosion")));
    public static final RegistryObject<SoundEvent> X_WING_SHOOTING_SOUND = SOUND_EVENTS.register("x_wing_shooting_sound", () -> new SoundEvent(new ResourceLocation(Stardust.MOD_ID, "x_wing_shooting_sound")));
}
