package net.asrielknight.ihatethismod;

import net.asrielknight.ihatethismod.block.entity.TelevisionModel;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class IHateThisModelProvider implements ModelResourceProvider {
    public static final Identifier TELEVISION_MODEL = new Identifier("ihatethismod:block/television_on");
    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) throws ModelProviderException {
        if (resourceId.equals(TELEVISION_MODEL)) {
            return new TelevisionModel();
        } else {
            return null;
        }
    }
}
