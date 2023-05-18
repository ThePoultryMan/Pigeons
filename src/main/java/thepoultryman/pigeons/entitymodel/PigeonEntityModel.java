package thepoultryman.pigeons.entitymodel;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import thepoultryman.pigeons.Pigeons;
import thepoultryman.pigeons.entity.PigeonEntity;

public class PigeonEntityModel extends GeoModel<PigeonEntity> {

    @Override
    public Identifier getModelResource(PigeonEntity object) {
        return new Identifier(Pigeons.MOD_ID, "geo/pigeon.geo.json");
    }

    @Override
    public Identifier getTextureResource(PigeonEntity pigeonEntity) {
        return new Identifier(Pigeons.MOD_ID, "textures/entity/pigeon/pigeon_" + pigeonEntity.getPigeonTypeString() + ".png");
    }

    @Override
    public Identifier getAnimationResource(PigeonEntity animatable) {
        return new Identifier(Pigeons.MOD_ID, "animations/pigeon.animation.json");
    }

    public void setLivingAnimations(PigeonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");
        if (head != null && entity.getIdle() == 0) {
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180f));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180f));
        }

        IBone root = this.getAnimationProcessor().getBone("root");
        if (root != null && entity.isBaby()) {
            root.setScaleX(0.65f);
            root.setScaleY(0.65f);
            root.setScaleZ(0.65f);
        }

        IBone topHatExtension = this.getAnimationProcessor().getBone("top_hat_extension");
        if (entity.getAccessory().equals("top_hat") && entity.getPigeonTypeString().equals("city")) {
            if (topHatExtension != null) {
                topHatExtension.setHidden(false);
            }
        } else topHatExtension.setHidden(true);
    }
}
