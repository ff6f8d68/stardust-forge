package cool.ender.stardust.component.turret;

import cool.ender.stardust.Stardust;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.swing.plaf.PanelUI;
import java.util.HashSet;

abstract public class AbstractTurret{

    public String getRegisterName() {
        return null;
    }

    public enum REGISTRY_TYPE {
        BLOCK, TILE, MODEL, ITEM
    }

    public String getRegisterName(REGISTRY_TYPE type) {
        return getRegisterName() + "_" + (type.toString()).toLowerCase();
    }

    public abstract static class Block extends BaseEntityBlock {

        public Block(Properties properties) {
            super(properties);
        }

        @Override
        @NotNull
        public RenderShape getRenderShape(@NotNull BlockState state) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }

    }

    public abstract static class Model extends AnimatedGeoModel<Tile> {

    }

    public abstract static class Tile extends BlockEntity implements IAnimatable {

        public HashSet<BlockPos> relatedShieldGenerators = new HashSet<>();
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Tile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
            super(p_155228_, p_155229_, p_155230_);
        }

        public void addShieldGenerator(BlockPos pos) {
            this.relatedShieldGenerators.add(pos);
        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }

    public abstract static class Renderer extends GeoBlockRenderer<Tile> {

        public Renderer(BlockEntityRendererProvider.Context rendererProvider, AnimatedGeoModel<Tile> modelProvider) {
            super(rendererProvider, modelProvider);
        }
    }

    public abstract static class Item extends net.minecraft.world.item.Item {

        public Item(Properties properties) {
            super(properties);
        }
    }

    public abstract static class Listener {
        @Mod.EventBusSubscriber(modid = Stardust.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public abstract static class ClientListener {

        }

        public abstract static class ServerListener {

        }
    }
}
