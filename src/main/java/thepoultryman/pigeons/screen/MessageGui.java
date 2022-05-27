package thepoultryman.pigeons.screen;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class MessageGui extends LightweightGuiDescription {
    public MessageGui(String message) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(126, 162);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel title = new WLabel(new TranslatableText("item.pigeons.letter"));
        root.add(title, 0, 0);

        WText infoText = new WText(new LiteralText(message));
        root.add(infoText, 1, 1, 4, 1);

        root.validate(this);
    }
}
