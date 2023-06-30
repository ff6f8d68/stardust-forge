package cool.ender.stardust.component.tube;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Tube {

    public static class Block extends BaseEntityBlock implements ITubeConnectable {

        public static final BooleanProperty NORTH = BooleanProperty.create("north");
        public static final BooleanProperty EAST = BooleanProperty.create("east");
        public static final BooleanProperty SOUTH = BooleanProperty.create("south");
        public static final BooleanProperty WEST = BooleanProperty.create("west");
        public static final BooleanProperty UP = BooleanProperty.create("up");
        public static final BooleanProperty DOWN = BooleanProperty.create("down");

        public Block () {
            super(Properties.of(Material.STONE).noOcclusion());
            this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));

        }

        public BlockEntity newBlockEntity ( @NotNull BlockPos blockPos, @NotNull BlockState blockState ) {
            return new Tile(blockPos, blockState);
        }

        public void neighborChanged ( BlockState blockState, Level level, BlockPos selfBlock, net.minecraft.world.level.block.Block block, BlockPos neighborBlock, boolean p_62514_ ) {
            if (level.getBlockState(neighborBlock).getBlock() instanceof ITubeConnectable) {
                if (selfBlock.north().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(NORTH, true), 2);
                }
                if (selfBlock.south().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(SOUTH, true), 2);
                }
                if (selfBlock.east().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(EAST, true), 2);
                }
                if (selfBlock.west().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(WEST, true), 2);
                }
                if (selfBlock.above().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(UP, true), 2);
                }
                if (selfBlock.below().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(DOWN, true), 2);
                }
            } else {
                if (selfBlock.north().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(NORTH, false), 2);
                }
                if (selfBlock.south().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(SOUTH, false), 2);
                }
                if (selfBlock.east().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(EAST, false), 2);
                }
                if (selfBlock.west().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(WEST, false), 2);
                }
                if (selfBlock.above().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(UP, false), 2);
                }
                if (selfBlock.below().equals(neighborBlock)) {
                    level.setBlock(selfBlock, blockState.setValue(DOWN, false), 2);
                }
            }
        }

        @Nullable
        @Override
        public BlockState getStateForPlacement ( BlockPlaceContext context ) {
            BlockPos placePos = context.getClickedPos();
            BlockState defaultState = this.defaultBlockState();

            for (Direction direction : Direction.values()) {
                BlockPos neighbor = placePos.relative(direction);
                BlockState blockState = context.getLevel().getBlockState(neighbor);
                net.minecraft.world.level.block.Block block = blockState.getBlock();
                if (block instanceof ITubeConnectable && ((ITubeConnectable) block).isConnectable(direction.getOpposite(), blockState)) {
                    for (Property property : defaultState.getProperties()) {
                        if (property.getName().equals(direction.getName())) {
                            defaultState = defaultState.setValue(property, true);
                        }
                    }
                }
            }

            return defaultState;
        }

        @Override
        protected void createBlockStateDefinition ( StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_49915_ ) {
            p_49915_.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
        }

        @Override
        public boolean isConnectable(Direction direction, BlockState self) {
            return true;
        }
    }


    public static class Tile extends BlockEntity implements IAnimatable {

        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Tile ( BlockPos p_155229_, BlockState p_155232_ ) {
            super(TileRegistry.TUBE.get(), p_155229_, p_155232_);
        }

        public void registerControllers ( AnimationData data ) {

        }

        @Override
        public AnimationFactory getFactory () {
            return factory;
        }
    }

    public static class Model extends AnimatedGeoModel<Tile> {
        public ResourceLocation getModelLocation ( Tile object ) {


            return new ResourceLocation(Stardust.MOD_ID, "geo/tube_7_1.geo.json");
        }


        public ResourceLocation getTextureLocation ( Tile object ) {

            return new ResourceLocation(Stardust.MOD_ID, "textures/block/tube_7_1.png");
        }

        public ResourceLocation getAnimationFileLocation ( Tile animatable ) {
            return null;
        }

        @Override
        public void setCustomAnimations(Tile animatable, int instanceId) {
            String[] strings = new String[5];
            super.setCustomAnimations(animatable, instanceId);
            if (!animatable.getBlockState().getValue(Block.NORTH)) {
                strings[4] = "bone6";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true);
            }
            if (!animatable.getBlockState().getValue(Block.SOUTH)) {
                strings[4] = "bone4";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true);
            }
            if (!animatable.getBlockState().getValue(Block.WEST)) {
                strings[4] = "bone10";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true);
            }
            if (!animatable.getBlockState().getValue(Block.EAST)) {
                strings[4] = "bone2";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true);
            }
            if (!animatable.getBlockState().getValue(Block.UP)) {
                strings[4] = "bone12";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true);
            }
            if (!animatable.getBlockState().getValue(Block.DOWN)) {
                strings[4] = "bone8";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true);
            }
            if (animatable.getBlockState().getValue(Block.NORTH)) {
                strings[4] = "bone6";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true, false);
            }
            if (animatable.getBlockState().getValue(Block.SOUTH)) {
                strings[4] = "bone4";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true, false);
            }
            if (animatable.getBlockState().getValue(Block.WEST)) {
                strings[4] = "bone10";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true, false);
            }
            if (animatable.getBlockState().getValue(Block.EAST)) {
                strings[4] = "bone2";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true, false);
            }
            if (animatable.getBlockState().getValue(Block.UP)) {
                strings[4] = "bone12";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true, false);
            }
            if (animatable.getBlockState().getValue(Block.DOWN)) {
                strings[4] = "bone8";
                this.getAnimationProcessor().getBone(strings[4]).setHidden(true, false);
            }
        }
    }

    public static class Renderer extends GeoBlockRenderer<Tile> {
        public Renderer ( BlockEntityRendererProvider.Context rendererProvider ) {
            super(rendererProvider, new Model());
        }
    }

    public abstract static class Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener {
            @SubscribeEvent
            public static void registerRenderers ( final EntityRenderersEvent.RegisterRenderers event ) {
                event.registerBlockEntityRenderer(TileRegistry.TUBE.get(), Renderer::new);
            }
        }
    }

    public String getRegisterName () {
        return "tube";
    }
}
