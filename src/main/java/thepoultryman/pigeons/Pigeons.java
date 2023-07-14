package thepoultryman.pigeons;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thepoultryman.pigeons.config.PigeonsConfig;

public class Pigeons implements ModInitializer {
    public static final String MOD_ID = "pigeons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final PigeonsConfig CONFIG = new PigeonsConfig();

    @Override
    public void onInitialize() {}
}
