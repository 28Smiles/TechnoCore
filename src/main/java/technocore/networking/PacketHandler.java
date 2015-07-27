package technocore.networking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import technocore.networking.packets.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ChannelHandler.Sharable
public class PacketHandler extends MessageToMessageCodec<FMLProxyPacket, PacketBase> {
	
	public static final PacketHandler instance = new PacketHandler();
	private EnumMap<Side, FMLEmbeddedChannel> channels;
	private final LinkedList<Class<? extends PacketBase>> packets = new LinkedList();
	private boolean isPostInitialised = false;
	
	
	public boolean registerPacket(Class<? extends PacketBase> paramClass)
	  {
	    if (this.packets.size() > 256) {
	      return false;
	    }
	    if (this.packets.contains(paramClass)) {
	      return false;
	    }
	    if (this.isPostInitialised)
	    {
	      return false;
	    }
	    this.packets.add(paramClass);
	    return true;
	  }

	  protected void encode(ChannelHandlerContext paramChannelHandlerContext, PacketBase paramPacketBase, List<Object> paramList) throws Exception
	  {
	      ByteBuf localByteBuf = Unpooled.buffer();
	      Class localClass = paramPacketBase.getClass();

   	      if (!this.packets.contains(paramPacketBase.getClass())) {
	          throw new NullPointerException("No Packet Registered for: " + paramPacketBase.getClass().getCanonicalName());
	      }
	      int i = (byte)this.packets.indexOf(localClass);
	      localByteBuf.writeByte(i);
	      paramPacketBase.encodeInto(paramChannelHandlerContext, localByteBuf);
	      FMLProxyPacket localFMLProxyPacket = new FMLProxyPacket(new PacketBuffer(localByteBuf), (String)paramChannelHandlerContext.channel().attr(NetworkRegistry.FML_CHANNEL).get());
	      paramList.add(localFMLProxyPacket);
	  }

	  protected void decode(ChannelHandlerContext paramChannelHandlerContext, FMLProxyPacket paramFMLProxyPacket, List<Object> paramList)
	    throws Exception
	  {
	    ByteBuf localByteBuf = paramFMLProxyPacket.payload();
	    int i = localByteBuf.readByte();
	    Class localClass = (Class)this.packets.get(i);

	    if (localClass == null) {
	      throw new NullPointerException("No packet registered for discriminator: " + i);
	    }
	    PacketBase localPacketBase = (PacketBase)localClass.newInstance();
	    localPacketBase.decodeInto(paramChannelHandlerContext, localByteBuf.slice());
	    Object localObject;
	    switch (FMLCommonHandler.instance().getEffectiveSide().ordinal()) {
	    case 0:
	      localObject = getClientPlayer();
	      localPacketBase.handleClientSide((EntityPlayer)localObject);
	      break;
	    case 1:
	      INetHandler localINetHandler = (INetHandler)paramChannelHandlerContext.channel().attr(NetworkRegistry.NET_HANDLER).get();
	      localObject = ((NetHandlerPlayServer)localINetHandler).playerEntity;
	      localPacketBase.handleServerSide((EntityPlayer)localObject);
	      break;
	    }
	  }

	  public void initialize()
	  {
	    this.channels = NetworkRegistry.INSTANCE.newChannel("techcore", new ChannelHandler[] { this });
	  }

	  public void postInit()
	  {
	    if (this.isPostInitialised) {
	      return;
	    }
	    this.isPostInitialised = true;
	    Collections.sort(this.packets, new Comparator()
	    {
	    	@Override
	        public int compare(Object paramClass1, Object paramClass2)
	        {
	    		int i = String.CASE_INSENSITIVE_ORDER.compare(((PacketBase)paramClass1).getCanonicalName(), ((PacketBase)paramClass2).getCanonicalName());
		        if (i == 0) {
		            i = ((PacketBase)paramClass1).getCanonicalName().compareTo(((PacketBase)paramClass2).getCanonicalName());
		        }
		        return i;
	        } 
	     } );
	  }

	  @SideOnly(Side.CLIENT)
	  private EntityPlayer getClientPlayer() {
	    return Minecraft.getMinecraft().thePlayer;
	  }

	  public static void sendToAll(PacketBase paramPacketBase)
	  {
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).writeAndFlush(paramPacketBase);
	  }

	  public static void sendTo(PacketBase paramPacketBase, EntityPlayerMP paramEntityPlayerMP)
	  {
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(paramEntityPlayerMP);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).writeAndFlush(paramPacketBase);
	  }

	  public static void sendTo(PacketBase paramPacketBase, EntityPlayer paramEntityPlayer)
	  {
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(paramEntityPlayer);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).writeAndFlush(paramPacketBase);
	  }

	  public static void sendToAllAround(PacketBase paramPacketBase, NetworkRegistry.TargetPoint paramTargetPoint)
	  {
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(paramTargetPoint);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).writeAndFlush(paramPacketBase);
	  }

	  public static void sendToAllAround(PacketBase paramPacketBase, TileEntity paramTileEntity)
	  {
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);

	    ((FMLEmbeddedChannel)instance.channels
	      .get(Side.SERVER))
	      .attr(FMLOutboundHandler.FML_MESSAGETARGETARGS)
	      .set(new NetworkRegistry.TargetPoint(paramTileEntity
	      .getWorld().provider.getDimensionId(), paramTileEntity.getPos().getX(), paramTileEntity.getPos().getY(), paramTileEntity.getPos().getZ(), 192.0D));
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).writeAndFlush(paramPacketBase);
	  }

	  public static void sendToAllAround(PacketBase paramPacketBase, World paramWorld, int paramInt1, int paramInt2, int paramInt3)
	  {
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS)
	      .set(new NetworkRegistry.TargetPoint(paramWorld.provider.getDimensionId(), paramInt1, paramInt2, paramInt3, 192.0D));

	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).writeAndFlush(paramPacketBase);
	  }

	  public static void sendToDimension(PacketBase paramPacketBase, int paramInt)
	  {
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(Integer.valueOf(paramInt));
	    ((FMLEmbeddedChannel)instance.channels.get(Side.SERVER)).writeAndFlush(paramPacketBase);
	  }

	  public static void sendToServer(PacketBase paramPacketBase)
	  {
	    ((FMLEmbeddedChannel)instance.channels.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
	    ((FMLEmbeddedChannel)instance.channels.get(Side.CLIENT)).writeAndFlush(paramPacketBase);
	  }

	  public static Packet toMCPacket(PacketBase paramPacketBase)
	  {
	    return ((FMLEmbeddedChannel)instance.channels.get(FMLCommonHandler.instance().getEffectiveSide())).generatePacketFrom(paramPacketBase);
	  }
}
