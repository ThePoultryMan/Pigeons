package thepoultryman.pigeons.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PigeonsConfig {
    private final FileConfig config;

    private ItemStack cityDrop;
    private ItemStack antwerpBrownDrop;
    private ItemStack antwerpGrayDrop;
    private ItemStack egyptianDrop;
    private int dropChanceDay;
    private int dropChanceNight;
    private int specialDropChance;

    public PigeonsConfig() {
        this.config = FileConfig.builder(FabricLoader.getInstance().getConfigDir() + "/pleasant-pigeons.toml").defaultResource("/pleasant-pigeons.toml").autosave().build();
    }

    public void loadConfig() {
        this.config.load();

        this.cityDrop = this.getItemStack(this.config.getOrElse("special_drops.city.item", "minecraft:diamond"),
                this.config.getOrElse("special_drops.city.count", 1));
        this.antwerpBrownDrop = this.getItemStack(this.config.getOrElse("special_drops.antwerp_brown.item", "minecraft:raw_iron"),
                this.config.getOrElse("special_drops.antwerp_brown.count", 3));
        this.antwerpGrayDrop = this.getItemStack(this.config.getOrElse("special_drops.antwerp_gray.item", "minecraft:raw_copper"),
                this.config.getOrElse("special_drops.antwerp_gray.count", 7));
        this.egyptianDrop = this.getItemStack(this.config.getOrElse("special_drops.egyptian.item", "minecraft:cooked_beef"),
                this.config.getOrElse("special_drops.egyptian.count", 5));

        this.dropChanceDay = this.config.getOrElse("drop_chances.day", 17000);
        this.dropChanceNight = this.config.getOrElse("drop_chances.night", 5700);
        this.specialDropChance = this.config.getOrElse("drop_chances.special", 100);
    }

    private ItemStack getItemStack(String itemIdentifier, int count) {
        Item item = Registry.ITEM.get(new Identifier(itemIdentifier));
        return new ItemStack(item, count);
    }

    public ItemStack getSpecialDrop(String pigeonType) {
        return switch (pigeonType) {
            case "city" -> this.cityDrop;
            case "antwerp_smerle_brown" -> this.antwerpBrownDrop;
            case "antwerp_smerle_gray" -> this.antwerpGrayDrop;
            case "egyptian_swift" -> this.egyptianDrop;
            default -> new ItemStack(Items.BREAD);
        };
    }

    public int getDropChanceDay() {
        return this.dropChanceDay;
    }

    public int getDropChanceNight() {
        return this.dropChanceNight;
    }

    public int getSpecialDropChance() {
        return this.specialDropChance;
    }
}
