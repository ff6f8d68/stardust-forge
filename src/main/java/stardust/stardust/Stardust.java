package stardust.stardust;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("stardust")
public class Stardust {

    public static final Logger LOGGER = LogManager.getLogger();

    public static String MOD_ID = "stardust";

    public Stardust() {

        MinecraftForge.EVENT_BUS.register(this);
        
    }

}
