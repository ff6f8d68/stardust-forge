package stardust.stardust;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("stardust")
public class Stardust {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static String MODID = "stardust";

    public Stardust() {

        MinecraftForge.EVENT_BUS.register(this);
        
    }

}
