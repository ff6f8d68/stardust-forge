package cool.ender.stardust.control;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.registry.TileRegistry;
import cool.ender.stardust.turret.AbstractTurret;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Objects;

public class Computer {
    public static class Block extends BaseEntityBlock {
        public Block() {
            super(Properties.of(Material.GLASS));
        }

        @NotNull
        public RenderShape getRenderShape(@NotNull BlockState state) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }

        @Override
        public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
            if (level.isClientSide) {
                ((Tile) Objects.requireNonNull(level.getBlockEntity(blockPos))).open = !((Tile) Objects.requireNonNull(level.getBlockEntity(blockPos))).open;
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }


        @Nullable
        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            return new Computer.Tile(blockPos, blockState);
        }
    }

    public static class Tile extends BlockEntity implements IAnimatable {
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        boolean open = false;
        public Tile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
            super(p_155228_, p_155229_, p_155230_);
        }
        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            this(TileRegistry.COMPUTER_TILE.get(), p_155229_, p_155230_);
        }
        @Override
        public void registerControllers(AnimationData data) {
            data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
        }

        private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
            AnimationController<E> controller = event.getController();
            controller.transitionLengthTicks = 0;
            if (open) {
                if (controller.getAnimationState() == AnimationState.Stopped) {
                    controller.setAnimation(new AnimationBuilder().addAnimation("open", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
                    return PlayState.CONTINUE;
                }
            } else {
                if (controller.getAnimationState() == AnimationState.Stopped) {
                    controller.setAnimation(new AnimationBuilder().addAnimation("close", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
                    return PlayState.CONTINUE;
                }
            }
            return PlayState.CONTINUE;
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
    }

    public static class Model extends AnimatedGeoModel<Tile> {

        @Override
        public ResourceLocation getModelLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "geo/console.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/block/console.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(Tile animatable) {
            return new ResourceLocation(Stardust.MOD_ID, "animations/console_open_and_close.json");
        }
    }

    public abstract static class Listener extends AbstractTurret.Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener extends AbstractTurret.Listener.ClientListener {
            @SubscribeEvent
            public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
                event.registerBlockEntityRenderer(TileRegistry.COMPUTER_TILE.get(), Computer.Renderer::new);
            }
        }
    }
}
