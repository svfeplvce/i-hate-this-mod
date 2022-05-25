package net.asrielknight.ihatethismod.item;

import net.asrielknight.ihatethismod.IHateThisMod;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static final ItemGroup IHATETHISMODGROUP = FabricItemGroupBuilder.build(new Identifier(IHateThisMod.MOD_ID, "ihatethismod"),
            () -> new ItemStack(ModItems.DEBUG_INGOT));

}
