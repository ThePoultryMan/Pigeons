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

    public PigeonsConfig() {
        this.config = FileConfig.builder(FabricLoader.getInstance().getConfigDir() + "/pleasant-pigeons.toml").defaultResource("/pleasant-pigeons.toml").autosave().build();
    }

    public void loadConfig() {
        this.config.load();

        this.cityDrop = this.getSpecialDrop(this.config.get("special_drops.city.item"), this.config.get("special_drops.city.count"));
    }

    private ItemStack getSpecialDrop(String itemIdentifier, int count) {
        Item item = Registry.ITEM.get(new Identifier(itemIdentifier));
        return new ItemStack(item, count);
    }
}
