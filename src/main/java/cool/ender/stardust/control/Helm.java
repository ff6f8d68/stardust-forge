package cool.ender.stardust.control;

import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.sandbox.Sandbox;
import cool.ender.stardust.sandbox.SandboxManager;
import cool.ender.stardust.tube.ITubeConnectable;
import cool.ender.stardust.tube.TubeGraph;
import cool.ender.stardust.util.ShipAssembler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.world.ShipWorld;
import org.valkyrienskies.core.impl.datastructures.DenseBlockPosSet;
import org.valkyrienskies.mod.common.assembly.ShipAssemblyKt;

public class Helm {
    public static class Block extends BaseEntityBlock implements ITubeConnectable {

        public Block() {
            super(Properties.of(Material.STONE));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            return new Tile(blockPos, blockState);
        }

        @Override
        public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                Tile tile = (Tile) level.getBlockEntity(blockPos);
                if (tile != null) {
                    tile.assemble();
                }
                return InteractionResult.CONSUME;
            }
        }

        @Override
        public boolean isConnectable(Direction direction) {
            return true;
        }
    }

    public static class Tile extends BlockEntity {

        private Sandbox scriptSandbox = null;

        private TubeGraph connected;

        private ServerShip ship = null;

        public Tile(BlockEntityType<?> tile, BlockPos blockPos, BlockState blockState) {
            super(tile, blockPos, blockState);
        }

        public Tile(BlockPos blockPos, BlockState blockState) {
            this(TileRegistry.HELM.get(), blockPos, blockState);
        }

        /**
         * turn to script, this will change gui.
         */
        public void switchToScriptControl() {
            if (this.scriptSandbox == null) {
                this.scriptSandbox = new Sandbox();
                SandboxManager.manager.add(this, this.scriptSandbox);
            } else {
                SandboxManager.manager.unmute(this.scriptSandbox);
            }
        }

        public void switchToCommonControl() {
            SandboxManager.manager.mute(this.scriptSandbox);
        }

        public void openGui() {
        }

        public void assemble() {
            if (ship == null) {
                ShipAssembler assembler = new ShipAssembler(this.getBlockPos(), this.getLevel());
                this.ship = assembler.assemble();
            }
        }
    }


}
