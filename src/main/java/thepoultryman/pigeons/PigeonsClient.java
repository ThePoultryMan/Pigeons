package thepoultryman.pigeons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import thepoultryman.pigeons.enitityrenderer.PigeonEntityRenderer;

@Environment(EnvType.CLIENT)
public class PigeonsClient implements ClientModInitializer {
    public static final EntityModelLayer PIGEON_MODEL_LAYER = new EntityModelLayer(new Identifier(Pigeons.MOD_ID, "pigeon"), "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(Pigeons.PIGEON_ENTITY_TYPE, PigeonEntityRenderer::new);
    }
}
