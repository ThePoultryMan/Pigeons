package thepoultryman.pigeons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thepoultryman.pigeons.entity.PigeonEntity;
import thepoultryman.pigeons.registry.ItemRegistry;
import thepoultryman.pigeons.screen.LetterScreen;
import thepoultryman.pigeons.screen.LetterScreenHandler;

public class Pigeons implements ModInitializer {
    public static final String MOD_ID = "pigeons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final EntityType<PigeonEntity> PIGEON_ENTITY_TYPE = Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "pigeon"),
            FabricEntityTypeBuilder.<PigeonEntity>createMob().spawnGroup(SpawnGroup.CREATURE).entityFactory(PigeonEntity::new)
                    .defaultAttributes(PigeonEntity::createPigeonAttributes)
                    .dimensions(EntityDimensions.changing(0.45f, 0.5f))
                    .spawnRestriction(SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PigeonEntity::canSpawn).build());

    // Tags
    public static final TagKey<Item> PIGEON_LIKE_FOODS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "pigeon_like_foods"));
    public static final TagKey<Item> PIGEON_LOVE_FOODS = TagKey.of(Registry.ITEM_KEY, new Identifier(MOD_ID, "pigeon_love_foods"));

    // Screen Stuff
    public static final ScreenHandlerType<LetterScreenHandler> LETTER_SCREEN_TYPE = ScreenHandlerRegistry.registerExtended(new Identifier(MOD_ID, "letter_screen"),LetterScreenHandler::new);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing a pigeon army");

        // Pigeon Spawner
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.PLAINS), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 85, 2, 9);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.SAVANNA), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 80, 1, 7);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.FOREST), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 75, 3, 5);
        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.JUNGLE), SpawnGroup.CREATURE, PIGEON_ENTITY_TYPE, 40, 2, 5);

        ItemRegistry.registerItems();

        ScreenRegistry.register(LETTER_SCREEN_TYPE, LetterScreen::new);
    }
}
