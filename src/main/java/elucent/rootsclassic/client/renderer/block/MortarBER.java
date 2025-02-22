package elucent.rootsclassic.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import elucent.rootsclassic.block.mortar.MortarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class MortarBER implements BlockEntityRenderer<MortarBlockEntity> {

  public MortarBER(BlockEntityRendererProvider.Context context) {
  }

  @Override
  public void render(MortarBlockEntity mortarTile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    final ItemStackHandler inventory = mortarTile.inventory;
    for (int i = 0; i < inventory.getSlots(); i++) {
      ItemStack stack = inventory.getStackInSlot(i);
      if (!stack.isEmpty()) {
        matrixStackIn.pushPose();
        Random random = new Random(stack.hashCode());
        matrixStackIn.translate(0.475 + random.nextFloat() / 20.0, 0.05 + random.nextFloat() / 20.0, 0.475 + random.nextFloat() / 20.0);
        matrixStackIn.scale(0.65F, 0.65F, 0.65F);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(random.nextInt(360)));
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
        matrixStackIn.popPose();
      }
    }
  }
}
