package mod.syconn.sycore.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public record BlockID(BlockState state, BlockPos pos) {

    public AnchorPos anchorBlock(AnchorPos ap){
        return new AnchorPos(pos.getX() - ap.x(), pos.getY() - ap.y(), pos.getZ() - ap.z());
    }

    public void place(Level level){
        level.setBlock(pos, state, 2);
    }

    public static BlockID read(CompoundTag tag) {
        return new BlockID(NbtUtils.readBlockState(tag.getCompound("state")), NbtUtils.readBlockPos(tag.getCompound("pos")));
    }

    public CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.put("state", NbtUtils.writeBlockState(state));
        tag.put("pos", NbtUtils.writeBlockPos(pos));
        return tag;
    }
}
