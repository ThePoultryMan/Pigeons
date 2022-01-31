package thepoultryman.pigeons;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pigeons implements ModInitializer {
    public static final String MOD_ID = "pigeons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing the attack of the pigeons");
    }
}
