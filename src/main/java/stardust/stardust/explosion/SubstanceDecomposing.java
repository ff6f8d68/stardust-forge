package stardust.stardust.explosion;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stardust.stardust.Stardust;
import stardust.stardust.event.SubstanceDecomposerTickingHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class SubstanceDecomposing {


    private long energy;
    private final float attribute;
    private final BlockPos startPos;
    private final World world;
    private Queue<BlockPos> decomposeTaskQueue = new LinkedList<>();
    private final HashMap<BlockPos, Boolean> checkedPos = new HashMap<>();

    public boolean isTerminated = false;

    public SubstanceDecomposing(World world, BlockPos pos, long energy, float attribute) {
        this.energy = energy;
        this.startPos = pos;
        this.world = world;
        this.attribute = attribute;
        decomposeTaskQueue.add(pos);
        checkedPos.put(pos, true);
        this.decompose();
    }

    public void execute() {
        SubstanceDecomposerTickingHandler.addTask(this);
    }

    public void terminate() {
        this.isTerminated = true;
    }

    public void decompose() {
        BlockPos nowPos;
        Queue<BlockPos> newDecomposeTaskQueue = new LinkedList<>();

        while (!decomposeTaskQueue.isEmpty()) {
            nowPos = decomposeTaskQueue.poll();
            if (nowPos == null) break;
            int energyCost = substanceEnergyCost(nowPos);
            checkedPos.put(nowPos, true);
            if (energyCost > 0 && this.energy >= energyCost) {
                this.energy -= energyCost;
                this.world.setBlockState(nowPos, Blocks.AIR.getDefaultState());
                if (isTargetPosValid(nowPos.add(1, 0, 0))) newDecomposeTaskQueue.add(nowPos.add(1, 0, 0));
                if (isTargetPosValid(nowPos.add(0, 1, 0))) newDecomposeTaskQueue.add(nowPos.add(0, 1, 0));
                if (isTargetPosValid(nowPos.add(0, 0, 1))) newDecomposeTaskQueue.add(nowPos.add(0, 0, 1));
                if (isTargetPosValid(nowPos.add(-1, 0, 0))) newDecomposeTaskQueue.add(nowPos.add(-1, 0, 0));
                if (isTargetPosValid(nowPos.add(0, -1, 0))) newDecomposeTaskQueue.add(nowPos.add(0, -1, 0));
                if (isTargetPosValid(nowPos.add(0, 0, -1))) newDecomposeTaskQueue.add(nowPos.add(0, 0, -1));
            }
        }

        if (this.energy <= 0) {
            this.terminate();
            return;
        }

        this.decomposeTaskQueue = newDecomposeTaskQueue;



//        while (energy > 0) {
//            nowPos = decomposeTaskQueue.poll();
//            if (nowPos == null) break;
//            int energyCost = substanceEnergyCost(nowPos);
//            checkedPos.put(nowPos, true);
//
//            if (energyCost > 0 && this.energy >= energyCost) {
//                this.energy -= energyCost;
//                this.world.setBlockState(nowPos, Blocks.AIR.getDefaultState());
//
//                if (isTargetPosValid(nowPos.add(1, 0, 0))) decomposeTaskQueue.add(nowPos.add(1, 0, 0));
//                if (isTargetPosValid(nowPos.add(0, 1, 0))) decomposeTaskQueue.add(nowPos.add(0, 1, 0));
//                if (isTargetPosValid(nowPos.add(0, 0, 1))) decomposeTaskQueue.add(nowPos.add(0, 0, 1));
//                if (isTargetPosValid(nowPos.add(-1, 0, 0))) decomposeTaskQueue.add(nowPos.add(-1, 0, 0));
//                if (isTargetPosValid(nowPos.add(0, -1, 0))) decomposeTaskQueue.add(nowPos.add(0, -1, 0));
//                if (isTargetPosValid(nowPos.add(0, 0, -1))) decomposeTaskQueue.add(nowPos.add(0, 0, -1));
//            }
//        }

//        int energyCost = isSubstanceValid(world, targetPos);
//
//        if (energyCost <= 0) return;
//
//        world.setBlockState(targetPos, Blocks.AIR.getDefaultState(), 11);
//
//        if (isTargetPosValid(targetPos.add(1, 0, 0), energyFrom))
//            decompose(world, targetPos.add(1, 0, 0), targetPos, energy - energyCost);
//
//        if (isTargetPosValid(targetPos.add(0, 1, 0), energyFrom))
//            decompose(world, targetPos.add(0, 1, 0), targetPos, energy - energyCost);
//
//        if (isTargetPosValid(targetPos.add(0, 0, 1), energyFrom))
//            decompose(world, targetPos.add(0, 0, 1), targetPos, energy - energyCost);
//
//        if (isTargetPosValid(targetPos.add(-1, 0, 0), energyFrom))
//            decompose(world, targetPos.add(-1, 0, 0), targetPos, energy - energyCost);
//
//        if (isTargetPosValid(targetPos.add(0, -1, 0), energyFrom))
//            decompose(world, targetPos.add(0, -1, 0), targetPos, energy - energyCost);
//
//        if (isTargetPosValid(targetPos.add(0, 0, -1), energyFrom))
//            decompose(world, targetPos.add(0, 0, -1), targetPos, energy - energyCost);

    }

    private int substanceEnergyCost(BlockPos pos) {

        if (this.world.getBlockState(pos).isAir()) return 0;


        return (int) Math.ceil(this.world.getBlockState(pos).getBlock().getExplosionResistance());

    }

    private boolean isTargetPosValid(BlockPos target) {
        return checkedPos.get(target) == null && this.world.getBlockState(target).getBlock().getExplosionResistance() <= this.attribute;
    }

    public BlockPos getStartPos() {
        return this.startPos;
    }

}
