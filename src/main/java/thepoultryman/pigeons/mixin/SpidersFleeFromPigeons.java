package thepoultryman.pigeons.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thepoultryman.pigeons.entity.PigeonEntity;

@Mixin(SpiderEntity.class)
public class SpidersFleeFromPigeons extends HostileEntity {
    protected SpidersFleeFromPigeons(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    void initGoals(CallbackInfo ci) {
        this.goalSelector.add(4, new FleeEntityGoal<>(this, PigeonEntity.class, 9f, 0.8D, 1D));
    }
}
