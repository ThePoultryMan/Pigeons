package thepoultryman.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.pigeons.Pigeons;

public class ItemRegistry {
    public static final Item TOP_HAT = new Item(new Item.Settings().group(ItemGroup.TOOLS));

    public static void registerItems() {
        register("top_hat", TOP_HAT);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Pigeons.MOD_ID, name), item);
    }
}
