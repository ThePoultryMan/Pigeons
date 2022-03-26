package thepoultryman.pigeons.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
    public static DropElements.DropData cityDrop = new DropElements.DropData("minecraft:diamond", 1);
    public static DropElements.DropData antwerpSmerleBrownDrop = new DropElements.DropData("minecraft:raw_iron", 3);
    public static DropElements.DropData antwerpSmerleBrownGray = new DropElements.DropData("minecraft:raw_copper", 7);
    public static DropElements.DropData egyptianSwift = new DropElements.DropData("minecraft:cooked_beef", 5);
    public JsonElement configJson = gson.toJsonTree(new DropElements(cityDrop, antwerpSmerleBrownDrop, antwerpSmerleBrownGray, egyptianSwift));

    @Override
    public void onInitialize() {
        if (!new File(CONFIG_LOCATION).exists()) {
            // Start the writing process
            FileWriter writer = null;
            try {
                writer = new FileWriter(CONFIG_LOCATION);

                JsonObject finalJson = new JsonObject();
                finalJson.add("special_drops", configJson);

                gson.toJson(finalJson, writer);

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

            DropElements.DropData dropElement = switch(pigeonType.toLowerCase()) {
                case "antwerp_smerle_brown" -> gson.fromJson(reader, DropElements.class).getAntwerpSmerle("brown");
                case "antwerp_smerle_gray" -> gson.fromJson(reader, DropElements.class).getAntwerpSmerle("gray");
                case "egyptian_swift" -> gson.fromJson(reader, DropElements.class).getEgyptianSwift();
                default -> gson.fromJson(reader, DropElements.class).getCity();
            };
                Item item = Registry.ITEM.get(new Identifier(dropElement.getItem()));

                return new ItemStack(item, dropElement.getCount());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Pigeons.LOGGER.warn("There was an error finding your config file for Pleasant Pigeons.");
        return new ItemStack(Items.COAL, 64);
    }
}
