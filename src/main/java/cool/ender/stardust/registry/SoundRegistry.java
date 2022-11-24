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

    public static final RegistryObject<SoundEvent> SHIELD_GENERATOR_ON = SOUND_EVENTS.register("shield_generator_on", () -> new SoundEvent(new ResourceLocation(Stardust.MOD_ID, "shield_generator_on")));
    public static final RegistryObject<SoundEvent> SHIELD_GENERATOR_OFF = SOUND_EVENTS.register("shield_generator_off", () -> new SoundEvent(new ResourceLocation(Stardust.MOD_ID, "shield_generator_off")));
    public static final RegistryObject<SoundEvent> MISSILE_DOOR = SOUND_EVENTS.register("missile_door", () -> new SoundEvent(new ResourceLocation(Stardust.MOD_ID, "missile_door")));

}
