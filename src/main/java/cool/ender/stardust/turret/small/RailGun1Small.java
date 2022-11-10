package cool.ender.stardust.turret.small;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.turret.AbstractTurret;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import static cool.ender.stardust.turret.small.RailGun1Small.Block.CANNON_FACING;

public class RailGun1Small extends AbstractTurret {

    @Override
    public String getRegisterName() {
        return "rail_gun_1_small";
    }

    public static class Block extends AbstractTurret.Block implements EntityBlock {

        public static final DirectionProperty CANNON_FACING = DirectionProperty.create("cannon_facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

        public Block() {
            super(Properties.of(Material.METAL).noOcclusion());
            this.registerDefaultState(this.stateDefinition.any().setValue(CANNON_FACING, Direction.SOUTH));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            return new Tile(blockPos, blockState);
        }

        @Override
        public boolean propagatesSkylightDown(@NotNull BlockState p_49928_, @NotNull BlockGetter p_49929_, @NotNull BlockPos p_49930_) {
            return true;
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            return this.defaultBlockState().setValue(CANNON_FACING, context.getNearestLookingDirection().getOpposite());
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> p_52719_) {
            p_52719_.add(CANNON_FACING);
        }

        @NotNull
        public RenderShape getRenderShape(@NotNull BlockState state) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }

        public VoxelShape getShape(BlockState p_52807_, BlockGetter p_52808_, BlockPos p_52809_, CollisionContext p_52810_) {
            return box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        }
    }

    public static class Tile extends AbstractTurret.Tile {

        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.RAIL_GUN_1_SMALL_TILE.get(), p_155229_, p_155230_);
        }

        @Override
        public void registerControllers(AnimationData data) {

        }
    }

    public static class Model extends AbstractTurret.Model {

        @Override
        public ResourceLocation getModelLocation(AbstractTurret.Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "geo/rail_gun_1_small.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(AbstractTurret.Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/block/rail_gun_1_small.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(AbstractTurret.Tile animatable) {
            return null;
        }

        @Override
        public void codeAnimations(AbstractTurret.Tile entity, Integer uniqueID, AnimationEvent<?> customPredicate) {
            this.getAnimationProcessor().getBone("bone").setRotationX((float) (Math.PI * 0.5));
            super.codeAnimations(entity, uniqueID, customPredicate);
        }

        @Override
        public void setLivingAnimations(AbstractTurret.Tile animatable, Integer instanceId) {
            super.setLivingAnimations(animatable, instanceId);
            switch (animatable.getBlockState().getValue(CANNON_FACING)) {
                case SOUTH -> {
                    this.getAnimationProcessor().getBone("bone").setRotationY((float) (Math.PI));
                }

                case NORTH -> {
                }

                case WEST -> {
                    this.getAnimationProcessor().getBone("bone").setRotationY((float) (Math.PI * 0.5));
                }

                case EAST -> {
                    this.getAnimationProcessor().getBone("bone").setRotationY((float) (Math.PI * -0.5));
                }

                case UP -> {
                    this.getAnimationProcessor().getBone("bone").setRotationX((float) (Math.PI * 0.5));
                }

                case DOWN -> {
                    this.getAnimationProcessor().getBone("bone").setRotationX((float) (Math.PI * -0.5));
                }
            }
        }
    }

    public static class Renderer extends AbstractTurret.Renderer {

        public Renderer(BlockEntityRendererProvider.Context rendererProvider) {
            super(rendererProvider, new RailGun1Small.Model());
        }
    }

    public abstract static class Listener extends AbstractTurret.Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener extends AbstractTurret.Listener.ClientListener {
            @SubscribeEvent
            public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
                event.registerBlockEntityRenderer(TileRegistry.RAIL_GUN_1_SMALL_TILE.get(), RailGun1Small.Renderer::new);
            }
        }
    }

}
