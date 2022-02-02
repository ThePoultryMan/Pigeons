package thepoultryman.pigeons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
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
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PigeonEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing a pigeon army");

        FabricDefaultAttributeRegistry.register(PIGEON_ENTITY_TYPE, PigeonEntity.createMobAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.45D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D));
    }
}
