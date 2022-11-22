package cool.ender.stardust.missile.launcher;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.turret.AbstractTurret;
import cool.ender.stardust.turret.small.RailGun1Small;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

import java.util.Objects;

public class VerticalMissileLauncher {
    public static class Block extends BaseEntityBlock {

        public static final BooleanProperty ASSEMBLED = BooleanProperty.create("assembled");

        public Block() {
            super(Properties.of(Material.METAL).emissiveRendering((BlockState p_61036_, BlockGetter p_61037_, BlockPos p_61038_) -> true));
            this.registerDefaultState(this.stateDefinition.any().setValue(ASSEMBLED, false));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new Tile(blockPos, blockState);
        }
        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_52719_) {
            p_52719_.add(ASSEMBLED);
        }


        @Override
        public RenderShape getRenderShape(BlockState blockState) {
            return blockState.getValue(ASSEMBLED) ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
        }

        @Override
        public boolean canBeReplaced(BlockState blockState, BlockPlaceContext context) {
            if (!context.getLevel().isClientSide) {
                PlaceScanTask task = new PlaceScanTask(context.getClickedPos(), context.getLevel());

                if (task.scan()) {
                    BlockState centerState = context.getLevel().getBlockState(task.getCenterPos());
                    Stardust.LOGGER.info(centerState);
                    Stardust.LOGGER.info(task.getCenterPos());
                    context.getLevel().setBlock(task.getCenterPos(), centerState.setValue(ASSEMBLED, true), 2);
                }
            }
            return super.canBeReplaced(blockState, context);
        }

        @Override
        public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
            super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
        }
    }

    public static class Tile extends BlockEntity implements IAnimatable {

        Tile coreTile;
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.VERTICAL_MISSILE_LAUNCHER_TILE.get(), p_155229_, p_155230_);
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
            return blockstate.getValue(Block.ASSEMBLED);
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
    }

    public static class PlaceScanTask {
        Level level;
        BlockPos placePos;

        BlockPos centerPos;
        int north = 0;//-Z
        int south = 0;//+Z
        int west = 0;//-X
        int east = 0;//+X
        int up = 0;//+Y
        int down = 0;//-Y

        public PlaceScanTask(BlockPos placePos, Level level) {
            this.level = level;
            this.placePos = placePos;
        }

        public boolean scan() {

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

            Stardust.LOGGER.info("south+north");
            Stardust.LOGGER.info(south + north + 1);
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
            Stardust.LOGGER.info("west+east");
            Stardust.LOGGER.info(west + east + 1);
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
            Stardust.LOGGER.info("above+below");
            Stardust.LOGGER.info(up + down + 1);
            return up + down >= 5;
        }

        public BlockPos getCenterPos() {
            return placePos.offset(east - 1 , -down, south - 1);
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
