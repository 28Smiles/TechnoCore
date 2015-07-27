package technocore.networking.packets;

import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class PacketBase {

	public PacketBase() {}

	public abstract void encodeInto(ChannelHandlerContext paramChannelHandlerContext, ByteBuf localByteBuf);

	public abstract void decodeInto(ChannelHandlerContext paramChannelHandlerContext, ByteBuf slice);

	public abstract void handleClientSide(EntityPlayer localObject);

	public abstract void handleServerSide(EntityPlayer localObject);

	public abstract String getCanonicalName();
	
}
