package thepoultryman.pigeons.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.pigeons.Pigeons;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DropConfig {
    private static final String CONFIG_LOCATION = FabricLoader.getInstance().getConfigDir() + "/pleasant-pigeons.json";
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
