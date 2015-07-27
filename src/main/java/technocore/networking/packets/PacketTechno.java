package technocore.networking.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public abstract class PacketTechno extends PacketBase {
	
	  private ByteArrayOutputStream arrayout;
	  private DataOutputStream dataout;
	  public DataInputStream datain;

	  public PacketTechno()
	  {
	    this.arrayout = new ByteArrayOutputStream();
	    this.dataout = new DataOutputStream(this.arrayout);
	  }

	  public PacketTechno(byte[] paramArrayOfByte)
	  {
	    this.datain = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
	  }

	  public PacketTechno addString(String paramString)
	  {
	    try {
	      this.dataout.writeUTF(paramString);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addUUID(UUID paramUUID)
	  {
	    try {
	      this.dataout.writeLong(paramUUID.getMostSignificantBits());
	      this.dataout.writeLong(paramUUID.getLeastSignificantBits());
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addLong(long paramLong)
	  {
	    try {
	      this.dataout.writeLong(paramLong);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addInt(int paramInt)
	  {
	    try {
	      this.dataout.writeInt(paramInt);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addBool(boolean paramBoolean)
	  {
	    try {
	      this.dataout.writeBoolean(paramBoolean);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addByte(byte paramByte)
	  {
	    try {
	      this.dataout.writeByte(paramByte);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addByte(int paramInt)
	  {
	    return addByte((byte)paramInt);
	  }

	  public PacketTechno addShort(short paramShort)
	  {
	    try {
	      this.dataout.writeShort(paramShort);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addShort(int paramInt)
	  {
	    return addShort((short)paramInt);
	  }

	  public PacketTechno addByteArray(byte[] paramArrayOfByte)
	  {
	    try {
	      this.dataout.write(paramArrayOfByte);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addFloat(float paramFloat)
	  {
	    try {
	      this.dataout.writeFloat(paramFloat);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addItemStack(ItemStack paramItemStack)
	  {
	    try {
	      writeItemStack(paramItemStack);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	    return this;
	  }

	  public PacketTechno addCoords(TileEntity paramTileEntity)
	  {
	    addInt(paramTileEntity.getPos().getX());
	    addInt(paramTileEntity.getPos().getY());
	    return addInt(paramTileEntity.getPos().getZ());
	  }
	  
	  public PacketTechno addCoords(BlockPos paramBlockPos)
	  {
	    addInt(paramBlockPos.getX());
	    addInt(paramBlockPos.getY());
	    return addInt(paramBlockPos.getZ());
	  }

	  public PacketTechno addCoords(int paramInt1, int paramInt2, int paramInt3)
	  {
	    addInt(paramInt1);
	    addInt(paramInt2);
	    return addInt(paramInt3);
	  }

	  public String getString()
	  {
	    try {
	      return this.datain.readUTF();
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }return null;
	  }

	  public UUID getUUID()
	  {
	    try
	    {
	      long l1 = this.datain.readLong();
	      long l2 = this.datain.readLong();
	      return new UUID(l1, l2);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }return null;
	  }

	  public long getLong()
	  {
	    try
	    {
	      return this.datain.readLong();
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }return 0L;
	  }

	  public int getInt()
	  {
	    try
	    {
	      return this.datain.readInt();
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }return 0;
	  }

	  public boolean getBool()
	  {
	    try
	    {
	      return this.datain.readBoolean();
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }return false;
	  }

	  public byte getByte()
	  {
	    try
	    {
	      return this.datain.readByte();
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }return 0;
	  }

	  public short getShort()
	  {
	    try
	    {
	      return this.datain.readShort();
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }return 0;
	  }

	  public void getByteArray(byte[] paramArrayOfByte)
	  {
	    try
	    {
	      this.datain.readFully(paramArrayOfByte);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	  }

	  public float getFloat()
	  {
	    try {
	      return this.datain.readFloat();
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }return 0.0F;
	  }

	  public ItemStack getItemStack()
	  {
	    try
	    {
	      return readItemStack();
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }return null;
	  }

	  public int[] getCoords()
	  {
	    return new int[] { getInt(), getInt(), getInt() };
	  }
	  
	  public BlockPos getBlockCoords()
	  {
		return new BlockPos(getInt(), getInt(), getInt());
	  }

	  private void writeItemStack(ItemStack paramItemStack) throws IOException
	  {
	    if (paramItemStack == null) {
	      addShort(-1);
	    } else {
	      addShort(Item.getIdFromItem(paramItemStack.getItem()));
	      addByte(paramItemStack.stackSize);
	      addShort(paramItemStack.getItemDamage());
	      writeNBT(paramItemStack.getTagCompound());
	    }
	  }

	  public ItemStack readItemStack() throws IOException
	  {
	    ItemStack localItemStack = null;
	    int i = getShort();

	    if (i >= 0) {
	      int j = getByte();
	      int k = getShort();
	      localItemStack = new ItemStack(Item.getItemById(i), j, k);
	      localItemStack.setTagCompound(readNBT());
	    }

	    return localItemStack;
	  }

	  public void writeNBT(NBTTagCompound paramNBTTagCompound) throws IOException
	  {
	    if (paramNBTTagCompound == null) {
	      addShort(-1);
	    } else {
	      CompressedStreamTools.writeCompressed(paramNBTTagCompound, arrayout);
	    }
	  }

	  public NBTTagCompound readNBT() throws IOException
	  {
	    return CompressedStreamTools.readCompressed(datain);
	  }

	  public void encodeInto(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf)
	  {
	    paramByteBuf.writeBytes(this.arrayout.toByteArray());
	  }

	  public void decodeInto(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf)
	  {
	    this.datain = new DataInputStream(new ByteArrayInputStream(paramByteBuf.array()));
	    try {
	      this.datain.skipBytes(1);
	    } catch (IOException localIOException) {
	      localIOException.printStackTrace();
	    }
	  }

	  public void handleClientSide(EntityPlayer paramEntityPlayer)
	  {
	    if (paramEntityPlayer == null) {
	      return;
	    }
	    handlePacket(paramEntityPlayer, false);
	  }

	  public void handleServerSide(EntityPlayer paramEntityPlayer)
	  {
	    if (paramEntityPlayer == null) {
	      return;
	    }
	    handlePacket(paramEntityPlayer, true);
	  }

	  public abstract void handlePacket(EntityPlayer paramEntityPlayer, boolean isServer);
}
