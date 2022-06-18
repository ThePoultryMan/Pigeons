package thepoultryman.pigeons.enitityrenderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import thepoultryman.pigeons.accessory.AccessoryEntityRenderer;
import thepoultryman.pigeons.accessory.AccessoryFeatureRenderer;
import thepoultryman.pigeons.entity.PigeonEntity;
import thepoultryman.pigeons.entitymodel.PigeonEntityModel;

public class PigeonEntityRenderer extends GeoEntityRenderer<PigeonEntity> {
    public PigeonEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PigeonEntityModel());
        this.shadowRadius = 0.15f;
        this.addLayer(new AccessoryFeatureRenderer(this, new AccessoryEntityRenderer(context, new PigeonEntityModel())));
    }

    public Identifier getTexture(PigeonEntity entity) {
        return this.getTextureResource(entity);
    }
}
