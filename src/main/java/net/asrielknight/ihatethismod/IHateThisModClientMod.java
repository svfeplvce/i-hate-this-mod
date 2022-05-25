package net.asrielknight.ihatethismod;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.asrielknight.ihatethismod.block.ModBlocks;
import net.asrielknight.ihatethismod.block.entity.ModBlockEntities;
import net.asrielknight.ihatethismod.block.entity.TelevisionBlockEntity;
import net.asrielknight.ihatethismod.block.entity.TelevisionBlockEntityRenderer;
import net.asrielknight.ihatethismod.block.entity.TelevisionModel;
import net.asrielknight.ihatethismod.screen.TelevisionGuiDescription;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

import java.io.IOException;

public class IHateThisModClientMod implements ClientModInitializer {
    public static BlockEntityType<TelevisionBlockEntity> TELEVISION_ENTITY;
    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TELEVISION, RenderLayer.getCutout());
        HandledScreens.<TelevisionGuiDescription, CottonInventoryScreen<TelevisionGuiDescription>>register(
                ModBlockEntities.TELEVISION_SCREEN_HANDLER_TYPE,
                CottonInventoryScreen::new
        );
        try {
            TelevisionModel.saveNativeImageAsIdentifier(IHateThisMod.nativeImage, TelevisionModel.identifier);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new IHateThisModelProvider());
        BlockEntityRendererRegistry.register(TELEVISION_ENTITY, TelevisionBlockEntityRenderer::new);
    }
}
