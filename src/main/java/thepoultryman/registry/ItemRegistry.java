package thepoultryman.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.pigeons.Pigeons;
import thepoultryman.pigeons.accessory.item.AccessoryItem;

public class ItemRegistry {
    // Accessories
    public static final AccessoryItem TOP_HAT = new AccessoryItem(new Item.Settings().group(ItemGroup.TOOLS));
    public static final AccessoryItem BEANIE = new AccessoryItem(new Item.Settings().group(ItemGroup.TOOLS));

    public static void registerItems() {
        // Accessories
        register("top_hat", TOP_HAT);
        register("beanie", BEANIE);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Pigeons.MOD_ID, name), item);
    }
}
