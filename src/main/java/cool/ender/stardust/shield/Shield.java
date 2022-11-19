package cool.ender.stardust.shield;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.BlockRegistry;
import cool.ender.stardust.registry.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.PanelUI;

import java.util.Random;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

public class Shield {
    public static class Block extends GlassBlock implements EntityBlock {

        public static final BooleanProperty POWERED = BooleanProperty.create("powered");

        public Block() {
            super(Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F).noDrops().noOcclusion().lightLevel((BlockState state) -> {
                if (state.getValue(POWERED)) return 15;
                return 0;
            }).emissiveRendering((BlockState blockState, BlockGetter p_61037_, BlockPos p_61038_) -> blockState.getValue(POWERED)));
            this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
        }

        @Override
        public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
            super.onNeighborChange(state, level, pos, neighbor);
        }

        public void activeShield(Level level, BlockPos blockPos){
            if (!level.isClientSide) {
                level.setBlock(blockPos, BlockRegistry.SHIELD_BLOCK.get().defaultBlockState().setValue(Shield.Block.POWERED, true), 2);
                level.scheduleTick(blockPos, this, 1);
            }
        }

        @Override
        public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
            return box(1, 1, 1, 15, 15, 15);
        }

        @Override
        public void entityInside(BlockState p_60495_, Level level, BlockPos blockPos, Entity p_60498_) {
            super.entityInside(p_60495_, level, blockPos, p_60498_);
            activeShield(level, blockPos);
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new Tile(blockPos, blockState);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_52719_) {
            p_52719_.add(POWERED);
        }

        @Override
        public boolean skipRendering(BlockState blockState, BlockState neighborState, Direction p_53974_) {
            if (!blockState.getValue(POWERED)) return true;
            return super.skipRendering(blockState, neighborState, p_53974_);
        }

        @Override
        public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
            if (state.getValue(POWERED)) return 15;
            return super.getLightEmission(state, level, pos);
        }

        @Override
        public void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, Random random) {
            int r = random.nextInt(1, 20);
            if (r == 5) {
                level.setBlock(blockPos, BlockRegistry.SHIELD_BLOCK.get().defaultBlockState(), 2);
            } else {
                level.scheduleTick(blockPos, this, 1);
            }

        }

        @Override
        public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
            super.stepOn(level, blockPos, blockState, entity);
            activeShield(level, blockPos);
        }

        @Override
        public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
            Level serverLevel = explosion.level;
            if (!serverLevel.isClientSide) {
                serverLevel.setBlock(pos, BlockRegistry.SHIELD_BLOCK.get().defaultBlockState().setValue(Shield.Block.POWERED, true), 2);
                serverLevel.scheduleTick(pos, this, 1);
            }
            return super.getExplosionResistance(state, level, pos, explosion);
        }
    }

    public static class Tile extends BlockEntity {
        BlockPos owner;

        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.SHIELD_TILE.get(), p_155229_, p_155230_);
        }

        public void setOwner(BlockPos owner) {
            this.owner = owner;
        }

        public BlockPos getOwner() {
            return this.owner;
        }
    }
}
