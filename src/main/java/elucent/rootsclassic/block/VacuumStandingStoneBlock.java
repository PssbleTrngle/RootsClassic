package elucent.rootsclassic.block;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.blockentity.RepulsorStandingStoneTile;
import elucent.rootsclassic.blockentity.VacuumStandingStoneTile;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import javax.annotation.Nullable;

public class VacuumStandingStoneBlock extends AttunedStandingStoneBlock implements EntityBlock {

  public VacuumStandingStoneBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
    super.playerWillDestroy(world, pos, state, player);
    if (world.getBlockEntity(pos) instanceof BEBase) {
      ((BEBase) world.getBlockEntity(pos)).breakBlock(world, pos, state, player);
    }
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
      return new RepulsorStandingStoneTile(pos, state);
    }
    return null;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
    return createStandingStoneTicker(level, entityType, RootsRegistry.VACUUM_STANDING_STONE_TILE.get());
  }

  @Nullable
  protected static <T extends BlockEntity> BlockEntityTicker<T> createStandingStoneTicker(Level level, BlockEntityType<T> entityType, BlockEntityType<? extends VacuumStandingStoneTile> standingStoneType) {
    return level.isClientSide ?
            createTickerHelper(entityType, standingStoneType, VacuumStandingStoneTile::clientTick) :
            createTickerHelper(entityType, standingStoneType, VacuumStandingStoneTile::serverTick);
  }
}
