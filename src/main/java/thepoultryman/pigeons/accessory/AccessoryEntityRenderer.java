package thepoultryman.pigeons.accessory;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import thepoultryman.pigeons.entity.PigeonEntity;

public class AccessoryEntityRenderer extends GeoEntityRenderer<PigeonEntity> {
    public AccessoryEntityRenderer(EntityRendererFactory.Context ctx, AnimatedGeoModel<PigeonEntity> modelProvider) {
        super(ctx, modelProvider);
    }
}
