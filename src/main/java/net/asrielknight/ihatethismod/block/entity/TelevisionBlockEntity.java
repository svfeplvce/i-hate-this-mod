package net.asrielknight.ihatethismod.block.entity;

import net.asrielknight.ihatethismod.item.inventory.ImplementedInventory;
import net.asrielknight.ihatethismod.screen.TelevisionGuiDescription;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TelevisionBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory {

    DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public TelevisionBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TELEVISION, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return pos.isWithinDistance(player.getBlockPos(), 4.5);
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("Television");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new TelevisionGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
    }
}
