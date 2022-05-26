package thepoultryman.pigeons.screen;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.TranslatableText;

public class LetterGui extends LightweightGuiDescription {
    public LetterGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(162, 144);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel title = new WLabel(new TranslatableText("item.pigeons.letter"));
        root.add(title, 0, 0);

        WButton button = new WButton(new TranslatableText("gui.pigeons.sealLetter"));
        root.add(button, 2, 6, 4, 1);

        root.validate(this);
    }
}
