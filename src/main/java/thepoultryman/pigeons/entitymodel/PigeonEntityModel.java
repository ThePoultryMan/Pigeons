package thepoultryman.pigeons.entitymodel;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
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
}
