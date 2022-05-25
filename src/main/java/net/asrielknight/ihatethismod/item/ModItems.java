package net.asrielknight.ihatethismod.item;

import net.asrielknight.ihatethismod.IHateThisMod;
import net.asrielknight.ihatethismod.item.custom.ModSwordItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item DEBUG_INGOT = registerItem("debug_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.IHATETHISMODGROUP)));
    public static final Item DEBUG_SWORD = registerItem("debug_sword",
            new SwordItem(ModToolMaterials.DEBUG, 1, 2.0f, new FabricItemSettings().group(ModItemGroup.IHATETHISMODGROUP)));
    public static final Item DEBUG_HELMET = registerItem("debug_helmet",
            new ArmorItem(ModArmorMaterials.DEBUG, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.IHATETHISMODGROUP)));
    public static final Item DEBUG_CHESTPLATE = registerItem("debug_chestplate",
            new ArmorItem(ModArmorMaterials.DEBUG, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.IHATETHISMODGROUP)));
    public static final Item DEBUG_LEGGINGS = registerItem("debug_leggings",
            new ArmorItem(ModArmorMaterials.DEBUG, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.IHATETHISMODGROUP)));
    public static final Item DEBUG_BOOTS = registerItem("debug_boots",
            new ArmorItem(ModArmorMaterials.DEBUG, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.IHATETHISMODGROUP)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(IHateThisMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        IHateThisMod.LOGGER.info("Registering items in " + IHateThisMod.MOD_ID);
    }

}
