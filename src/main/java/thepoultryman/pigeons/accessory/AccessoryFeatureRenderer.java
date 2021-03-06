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
    // Texture Locations
    private final static Identifier TOP_HAT_LOCATION = new Identifier(Pigeons.MOD_ID, "textures/entity/pigeon/accessories/top_hat.png");
    private final static Identifier BEANIE_LOCATION = new Identifier(Pigeons.MOD_ID, "textures/entity/pigeon/accessories/beanie.png");
    private final static Identifier DRESS_SHOES_LOCATION = new Identifier(Pigeons.MOD_ID, "/textures/entity/pigeon/accessories/dress_shoes.png");
    private final static Identifier TIE_LOCATION = new Identifier(Pigeons.MOD_ID, "/textures/entity/pigeon/accessories/tie.png");
    private final static Identifier MOSS_LOCATION = new Identifier(Pigeons.MOD_ID, "/textures/entity/pigeon/accessories/moss.png");

    public AccessoryFeatureRenderer(IGeoRenderer<PigeonEntity> entityRendererIn, AccessoryEntityRenderer partyHatEntityRenderer) {
        super(entityRendererIn);
        this.accessoryEntityRenderer = partyHatEntityRenderer;
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, PigeonEntity pigeonEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!pigeonEntity.getAccessory().equals("none")) {
            switch (pigeonEntity.getAccessory()) {
                case "top_hat" -> accessoryEntityRenderer.render(getEntityModel().getModel(new Identifier(Pigeons.MOD_ID, MODEL_LOCATION)),
                        pigeonEntity, partialTicks,
                        RenderLayer.getEntityCutout(TOP_HAT_LOCATION), matrixStackIn, bufferIn,
                        bufferIn.getBuffer(RenderLayer.getEntityCutout(TOP_HAT_LOCATION)),
                        packedLightIn, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
                case "beanie" -> accessoryEntityRenderer.render(getEntityModel().getModel(new Identifier(Pigeons.MOD_ID, MODEL_LOCATION)),
                        pigeonEntity, partialTicks,
                        RenderLayer.getEntityCutout(BEANIE_LOCATION), matrixStackIn, bufferIn,
                        bufferIn.getBuffer(RenderLayer.getEntityCutout(BEANIE_LOCATION)),
                        packedLightIn, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
                case "dress_shoes" -> accessoryEntityRenderer.render(getEntityModel().getModel(new Identifier(Pigeons.MOD_ID, MODEL_LOCATION)),
                        pigeonEntity, partialTicks,
                        RenderLayer.getEntityCutout(DRESS_SHOES_LOCATION), matrixStackIn, bufferIn,
                        bufferIn.getBuffer(RenderLayer.getEntityCutout(DRESS_SHOES_LOCATION)),
                        packedLightIn, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
                case "tie" -> accessoryEntityRenderer.render(getEntityModel().getModel(new Identifier(Pigeons.MOD_ID, MODEL_LOCATION)),
                        pigeonEntity, partialTicks,
                        RenderLayer.getEntityCutout(TIE_LOCATION), matrixStackIn, bufferIn,
                        bufferIn.getBuffer(RenderLayer.getEntityCutout(TIE_LOCATION)),
                        packedLightIn, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
                case "moss_carpet" -> accessoryEntityRenderer.render(getEntityModel().getModel(new Identifier(Pigeons.MOD_ID, MODEL_LOCATION)),
                        pigeonEntity, partialTicks,
                        RenderLayer.getEntityCutout(MOSS_LOCATION), matrixStackIn, bufferIn,
                        bufferIn.getBuffer(RenderLayer.getEntityCutout(MOSS_LOCATION)),
                        packedLightIn, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
            }
        }
    }
}
