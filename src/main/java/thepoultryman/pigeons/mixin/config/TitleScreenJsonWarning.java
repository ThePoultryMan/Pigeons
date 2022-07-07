package thepoultryman.pigeons.mixin.config;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thepoultryman.pigeons.Pigeons;

@Mixin(TitleScreen.class)
public class TitleScreenJsonWarning extends Screen {
    protected TitleScreenJsonWarning(Text text) {
        super(text);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void pigeons$renderJsonWarning(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (Pigeons.CONFIG.doesJsonConfigExist()) {
            DrawableHelper.drawCenteredText(matrices, this.textRenderer, Text.translatable("config.pigeons.json_file_warning1"), this.width / 2, 90, 16777215);
            DrawableHelper.drawCenteredText(matrices, this.textRenderer, Text.translatable("config.pigeons.json_file_warning2"), this.width / 2, 100, 16777215);
        }
    }
}
