package elucent.rootsclassic.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import elucent.rootsclassic.block.imbuer.ImbuerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class ImbuerBER implements BlockEntityRenderer<ImbuerBlockEntity> {

  public ImbuerBER(BlockEntityRendererProvider.Context context) {
  }

  @Override
  public void render(ImbuerBlockEntity imbuerTile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    final ItemStack stickStack = imbuerTile.getStick();
    if (!stickStack.isEmpty()) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.5, 0.3125, 0.5);
      matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(imbuerTile.spin));
      Minecraft.getInstance().getItemRenderer().renderStatic(stickStack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
      matrixStackIn.popPose();
    }
    final ItemStack dustStack = imbuerTile.getSpellPowder();
    if (!dustStack.isEmpty()) {
      matrixStackIn.pushPose();
      matrixStackIn.translate(0.5, 0.125, (1/16F) * 6);
      matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90));
      Minecraft.getInstance().getItemRenderer().renderStatic(dustStack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
      matrixStackIn.popPose();
    }
  }
}
