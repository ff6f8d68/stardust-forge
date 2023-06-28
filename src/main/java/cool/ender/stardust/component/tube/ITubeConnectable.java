package cool.ender.stardust.component.tube;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface ITubeConnectable {
    boolean isConnectable(Direction direction, BlockState self);
}
