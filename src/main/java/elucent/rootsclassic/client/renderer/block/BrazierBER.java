package elucent.rootsclassic.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class BrazierBER implements BlockEntityRenderer<BrazierBlockEntity> {

  public BrazierBER(BlockEntityRendererProvider.Context context) {
  }

  @Override
  public void render(BrazierBlockEntity brazierTile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    if (!brazierTile.getHeldItem().isEmpty()) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.5, 0.5, 0.5);
      matrixStackIn.scale(0.5F, 0.5F, 0.5F);
      matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(brazierTile.getTicker()));
      Minecraft.getInstance().getItemRenderer().renderStatic(brazierTile.getHeldItem(), TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
      matrixStackIn.popPose();
    }
  }
}
