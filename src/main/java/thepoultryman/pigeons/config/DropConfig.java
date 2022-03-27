package thepoultryman.pigeons.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.pigeons.Pigeons;

import java.io.*;

public class DropConfig implements ModInitializer {
    private static final String CONFIG_LOCATION = FabricLoader.getInstance().getConfigDir() + "/pleasant-pigeons.json";

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Default Config Values
    public static DropConfigFormat.DropData cityDrop = new DropConfigFormat.DropData("minecraft:diamond", 1);
    public static DropConfigFormat.DropData antwerpSmerleBrownDrop = new DropConfigFormat.DropData("minecraft:raw_iron", 3);
    public static DropConfigFormat.DropData antwerpSmerleBrownGray = new DropConfigFormat.DropData("minecraft:raw_copper", 7);
    public static DropConfigFormat.DropData egyptianSwift = new DropConfigFormat.DropData("minecraft:cooked_beef", 5);
    public JsonElement configJson = gson.toJsonTree(new DropConfigFormat(cityDrop, antwerpSmerleBrownDrop, antwerpSmerleBrownGray, egyptianSwift,
            17000, 5700, 100));

    @Override
    public void onInitialize() {
        if (!new File(CONFIG_LOCATION).exists()) {
            // Start the writing process
            FileWriter writer;
            try {
                writer = new FileWriter(CONFIG_LOCATION);

                gson.toJson(configJson, writer);

                // Finish the writing process
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Config Reading

    public static ItemStack getSpecialDrop(String pigeonType) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CONFIG_LOCATION));

            DropConfigFormat.DropData dropElement = switch(pigeonType.toLowerCase()) {
                case "antwerp_smerle_brown" -> gson.fromJson(reader, DropConfigFormat.class).getAntwerpSmerle("brown");
                case "antwerp_smerle_gray" -> gson.fromJson(reader, DropConfigFormat.class).getAntwerpSmerle("gray");
                case "egyptian_swift" -> gson.fromJson(reader, DropConfigFormat.class).getEgyptianSwift();
                default -> gson.fromJson(reader, DropConfigFormat.class).getCity();
            };
                Item item = Registry.ITEM.get(new Identifier(dropElement.getItem()));

                return new ItemStack(item, dropElement.getCount());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Pigeons.LOGGER.warn("The config file for Pleasant Pigeons could not be found, and the pigeon type '"
                + pigeonType + "' will use the default configuration for the 'city' type.");
        return new ItemStack(Items.DIAMOND);
    }

    public static int getDropChanceDay() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CONFIG_LOCATION));

            return gson.fromJson(reader, DropConfigFormat.class).getDropChanceDay();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Pigeons.LOGGER.warn("The config file for Pleasant Pigeons could not be found," +
                "and the 'dropChanceDay' value will be the default of '17000'");
        return 17000;
    }

    public static int getDropChanceNight() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CONFIG_LOCATION));

            return gson.fromJson(reader, DropConfigFormat.class).getDropChanceNight();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Pigeons.LOGGER.warn("The config file for Pleasant Pigeons could not be found," +
                "and the 'dropChanceNight' value will be the default of '5700'");
        return 5700;
    }

    public static int getSpecialDropChance() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CONFIG_LOCATION));

            return gson.fromJson(reader, DropConfigFormat.class).getSpecialDropChance();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Pigeons.LOGGER.warn("The config file for Pleasant Pigeons could not be found," +
                "and the 'specialDropChance' value will be the default of '100'");
        return 100;
    }
}
