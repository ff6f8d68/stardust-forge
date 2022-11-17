package cool.ender.stardust.turret.small;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.projectile.PlasmaProjectile;
import cool.ender.stardust.registry.SoundRegistry;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.turret.AbstractTurret;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
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

        @Override
        public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
            return true;
        }

        @Override
        public void neighborChanged(BlockState blockState, Level level, BlockPos selfBlock, net.minecraft.world.level.block.Block block, BlockPos neighborBlock, boolean p_60514_) {
            if (level.hasNeighborSignal(selfBlock)) {
                ((Tile) level.getBlockEntity(selfBlock)).shoot();
            }
        }

        @Override
        public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                ((Tile) level.getBlockEntity(blockPos)).shoot();
                return InteractionResult.CONSUME;
            }
        }

        public int getAnalogOutputSignal(BlockState p_52689_, Level p_52690_, BlockPos p_52691_) {
            return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(p_52690_.getBlockEntity(p_52691_));
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

        @Override
        public VoxelShape getShape(BlockState p_52807_, BlockGetter p_52808_, BlockPos p_52809_, CollisionContext p_52810_) {
            return box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        }

        @Override
        public boolean hasDynamicShape() {
            return false;
        }
    }

    public static class Tile extends AbstractTurret.Tile {
        protected Tile(BlockEntityType<?> entityType, BlockPos p_155229_, BlockState p_155230_) {
            super(entityType, p_155229_, p_155230_);
        }

        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            this(TileRegistry.RAIL_GUN_1_SMALL_TILE.get(), p_155229_, p_155230_);
        }

        @Override
        public void registerControllers(AnimationData data) {

        }

        public void shoot() {
            Vec3 centerVec = new Vec3(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5);
            Vec3i facingVec = this.getBlockState().getValue(CANNON_FACING).getNormal();
            centerVec = centerVec.add(facingVec.getX(), facingVec.getY(), facingVec.getZ());
            PlasmaProjectile.Entity projectile = new PlasmaProjectile.Entity(centerVec.x, centerVec.y, centerVec.z, facingVec.getX(), facingVec.getY(), facingVec.getZ(), this.getLevel(), this);
            assert this.level != null;
            this.level.addFreshEntity(projectile);
            level.playSound(null, centerVec.x, centerVec.y, centerVec.z, SoundRegistry.X_WING_SHOOTING_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    public static class Model extends AbstractTurret.Model {

        @Override
        public ResourceLocation getModelLocation(AbstractTurret.Tile tile) {
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
            super.codeAnimations(entity, uniqueID, customPredicate);
        }

        @Override
        public void setCustomAnimations(AbstractTurret.Tile animatable, int instanceId) {
            super.setCustomAnimations(animatable, instanceId);
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
