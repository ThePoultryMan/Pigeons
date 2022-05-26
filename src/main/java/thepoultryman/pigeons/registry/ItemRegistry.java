package thepoultryman.pigeons.registry;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.pigeons.Pigeons;
import thepoultryman.pigeons.item.Letter;

public class ItemRegistry {
    // Spawn Egg
    public static final Item PIGEON_SPAWN_EGG = new SpawnEggItem(Pigeons.PIGEON_ENTITY_TYPE, 7830400, 7628935, new Item.Settings().group(ItemGroup.MISC));
    // Foods
    public static final Item BREAD_CRUMBS = new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).build()));
    // Accessories
    public static final Item TOP_HAT = new Item(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item BEANIE = new Item(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item DRESS_SHOES = new Item(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item TIE = new Item(new Item.Settings().group(ItemGroup.TOOLS));
    // Other
    public static final Letter LETTER = new Letter(new Item.Settings().group(ItemGroup.MISC).maxCount(1));

    public static void registerItems() {
        // Spawn Egg
        register("pigeon_spawn_egg", PIGEON_SPAWN_EGG);
        // Foods
        register("bread_crumbs", BREAD_CRUMBS);
        // Accessories
        register("top_hat", TOP_HAT);
        register("beanie", BEANIE);
        register("dress_shoes", DRESS_SHOES);
        register("tie", TIE);
        // Other
        register("letter", LETTER);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Pigeons.MOD_ID, name), item);
    }
}
