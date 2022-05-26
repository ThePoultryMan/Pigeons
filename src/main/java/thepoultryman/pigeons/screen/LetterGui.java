package thepoultryman.pigeons.screen;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import thepoultryman.pigeons.item.Letter;

public class LetterGui extends LightweightGuiDescription {
    private static final Text ENTER_COORDS_TEXT = new TranslatableText("gui.pigeons.info.enterCoords");
    private static final Text[] INVALID_COORD_TEXT = new Text[] {new TranslatableText("gui.pigeons.info.invalid.coordX"),
            new TranslatableText("gui.pigeons.info.invalid.coordY"), new TranslatableText("gui.pigeons.info.invalid.coordZ")};
    private static final Text[] MISSING_COORD_TEXT = new Text[] {new TranslatableText("gui.pigeons.info.missing.coordX"),
            new TranslatableText("gui.pigeons.info.missing.coordY"), new TranslatableText("gui.pigeons.info.missing.coordZ")};

    public LetterGui(ItemStack letterItemStack) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(198, 162);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel title = new WLabel(new TranslatableText("item.pigeons.letter"));
        root.add(title, 0, 0);

        WText infoText = new WText(ENTER_COORDS_TEXT);
        infoText.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(infoText, 1, 5, 8, 1);

        WTextField[] coordinateFields = new WTextField[] {new WTextField(new TranslatableText("gui.pigeons.xCoord")),
                new WTextField(new TranslatableText("gui.pigeons.yCoord")), new WTextField(new TranslatableText("gui.pigeons.zCoord"))};
        root.add(coordinateFields[0], 1, 3, 2, 1);
        root.add(coordinateFields[1], 4, 3, 2, 1);
        root.add(coordinateFields[2], 7, 3, 2, 1);

        WButton sealButton = new WButton(new TranslatableText("gui.pigeons.sealLetter"));
        sealButton.setEnabled(false);
        sealButton.setOnClick(() -> {
            Letter.setSealed(true, letterItemStack);
            LetterScreen.closeScreen();
        });
        root.add(sealButton, 3, 7, 4, 1);

        WTextField messageField = new WTextField(new TranslatableText("gui.pigeons.messageField"));
        messageField.setMaxLength(128);
        messageField.setChangedListener(s -> updateInfoAndButton(coordinateFields, infoText, sealButton, messageField.getText(), letterItemStack));
        root.add(messageField, 2, 1, 6, 1);

        coordinateFields[0].setChangedListener(s -> updateInfoAndButton(coordinateFields, infoText, sealButton, messageField.getText(), letterItemStack));
        coordinateFields[1].setChangedListener(s -> updateInfoAndButton(coordinateFields, infoText, sealButton, messageField.getText(), letterItemStack));
        coordinateFields[2].setChangedListener(s -> updateInfoAndButton(coordinateFields, infoText, sealButton, messageField.getText(), letterItemStack));

        root.validate(this);
    }

    private static void updateInfoAndButton(WTextField[] coordinateFields, WText infoText, WButton sealButton, String message, ItemStack letterItemStack) {
        for (int i = 0; i < coordinateFields.length; ++i) {
            String fieldText = coordinateFields[i].getText();
            Integer coordinate = tryIntegerParse(fieldText);
            if (coordinate == null && !fieldText.equals("")) {
                infoText.setText(INVALID_COORD_TEXT[i]);
                sealButton.setEnabled(false);
                return;
            } else if (fieldText.equals("")) {
                infoText.setText(MISSING_COORD_TEXT[i]);
                sealButton.setEnabled(false);
                return;
            } else {
                infoText.setText(new TranslatableText("gui.pigeons.info.ready"));
                sealButton.setEnabled(true);
            }
        }

        if (message.equals("") && !letterItemStack.getOrCreateNbt().contains("Items")) {
            infoText.setText(new TranslatableText("gui.pigeons.info.needsMessageOrItem"));
            sealButton.setEnabled(false);
        }
    }

    private static Integer tryIntegerParse(String string) {
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
