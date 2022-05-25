package thepoultryman.pigeons.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import thepoultryman.pigeons.Pigeons;

public class LetterScreenHandler extends ScreenHandler {
    public LetterScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, buf.readInt());
    }

    public LetterScreenHandler(int syncId, PlayerInventory playerInventory, int i) {
        super(Pigeons.LETTER_SCREEN_TYPE, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
