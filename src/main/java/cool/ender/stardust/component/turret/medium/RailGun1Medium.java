package cool.ender.stardust.component.turret.medium;

import cool.ender.stardust.Stardust;
import cool.ender.stardust.component.tube.ITubeConnectable;
import cool.ender.stardust.component.turret.AbstractTurret;
import cool.ender.stardust.registry.TileRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationData;

public class RailGun1Medium extends AbstractTurret {


    public static class Block extends AbstractTurret.Block implements ITubeConnectable {

        public Block() {
            super(Properties.of(Material.STONE).noOcclusion());
        }

        public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
            return new Tile(blockPos, blockState);
        }

        @Override
        public boolean isConnectable(Direction direction, BlockState self) {
            return true;
        }
    }

    public static class Tile extends AbstractTurret.Tile {

        public Tile(BlockPos p_155229_, BlockState p_155230_) {
            super(TileRegistry.RAIL_GUN_1_MEDIUM.get(), p_155229_, p_155230_);
        }

        @Override
        public void registerControllers(AnimationData data) {

        }
    }

    public static class Model extends AbstractTurret.Model {

        @Override
        public ResourceLocation getModelLocation(AbstractTurret.Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "geo/rail_gun_4_medium.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(AbstractTurret.Tile object) {
            return new ResourceLocation(Stardust.MOD_ID, "textures/block/rail_gun_4_medium.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(AbstractTurret.Tile animatable) {
            return null;
        }
    }

    public static class Renderer extends AbstractTurret.Renderer {

        public Renderer(BlockEntityRendererProvider.Context rendererProvider) {
            super(rendererProvider, new Model());
        }
    }

    public abstract static class Listener extends AbstractTurret.Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener extends AbstractTurret.Listener.ClientListener {
            @SubscribeEvent
            public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
                event.registerBlockEntityRenderer(TileRegistry.RAIL_GUN_1_MEDIUM.get(), RailGun1Medium.Renderer::new);
            }
        }
    }

    @Override
    public String getRegisterName() {
        return "rail_gun_1_medium";
    }
}
