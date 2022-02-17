package thepoultryman.pigeons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thepoultryman.pigeons.entity.PigeonEntity;

public class Pigeons implements ModInitializer {
    public static final String MOD_ID = "pigeons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final EntityType<PigeonEntity> PIGEON_ENTITY_TYPE = Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "pigeon"),
            FabricEntityTypeBuilder.<PigeonEntity>createMob().spawnGroup(SpawnGroup.CREATURE).entityFactory(PigeonEntity::new)
                    .defaultAttributes(PigeonEntity::createPigeonAttributes)
                    .dimensions(EntityDimensions.changing(0.45f, 0.5f))
                    .spawnRestriction(SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PigeonEntity::canSpawn).build());

    // Items
    public static final Item PIGEON_SPAWN_EGG = new SpawnEggItem(PIGEON_ENTITY_TYPE, 7830400, 7628935, new Item.Settings().group(ItemGroup.MISC));
    public static final Item BREAD_CRUMBS = new Item(new Item.Settings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).build()));

    // Tags
    public static final Tag<Item> PIGEON_LIKE_FOODS = TagFactory.ITEM.create(new Identifier(MOD_ID, "pigeon_like_foods"));
    public static final Tag<Item> PIGEON_LOVE_FOODS = TagFactory.ITEM.create(new Identifier(MOD_ID, "pigeon_love_foods"));

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing a pigeon army");

        // Pigeon Spawner
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.SAVANNA), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 85, 1, 7);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.PLAINS), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 75, 2, 9);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.FOREST), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 65, 3, 5);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.JUNGLE), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 25, 2, 5);

        // Items
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "pigeon_spawn_egg"), PIGEON_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "bread_crumbs"), BREAD_CRUMBS);
    }
}
