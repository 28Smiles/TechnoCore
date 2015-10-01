package technocore.block.tileentity;

import java.util.ArrayList;

import technocore.TechnoCore;
import technocore.block.container.TechnoCoreContainer;
import technocore.datavalues.Localization;
import technocore.networking.IPacketHandler;
import technocore.networking.PacketHandler;
import technocore.networking.packets.PacketTechno;
import technocore.networking.packets.PacketTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TechnoCoreTileEntity extends TileEntity implements IPacketHandler {

	@Override
	public void handlePacket(PacketTechno packet)
	{
		switch (packet.getByte()) {
		case 1:
			handleDescriptionPacket(packet);
			break;
		case 2:
			handleGuiPacket(packet);
			break;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(this instanceof ISecureable)
			((ISecureable)this).writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(this instanceof ISecureable)
			((ISecureable)this).readFromNBT(nbt);
	}

	public abstract void handleGuiPacket(PacketTechno packet);

	public abstract void handleDescriptionPacket(PacketTechno packet);

	@SuppressWarnings(value = { "all" })
	public boolean openGui(World world, BlockPos bp, IBlockState bs, EntityPlayer player, EnumFacing facing, float x, float y, float z) {
		if(this instanceof ISecureable)
		{
			if(((ISecureable)this).canPlayerOpen(player.getUniqueID()))
			{
				player.openGui(TechnoCore.instance, 0, world, getPos().getX(), getPos().getY(), getPos().getZ());
				return true;
			} else {
				player.addChatMessage(new ChatComponentText(I18n.format(Localization.access_denied, null)));
				return false;
			}
		} else {
			player.openGui(TechnoCore.instance, 0, world, getPos().getX(), getPos().getY(), getPos().getZ());
			return true;
		}
	}

	@SideOnly(Side.CLIENT)
	public abstract Gui getGui(IInventory player);

	public abstract Container getContainer(InventoryPlayer player);

	public PacketTechno getGuiPacket() {
		return getPacket().addByte((byte)2);
	}
	
	public PacketTechno getTechDescriptionPacket() {
		return getPacket().addByte((byte)1);
	}

	public PacketTechno getPacket() {
		return new PacketTileEntity(this);
	}

	public boolean isUseable(EntityPlayer paramEntityPlayer) {
		return true;
	}

	public void sendGuiNetworkData(TechnoCoreContainer technoCoreContainer, ICrafting iCrafting)
	{
		if ((iCrafting instanceof EntityPlayer)) {
			PacketTechno localPacketCoFHBase = getGuiPacket();
			if (localPacketCoFHBase != null)
				PacketHandler.sendTo(localPacketCoFHBase, (EntityPlayer)iCrafting);
		}
	}
}
