package thepoultryman.pigeons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import thepoultryman.pigeons.enitityrenderer.PigeonEntityRenderer;

@Environment(EnvType.CLIENT)
public class PigeonsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(Pigeons.PIGEON_ENTITY_TYPE, PigeonEntityRenderer::new);
    }
}
