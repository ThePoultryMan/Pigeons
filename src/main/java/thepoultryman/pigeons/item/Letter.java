package thepoultryman.pigeons.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import thepoultryman.pigeons.screen.LetterGui;
import thepoultryman.pigeons.screen.LetterScreen;
import thepoultryman.pigeons.screen.MessageGui;
import thepoultryman.pigeons.screen.MessageScreen;

import java.util.List;

public class Letter extends BundleItem {
    public Letter(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (!LetterHelper.isSealed(stack))
            return super.onStackClicked(stack, slot, clickType, player);
        else
            return false;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (!LetterHelper.isSealed(stack))
            return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
        else
            return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        if (!LetterHelper.isSealed(stackInHand)) {
            if (world.isClient()) {
                MinecraftClient.getInstance().setScreen(new LetterScreen(new LetterGui(stackInHand)));
            }
            return TypedActionResult.success(stackInHand, world.isClient());
        } else if (LetterHelper.isDelivered(stackInHand)) {
            if (world.isClient()) {
                MinecraftClient.getInstance().setScreen(new MessageScreen(new MessageGui(LetterHelper.getMessage(stackInHand))));
            }
            return super.use(world, user, hand);
        } else {
            return TypedActionResult.fail(stackInHand);
        }
    }

    @Override
    public boolean canBeNested() {
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (LetterHelper.isSealed(stack))
            tooltip.add(new TranslatableText("pigeons.letter.sealed").formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    public static class LetterHelper {
        public static void sealLetter(ItemStack letterStack, int[] coordinates, String message) {
            if (letterStack.getItem() instanceof Letter letterItem) {
                NbtCompound letterNbt = letterStack.getOrCreateNbt();
                // Seal the letter
                letterNbt.putBoolean("Sealed", true);
                // Save the destination coordinates to NBT
                letterNbt.putIntArray("Destination", coordinates);
                // Save the message to NBT
                letterNbt.putString("Message", message);
            }
        }

        public static boolean isSealed(ItemStack letterStack) {
            NbtCompound nbtCompound = letterStack.getOrCreateNbt();
            if (nbtCompound.contains("Sealed")) {
                return nbtCompound.getBoolean("Sealed");
            } else {
                return false;
            }
        }

        public static int[] getDestinationCoordinates(ItemStack letterStack) {
            NbtCompound letterNbt = letterStack.getOrCreateNbt();
            if (letterNbt.contains("Destination")) {
                return letterNbt.getIntArray("Destination");
            } else {
                return new int[] {0, 0, 0};
            }
        }

        public static void setDelivered(ItemStack letterStack, boolean delivered) {
            letterStack.getOrCreateNbt().putBoolean("Delivered", delivered);
        }

        public static boolean isDelivered(ItemStack letterStack) {
            return letterStack.getOrCreateNbt().getBoolean("Delivered");
        }

        public static String getMessage(ItemStack letterStack) {
            return letterStack.getOrCreateNbt().getString("Message");
        }
    }
}
