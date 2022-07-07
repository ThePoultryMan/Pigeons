package thepoultryman.pigeons.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import thepoultryman.pigeons.Pigeons;

import java.io.File;

public class PigeonsConfig {
    private final FileConfig config;

    private boolean jsonFileExists = false;

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

        if (this.useJsonConfig()) {
            return;
        }

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

    public void completelySaveConfig() {
        this.config.set("drop_chances.day", this.dropChanceDay);
        this.config.set("drop_chances.night", this.dropChanceNight);
        this.config.set("drop_chances.special", this.specialDropChance);
        this.config.set("special_drops.city.item", Registry.ITEM.getId(this.cityDrop.getItem()).toString());
        this.config.set("special_drops.city.count", this.cityDrop.getCount());
        this.config.set("special_drops.antwerp_brown.item", Registry.ITEM.getId(this.antwerpBrownDrop.getItem()).toString());
        this.config.set("special_drops.antwerp_brown.count", this.antwerpBrownDrop.getCount());
        this.config.set("special_drops.antwerp_gray.item", Registry.ITEM.getId(this.antwerpGrayDrop.getItem()).toString());
        this.config.set("special_drops.antwerp_gray.count", this.antwerpGrayDrop.getCount());
        this.config.set("special_drops.egyptian.item", Registry.ITEM.getId(this.egyptianDrop.getItem()).toString());
        this.config.set("special_drops.egyptian.count", this.egyptianDrop.getCount());

        Pigeons.LOGGER.debug("The Pleasant Pigeons config file has been completely saved.");
    }

    private boolean useJsonConfig() {
        if (new File(FabricLoader.getInstance().getConfigDir() + "/pleasant-pigeons.json").exists()) {
            Pigeons.LOGGER.info("Pleasant Pigeons has moved to a TOML config system. All of your changes in the JSON config file are being moved over to the new TOML file.");
            this.jsonFileExists = true;
            this.cityDrop = DropConfig.getSpecialDrop("city");
            this.antwerpBrownDrop = DropConfig.getSpecialDrop("antwerp_smerle_brown");
            this.antwerpGrayDrop = DropConfig.getSpecialDrop("antwerp_smerle_gray");
            this.egyptianDrop = DropConfig.getSpecialDrop("egyptian_swift");
            this.dropChanceDay = DropConfig.getDropChanceDay();
            this.dropChanceNight = DropConfig.getDropChanceNight();
            this.specialDropChance = DropConfig.getSpecialDropChance();
            this.completelySaveConfig();

            Pigeons.LOGGER.info("The config-file conversion has been completed, and you should now delete the old JSON file.");

            return true;
        } else {
            return false;
        }
    }

    public boolean doesJsonConfigExist() {
        return this.jsonFileExists;
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
