package stardust.stardust.turret;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
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

    abstract public class Block extends net.minecraft.block.Block {

        public Block(Properties properties) {
            super(properties);
        }
    }

    abstract public class Model extends AnimatedGeoModel<Tile> {

    }

    abstract public class Tile extends TileEntity implements IAnimatable {

        public Tile(TileEntityType<?> tile) {
            super(tile);
        }
    }

    abstract public class Renderer extends GeoBlockRenderer<Tile> {

        public Renderer(TileEntityRendererDispatcher rendererDispatcherIn, AnimatedGeoModel<Tile> modelProvider) {
            super(rendererDispatcherIn, modelProvider);
        }
    }

    abstract public class Item extends net.minecraft.item.Item {

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
