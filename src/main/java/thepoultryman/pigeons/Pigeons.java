package thepoultryman.pigeons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thepoultryman.pigeons.entity.PigeonEntity;
import thepoultryman.pigeons.registry.ItemRegistry;

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
    public static final TagKey<Biome> PIGEON_SPAWN_BIOMES_H = TagKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID, "pigeon_spawn_biomes_high"));
    public static final TagKey<Biome> PIGEON_SPAWN_BIOMES_M = TagKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID, "pigeon_spawn_biomes_medium"));
    public static final TagKey<Biome> PIGEON_SPAWN_BIOMES_L = TagKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID, "pigeon_spawn_biomes_low"));
    public static final TagKey<Item> PIGEON_LIKE_FOODS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "pigeon_like_foods"));
    public static final TagKey<Item> PIGEON_LOVE_FOODS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "pigeon_love_foods"));

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing a pigeon army");

        // Pigeon Spawner
        BiomeModifications.addSpawn(BiomeSelectors.tag(PIGEON_SPAWN_BIOMES_H), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 65, 1, 9);
        BiomeModifications.addSpawn(BiomeSelectors.tag(PIGEON_SPAWN_BIOMES_M), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 45, 1, 6);
        BiomeModifications.addSpawn(BiomeSelectors.tag(PIGEON_SPAWN_BIOMES_L), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 15, 1, 2);

        // Items
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "pigeon_spawn_egg"), PIGEON_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "bread_crumbs"), BREAD_CRUMBS);

        ItemRegistry.registerItems();
    }
}
