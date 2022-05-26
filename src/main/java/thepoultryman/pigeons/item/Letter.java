package thepoultryman.pigeons.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
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
    private boolean sealed = false;

    public Letter(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        if (world.isClient())
            MinecraftClient.getInstance().setScreen(new LetterScreen(new LetterGui(stackInHand, this)));
        return TypedActionResult.success(stackInHand, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (sealed) tooltip.add(new TranslatableText("pigeons.letter.sealed").formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    public void setSealed(boolean sealed) {
        this.sealed = sealed;
    }
}
