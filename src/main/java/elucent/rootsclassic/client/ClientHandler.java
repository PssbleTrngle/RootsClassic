package elucent.rootsclassic.client;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import elucent.rootsclassic.client.model.WildwoodArmorModel;
import elucent.rootsclassic.client.renderer.block.AltarBER;
import elucent.rootsclassic.client.renderer.block.BrazierBER;
import elucent.rootsclassic.client.renderer.block.ImbuerBER;
import elucent.rootsclassic.client.renderer.block.MortarBER;
import elucent.rootsclassic.client.renderer.entity.AcceleratorRenderer;
import elucent.rootsclassic.client.renderer.entity.PhantomSkeletonRenderer;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
  public static final ModelLayerLocation SYLVAN_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "sylvan_armor");
  public static final ModelLayerLocation WILDWOOD_ARMOR = new ModelLayerLocation(new ResourceLocation(Const.MODID, "main"), "wildwood_armor");

  public static void onClientSetup(final FMLClientSetupEvent event) {
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.MIDNIGHT_BLOOM.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.FLARE_ORCHID.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.RADIANT_DAISY.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.ALTAR.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(RootsRegistry.BRAZIER.get(), RenderType.cutout());

    ItemProperties.register(RootsRegistry.STAFF.get(), new ResourceLocation("imbued"), (stack, world, livingEntity, unused) ->
            stack.getTag() != null && stack.getTag().contains(Const.NBT_EFFECT) ? 1.0F : 0.0F);
  }

  public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(RootsRegistry.MORTAR_TILE.get(), MortarBER::new);
    event.registerBlockEntityRenderer(RootsRegistry.IMBUER_TILE.get(), ImbuerBER::new);
    event.registerBlockEntityRenderer(RootsRegistry.ALTAR_TILE.get(), AltarBER::new);
    event.registerBlockEntityRenderer(RootsRegistry.BRAZIER_TILE.get(), BrazierBER::new);

    event.registerEntityRenderer(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonRenderer::new);
    event.registerEntityRenderer(RootsEntities.ENTITY_ACCELERATOR.get(), AcceleratorRenderer::new);
    event.registerEntityRenderer(RootsEntities.TILE_ACCELERATOR.get(), AcceleratorRenderer::new);
  }

  public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(SYLVAN_ARMOR, () -> SylvanArmorModel.createArmorDefinition());
    event.registerLayerDefinition(WILDWOOD_ARMOR, () -> WildwoodArmorModel.createArmorDefinition());
  }

  public static void registerItemColors(final ColorHandlerEvent.Item event) {
    ItemColors colors = event.getItemColors();
    colors.register((stack, tintIndex) -> {
      if (stack.hasTag() && stack.getItem() instanceof StaffItem) {
        CompoundTag tag = stack.getTag();
        ResourceLocation compName = ResourceLocation.tryParse(tag.getString(Const.NBT_EFFECT));
        if (compName != null) {
          ComponentBase comp = ComponentManager.getComponentFromName(compName);
          if (comp != null) {
            if (tintIndex == 2) {
              return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
            }
            if (tintIndex == 1) {
              return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
            }
          }
        }
      }
      return RootsUtil.intColor(255, 255, 255);
    }, RootsRegistry.STAFF.get());
    colors.register((stack, tintIndex) -> {
      if (stack.getItem() instanceof CrystalStaffItem && stack.hasTag()) {
        String effect = CrystalStaffItem.getEffect(stack);
        if (effect != null) {
          ResourceLocation compName = ResourceLocation.tryParse(effect);
          if (compName != null) {
            ComponentBase comp = ComponentManager.getComponentFromName(compName);
            if (comp != null) {
              if (tintIndex == 2) {
                return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
              }
              if (tintIndex == 1) {
                return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
              }
            }
          }
        }
      }
      return RootsUtil.intColor(255, 255, 255);
    }, RootsRegistry.CRYSTAL_STAFF.get());
  }
}
