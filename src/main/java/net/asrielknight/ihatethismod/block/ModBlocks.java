package net.asrielknight.ihatethismod.block;

import net.asrielknight.ihatethismod.IHateThisMod;
import net.asrielknight.ihatethismod.item.ModItemGroup;
import net.asrielknight.ihatethismod.block.custom.TelevisionBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block DEBUG_BLOCK = registerBlock("debug_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(6f).requiresTool()), ModItemGroup.IHATETHISMODGROUP);
    public static final Block TELEVISION = registerBlock("television",
            new TelevisionBlock(FabricBlockSettings.of(Material.METAL).breakInstantly().nonOpaque().luminance((state) -> state.get(TelevisionBlock.ON) ? 6 : 0)), ModItemGroup.IHATETHISMODGROUP);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(IHateThisMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(IHateThisMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        IHateThisMod.LOGGER.info("Registering blocks for " + IHateThisMod.MOD_ID);
    }

}
