package thepoultryman.pigeons.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class LetterScreen extends Screen {
    public LetterScreen() {
        super(NarratorManager.EMPTY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int i = (this.width - 192) / 2;
        TranslatableText text = new TranslatableText("item.pigeons.letter");
        this.textRenderer.draw(matrices, text, (float)(i + 36 + (114 - this.textRenderer.getWidth(text)) / 2), 34.0F, 0x000000);
    }
}
