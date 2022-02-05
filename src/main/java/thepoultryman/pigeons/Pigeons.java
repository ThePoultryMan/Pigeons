package thepoultryman.pigeons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thepoultryman.pigeons.entity.PigeonEntity;

public class Pigeons implements ModInitializer {
    public static final String MOD_ID = "pigeons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final EntityType<PigeonEntity> PIGEON_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(MOD_ID, "pigeon"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PigeonEntity::new).dimensions(EntityDimensions.fixed(0.375f, 0.5f)).build()
    );

    public static final Item PIGEON_SPAWN_EGG = new SpawnEggItem(PIGEON_ENTITY_TYPE, 12895428, 11382189, new Item.Settings().group(ItemGroup.MISC));

    // Tags
    public static final Tag<Item> PIGEON_LIKE_FOODS = TagFactory.ITEM.create(new Identifier("c", "pigeon_like_foods"));

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing a pigeon army");

        FabricDefaultAttributeRegistry.register(PIGEON_ENTITY_TYPE, PigeonEntity.createMobAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.65D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D));
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "pigeon_spawn_egg"), PIGEON_SPAWN_EGG);
    }
}
