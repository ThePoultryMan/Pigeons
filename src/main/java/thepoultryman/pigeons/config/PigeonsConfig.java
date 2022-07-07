package thepoultryman.pigeons.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PigeonsConfig {
    private final FileConfig config;

    private ItemStack cityDrop;
    private ItemStack antwerpBrownDrop;
    private ItemStack antwerpGrayDrop;
    private ItemStack egyptianDrop;

    public PigeonsConfig() {
        this.config = FileConfig.builder(FabricLoader.getInstance().getConfigDir() + "/pleasant-pigeons.toml").defaultResource("/pleasant-pigeons.toml").autosave().build();
    }

    public void loadConfig() {
        this.config.load();

        this.cityDrop = this.getSpecialDrop(this.config.getOrElse("special_drops.city.item", "minecraft:diamond"),
                this.config.getOrElse("special_drops.city.count", 1));
        this.antwerpBrownDrop = this.getSpecialDrop(this.config.getOrElse("special_drops.antwerp_brown.item", "minecraft:raw_iron"),
                this.config.getOrElse("special_drops.antwerp_brown.count", 3));
        this.antwerpGrayDrop = this.getSpecialDrop(this.config.getOrElse("special_drops.antwerp_gray.item", "minecraft:raw_copper"),
                this.config.getOrElse("special_drops.antwerp_gray.count", 7));
        this.egyptianDrop = this.getSpecialDrop(this.config.getOrElse("special_drops.egyptian.item", "minecraft:cooked_beef"),
                this.config.getOrElse("special_drops.egyptian.count", 5));
    }

    private ItemStack getSpecialDrop(String itemIdentifier, int count) {
        Item item = Registry.ITEM.get(new Identifier(itemIdentifier));
        return new ItemStack(item, count);
    }
}
