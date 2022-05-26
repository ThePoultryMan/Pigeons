package thepoultryman.pigeons.screen;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class LetterGui extends LightweightGuiDescription {
    private static final Text ENTER_COORDS_TEXT = new TranslatableText("gui.pigeons.info.enterCoords");
    private static final Text[] INVALID_COORD_TEXT = new Text[] {new TranslatableText("gui.pigeons.info.coordX"),
            new TranslatableText("gui.pigeons.info.coordY"), new TranslatableText("gui.pigeons.info.coordZ")};

    public LetterGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(198, 162);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel title = new WLabel(new TranslatableText("item.pigeons.letter"));
        root.add(title, 0, 0);

        WTextField messageField = new WTextField(new TranslatableText("gui.pigeons.messageField"));
        messageField.setMaxLength(128);
        root.add(messageField, 2, 1, 6, 1);

        WText infoText = new WText(ENTER_COORDS_TEXT);
        infoText.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(infoText, 1, 5, 8, 1);

        WTextField xCoord = new WTextField(new TranslatableText("gui.pigeons.xCoord"));
        xCoord.setChangedListener((s -> {
            infoText.setText(changeInfoText(xCoord.getText(), 0));
        }));
        root.add(xCoord, 1, 3, 2, 1);
        WTextField yCoord = new WTextField(new TranslatableText("gui.pigeons.yCoord"));
        yCoord.setChangedListener((s -> {
            infoText.setText(changeInfoText(yCoord.getText(), 1));
        }));
        root.add(yCoord, 4, 3, 2, 1);
        WTextField zCoord = new WTextField(new TranslatableText("gui.pigeons.zCoord"));
        zCoord.setChangedListener((s -> {
            infoText.setText(changeInfoText(zCoord.getText(), 2));
        }));
        root.add(zCoord, 7, 3, 2, 1);

        WButton button = new WButton(new TranslatableText("gui.pigeons.sealLetter"));
        root.add(button, 3, 7, 4, 1);

        root.validate(this);
    }

    private static Text changeInfoText(String fieldText, int coordInt) {
        try {
            Integer coord = Integer.valueOf(fieldText);
        } catch (NumberFormatException ignored) {
            if (!fieldText.equals(""))
                return INVALID_COORD_TEXT[coordInt];
        }

        return new TranslatableText("");
    }
}
