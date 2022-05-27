package thepoultryman.pigeons.screen;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;

public class MessageScreen extends CottonClientScreen {
    public static boolean closeScreen = false;

    public MessageScreen(GuiDescription description) {
        super(description);
    }

    public static void closeScreen() {
        closeScreen = true;
    }

    @Override
    public void tick() {
        if (closeScreen) this.onClose();
        closeScreen = false;
    }
}
