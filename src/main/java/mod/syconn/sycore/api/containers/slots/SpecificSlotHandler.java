package mod.syconn.sycore.api.containers.slots;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SpecificSlotHandler extends SlotItemHandler {

    private final Item onlyType;

    public SpecificSlotHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item item) {
        super(itemHandler, index, xPosition, yPosition);
        this.onlyType = item;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (stack.getItem() != onlyType)
            return false;

        return super.mayPlace(stack);
    }
}
