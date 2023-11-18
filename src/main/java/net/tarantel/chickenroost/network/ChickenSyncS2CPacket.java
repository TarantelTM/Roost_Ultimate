package net.tarantel.chickenroost.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.tarantel.chickenroost.block.entity.breeder_entity;
import net.tarantel.chickenroost.block.entity.roost_entity;

public class ChickenSyncS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){

        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for(int i = 0; i < size; i++){
            list.set(i, buf.readItemStack());
        }
        BlockPos position = buf.readBlockPos();

        assert client.world != null;
        if(client.world.getBlockEntity(position) instanceof breeder_entity blockEntity){
            blockEntity.setInventory(list);
        }
        if(client.world.getBlockEntity(position) instanceof roost_entity blockEntity){
            blockEntity.setInventory(list);
        }
    }
}
