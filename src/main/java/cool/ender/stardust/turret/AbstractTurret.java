package cool.ender.stardust.turret;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

abstract public class AbstractTurret {
    public static String registerName;
    public static boolean isRegistered = selfRegistry();

    public static boolean selfRegistry() {
        //DO ALL REG STUFF HERE
        return false;
    }

    enum REGISTRY_TYPE {
        BLOCK, TILE, MODEL
    }

    static String getRegisterName(REGISTRY_TYPE type) {
        return registerName + "_" + (type.toString()).toLowerCase();
    }

    abstract public class Block extends net.minecraft.world.level.block.Block {

        public Block(Properties properties) {
            super(properties);
        }
    }

    abstract public class Model extends AnimatedGeoModel<Tile> {

    }

    abstract public class Tile extends BlockEntity implements IAnimatable {

        public Tile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
            super(p_155228_, p_155229_, p_155230_);
        }
    }

    abstract public class Renderer extends GeoBlockRenderer<Tile> {

        public Renderer(BlockEntityRendererProvider.Context rendererProvider, AnimatedGeoModel<Tile> modelProvider) {
            super(rendererProvider, modelProvider);
        }
    }

    abstract public class Item extends net.minecraft.world.item.Item {

        public Item(Properties properties) {
            super(properties);
        }
    }

    abstract public class Events {
        abstract public class ClientEvents {

        }

        abstract public class ServerEvents {

        }
    }
}
