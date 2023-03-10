package mod.syconn.sycore.api.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class ClientMenuBlockEntity extends MenuBlockEntity {

    public ClientMenuBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    protected CompoundTag saveData(){
        CompoundTag tag = new CompoundTag();
        tag.put("items", itemHandler.serializeNBT());
        return tag;
    }


    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveData();
    }

    public void update(){
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
        setChanged();
    }
}
