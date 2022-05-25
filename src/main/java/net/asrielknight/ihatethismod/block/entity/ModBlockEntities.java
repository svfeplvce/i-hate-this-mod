package net.asrielknight.ihatethismod.block.entity;

import net.asrielknight.ihatethismod.IHateThisMod;
import net.asrielknight.ihatethismod.block.ModBlocks;
import net.asrielknight.ihatethismod.screen.TelevisionGuiDescription;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static ScreenHandlerType<TelevisionGuiDescription> TELEVISION_SCREEN_HANDLER_TYPE;
    public static BlockEntityType<TelevisionBlockEntity> TELEVISION;

    public static void registerAllBlockEntities() {
        TELEVISION = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(IHateThisMod.MOD_ID, "television"),
                FabricBlockEntityTypeBuilder.create(TelevisionBlockEntity::new,
                        ModBlocks.TELEVISION).build(null));
        TELEVISION_SCREEN_HANDLER_TYPE = new ScreenHandlerType<>((int syncId, PlayerInventory inventory) -> {
            return new TelevisionGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY);
        });
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(IHateThisMod.MOD_ID, "television"), TELEVISION_SCREEN_HANDLER_TYPE);
    }
}
