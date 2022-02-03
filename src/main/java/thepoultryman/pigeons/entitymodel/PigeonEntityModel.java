package thepoultryman.pigeons.entitymodel;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import thepoultryman.pigeons.Pigeons;
import thepoultryman.pigeons.entity.PigeonEntity;

public class PigeonEntityModel extends AnimatedGeoModel<PigeonEntity> {

    @Override
    public Identifier getModelLocation(PigeonEntity object) {
        return new Identifier(Pigeons.MOD_ID, "geo/pigeon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PigeonEntity object) {
        return new Identifier(Pigeons.MOD_ID, "textures/entity/pigeon/pigeon1.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PigeonEntity animatable) {
        return new Identifier(Pigeons.MOD_ID, "animations/pigeon.animation.json");
    }

    @Override
    public void setLivingAnimations(PigeonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");
        if (head != null && entity.getIdle() == 0) {
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
