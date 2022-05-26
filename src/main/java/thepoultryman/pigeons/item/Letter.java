package thepoultryman.pigeons.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import thepoultryman.pigeons.screen.LetterGui;
import thepoultryman.pigeons.screen.LetterScreen;

import java.util.List;

public class Letter extends BundleItem {
    public Letter(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        if (!isSealed(stackInHand)) {
            if (world.isClient()) {
                MinecraftClient.getInstance().setScreen(new LetterScreen(new LetterGui(stackInHand)));
            }
            return TypedActionResult.success(stackInHand, world.isClient());
        } else {
            return super.use(world, user, hand);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (isSealed(stack))
            tooltip.add(new TranslatableText("pigeons.letter.sealed").formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    public static void setSealed(boolean sealed, ItemStack letterStack) {
        letterStack.getOrCreateNbt().putBoolean("Sealed", sealed);
    }

    public static boolean isSealed(ItemStack letterStack) {
        NbtCompound nbtCompound = letterStack.getOrCreateNbt();
        if (nbtCompound.contains("Sealed")) {
            return nbtCompound.getBoolean("Sealed");
        } else {
            return false;
        }
    }
}
