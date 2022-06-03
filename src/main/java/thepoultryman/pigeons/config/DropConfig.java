package thepoultryman.pigeons.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import thepoultryman.pigeons.Pigeons;

import java.io.*;

public class DropConfig {
    private static final String CONFIG_LOCATION = FabricLoader.getInstance().getConfigDir() + "/pleasant-pigeons.json";

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Default Config Values
    public static DropConfigFormat.DropData cityDrop = new DropConfigFormat.DropData("minecraft:diamond", 1);
    public static DropConfigFormat.DropData antwerpSmerleBrownDrop = new DropConfigFormat.DropData("minecraft:raw_iron", 3);
    public static DropConfigFormat.DropData antwerpSmerleBrownGray = new DropConfigFormat.DropData("minecraft:raw_copper", 7);
    public static DropConfigFormat.DropData egyptianSwift = new DropConfigFormat.DropData("minecraft:cooked_beef", 5);

    public static DropConfigFormat loadConfig() {
        if (!new File(CONFIG_LOCATION).exists()) {
            // Start the writing process
            FileWriter writer;
            try {
                writer = new FileWriter(CONFIG_LOCATION);
                DropConfigFormat dropConfig = new DropConfigFormat(cityDrop, antwerpSmerleBrownDrop, antwerpSmerleBrownGray, egyptianSwift,
                        17000, 5700, 100);
                gson.toJson(gson.toJsonTree(dropConfig), writer);

                // Finish the writing process
                try {
                    writer.flush();
                    writer.close();
                    return dropConfig;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(CONFIG_LOCATION));
                return gson.fromJson(reader, DropConfigFormat.class);
            } catch (FileNotFoundException e) {
                Pigeons.LOGGER.info("There was an issue reading you config file.", e);
            }
        }
        return null;
    }
}
