package cool.ender.stardust.missile.launcher;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.turret.AbstractTurret;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class VerticalMissileLauncher {
    public static class Block extends BaseEntityBlock {

        public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");
        public static final BooleanProperty CENTERED = BooleanProperty.create("centered");

        public Block() {
            super(Properties.of(Material.METAL).emissiveRendering((BlockState p_61036_, BlockGetter p_61037_, BlockPos p_61038_) -> true));
            this.registerDefaultState(this.stateDefinition.any().setValue(ASSEMBLED, false).setValue(CENTERED, false));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new Tile(blockPos, blockState);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_52719_) {
            p_52719_.add(ASSEMBLED).add(CENTERED);
        }

        @Override
        public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
            return false;
        }

        @Override
        public RenderShape getRenderShape(BlockState blockState) {
            return blockState.getValue(ASSEMBLED) ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
        }

        @Override
        public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState p_60569_, boolean p_60570_) {
            if (!level.isClientSide) {
                AssembleTask1 task1 = new AssembleTask1(blockPos, level);
                if (task1.fastVerify()) {
                    AssembleTask2 task2 = new AssembleTask2(task1.getCenterPos(), level);
                    if (task2.searchAndSet()) {
                        BlockState centerState = level.getBlockState(task1.getCenterPos());
                        level.setBlock(task1.getCenterPos(), centerState.setValue(CENTERED, true), 2);
                    }
                }
            }
            super.onPlace(blockState, level, blockPos, p_60569_, p_60570_);
        }

        @Override
        public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
            super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
        }
    }

    public static class Tile extends BlockEntity implements IAnimatable {

        BlockPos centerPos;
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.VERTICAL_MISSILE_LAUNCHER_TILE.get(), p_155229_, p_155230_);
        }

        @Override
        public void handleUpdateTag(CompoundTag tag) {
            this.centerPos = new BlockPos(tag.getInt("center_x"), tag.getInt("center_y"), tag.getInt("center_z"));
        }

        @Override
        public CompoundTag getUpdateTag() {
            CompoundTag tag = super.getUpdateTag();
            tag.putInt("center_x", centerPos.getX());
            tag.putInt("center_y", centerPos.getY());
            tag.putInt("center_z", centerPos.getZ());
            return tag;
        }

        /**
         * Server Send
         * */
        @Nullable
        @Override
        public Packet<ClientGamePacketListener> getUpdatePacket() {
            return super.getUpdatePacket();
        }

        /**
         * Client Receive
         * */
        @Override
        public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
            super.onDataPacket(net, pkt);
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = super.serializeNBT();
            nbt.putInt("center_x", centerPos.getX());
            nbt.putInt("center_y", centerPos.getY());
            nbt.putInt("center_z", centerPos.getZ());
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            super.deserializeNBT(nbt);
            this.centerPos = new BlockPos(nbt.getInt("center_x"), nbt.getInt("center_y"), nbt.getInt("center_z"));
        }

        @Override
        public void registerControllers(AnimationData data) {
        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }

    public static class Renderer extends GeoBlockRenderer<Tile> {
        public Renderer(BlockEntityRendererProvider.Context rendererProvider) {
            super(rendererProvider, new Model());
        }

        @Override
        public boolean shouldRender(BlockEntity blockEntity, Vec3 vec3) {
            BlockState blockstate = blockEntity.getBlockState();
            return blockstate.getValue(Block.CENTERED);
        }
    }

    public static class Model extends AnimatedGeoModel<Tile> {
        @Override
        public ResourceLocation getModelLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "geo/vertical_missile_launcher.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/block/vertical_missile_launcher.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(Tile animatable) {
            return null;
        }

        @Override
        public void setCustomAnimations(Tile animatable, int instanceId) {
            super.setCustomAnimations(animatable, instanceId);
        }
    }

    public static class AssembleTask1 {
        Level level;
        BlockPos placePos;
        int north = 0;//-Z
        int south = 0;//+Z
        int west = 0;//-X
        int east = 0;//+X
        int up = 0;//+Y
        int down = 0;//-Y

        public AssembleTask1(BlockPos placePos, Level level) {
            this.level = level;
            this.placePos = placePos;
        }

        public boolean fastVerify() {

            //north & south
            BlockPos temPos = placePos.north();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED)) {
                north++;
                temPos = temPos.north();
            }
            temPos = placePos.south();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED) && north + south < 3) {
                south++;
                temPos = temPos.south();
            }

            if (south + north < 2) {
                return false;
            }

            //east & west
            temPos = placePos.east();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED)) {
                east++;
                temPos = temPos.east();
            }
            temPos = placePos.west();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED) && east + west < 3) {
                west++;
                temPos = temPos.west();
            }
            if (east + west < 2) {
                return false;
            }

            //up & down
            temPos = placePos.above();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED)) {
                up++;
                temPos = temPos.above();
            }
            temPos = placePos.below();
            while (level.getBlockState(temPos).getBlock() instanceof Block && !level.getBlockState(temPos).getValue(Block.ASSEMBLED) && up + down < 5) {
                down++;
                temPos = temPos.below();
            }
            return up + down >= 4;
        }

        public BlockPos getCenterPos() {
            return placePos.offset(east - 1, -down, south - 1);
        }
    }

    public static class AssembleTask2 {

        BlockPos centerPos;
        Level level;

        public AssembleTask2(BlockPos centerPos, Level level) {
            this.centerPos = centerPos;
            this.level = level;
        }

        public boolean searchAndSet() {
            for (int x = centerPos.getX() - 1; x <= centerPos.getX() + 1; x++) {
                for (int y = centerPos.getY(); y <= centerPos.getY() + 4; y++) {
                    for (int z = centerPos.getZ() - 1; z <= centerPos.getZ() + 1; z++) {
                        BlockState current = level.getBlockState(new BlockPos(x, y, z));
                        if (!(current.getBlock() instanceof  Block && !current.getValue(Block.ASSEMBLED))) {
                            return false;
                        }
                    }
                }
            }
            for (int x = centerPos.getX() - 1; x <= centerPos.getX() + 1; x++) {
                for (int y = centerPos.getY(); y <= centerPos.getY() + 4; y++) {
                    for (int z = centerPos.getZ() - 1; z <= centerPos.getZ() + 1; z++) {
                        BlockState current = level.getBlockState(new BlockPos(x, y, z));
                        level.setBlock(new BlockPos(x, y, z), current.setValue(Block.ASSEMBLED, true), 2);
                    }
                }
            }
            return true;
        }
    }

    public abstract static class Listener extends AbstractTurret.Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener extends AbstractTurret.Listener.ClientListener {
            @SubscribeEvent
            public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
                event.registerBlockEntityRenderer(TileRegistry.VERTICAL_MISSILE_LAUNCHER_TILE.get(), VerticalMissileLauncher.Renderer::new);
            }
        }
    }

}
