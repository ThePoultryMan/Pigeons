package thepoultryman.pigeons.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import thepoultryman.pigeons.Pigeons;

import java.io.*;

public class DropConfig implements ModInitializer {
    private static final String CONFIG_LOCATION = FabricLoader.getInstance().getConfigDir() + "/pleasant-pigeons.json";

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Config Values
    public DropElements.DropData cityDrop = new DropElements.DropData("minecraft:item", 1);
    public DropElements.DropData antwerpSmerleBrownDrop = new DropElements.DropData("minecraft:steak", 3);

    public JsonElement configJson = gson.toJsonTree(new DropElements(cityDrop, antwerpSmerleBrownDrop));

    @Override
    public void onInitialize() {
        // Start the writing process
        FileWriter writer = null;
        try {
            writer = new FileWriter(CONFIG_LOCATION);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gson.toJson(configJson, writer);

        // Finish the writing process
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Pigeons.LOGGER.info(getSpecialDrop("city"));
    }

    public String getSpecialDrop(String pigeonType) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(CONFIG_LOCATION));
        } catch (FileNotFoundException e) {
            Pigeons.LOGGER.warn("There was an issue finding the config file. " +
                    "Please check if your config file is in your config directory. The stack trace will be printed below.");
            e.printStackTrace();
        }

        if (reader != null)
            return gson.fromJson(reader, DropElements.class).getCity().getItem();
        else {
            return "green, lmao";
        }
    }
}
