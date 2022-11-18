package cool.ender.stardust.missile.launcher;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class VerticalMissileLauncher {
    public static class Block extends BaseEntityBlock {


        public Block() {
            super(Properties.of(Material.METAL));
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return null;
        }
    }

    public static class Tile extends BlockEntity implements IAnimatable {
        public AnimationFactory factory = GeckoLibUtil.createFactory(this);

        public Tile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
            super(p_155228_, p_155229_, p_155230_);
        }

        @Override
        public void registerControllers(AnimationData data) {

        }

        @Override
        public AnimationFactory getFactory() {
            return factory;
        }
    }
}
