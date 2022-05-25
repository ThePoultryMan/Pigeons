package thepoultryman.pigeons.item;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import thepoultryman.pigeons.screen.LetterScreenHandler;

public class Letter extends Item implements ExtendedScreenHandlerFactory {
    public Letter(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);

        if (world.isClient()) {
            return TypedActionResult.success(stackInHand, world.isClient());
        } else {
            openTestScreen(user);
            return TypedActionResult.success(stackInHand);
        }
    }

    private static void openTestScreen(PlayerEntity player) {
        if (player.getWorld() != null && !player.getWorld().isClient()) {
            player.openHandledScreen(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                    buf.writeInt(1);
                }

                @Override
                public Text getDisplayName() {
                    return new TranslatableText("item.pigeon.letter");
                }

                @Nullable
                @Override
                public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    return new LetterScreenHandler(i, playerInventory, 13);
                }
            });
        }
    }

    // Extend Screen Handler Factory

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(1);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("item.pigeons.letter");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new Generic3x3ContainerScreenHandler(i, playerInventory);
    }
}
