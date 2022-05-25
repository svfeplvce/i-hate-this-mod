package net.asrielknight.ihatethismod.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.asrielknight.ihatethismod.block.custom.TelevisionBlock;
import net.asrielknight.ihatethismod.block.entity.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

public class TelevisionGuiDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 1;
    private static BlockPos pos;

    public TelevisionGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext ctx) {
        super(ModBlockEntities.TELEVISION_SCREEN_HANDLER_TYPE, syncId, playerInventory, getBlockInventory(ctx, INVENTORY_SIZE), getBlockPropertyDelegate(ctx));
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);
        root.setInsets(Insets.ROOT_PANEL);
        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        root.add(itemSlot, 4, 1);
        root.add(this.createPlayerInventoryPanel(), 0 , 3);
        WButton buttonA = new WButton(new LiteralText("Bruh"));
        buttonA.setOnClick(() -> {
            ctx.get((world1, blockPos) -> pos = blockPos);
            BlockState state = world.getBlockState(pos);
            state.cycle(TelevisionBlock.ON);
        });
        root.validate(this);
    }
}
