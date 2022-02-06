package thepoultryman.pigeons.accessory;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import thepoultryman.pigeons.Pigeons;
import thepoultryman.pigeons.entity.PigeonEntity;

public class AccessoryFeatureRenderer extends GeoLayerRenderer<PigeonEntity> {
    private final static String MODEL_LOCATION = "geo/pigeon.geo.json";
    private final AccessoryEntityRenderer accessoryEntityRenderer;
    private final static Identifier TOP_HAT_LOCATION = new Identifier(Pigeons.MOD_ID, "textures/entity/pigeon/accessories/top_hat.png");

    public AccessoryFeatureRenderer(IGeoRenderer<PigeonEntity> entityRendererIn, AccessoryEntityRenderer partyHatEntityRenderer) {
        super(entityRendererIn);
        this.accessoryEntityRenderer = partyHatEntityRenderer;
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, PigeonEntity pigeonEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!pigeonEntity.getAccessory().equals("none")) {
            switch(pigeonEntity.getAccessory()) {
                case "top_hat":
                    accessoryEntityRenderer.render(getEntityModel().getModel(new Identifier(Pigeons.MOD_ID, MODEL_LOCATION)),
                            pigeonEntity, partialTicks,
                            RenderLayer.getEntityCutout(TOP_HAT_LOCATION), matrixStackIn, bufferIn,
                            bufferIn.getBuffer(RenderLayer.getEntityCutout(TOP_HAT_LOCATION)),
                            packedLightIn, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
            }
        }
    }
}
