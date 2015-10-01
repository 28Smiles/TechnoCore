package technocore.mechanic.fluid.pipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import technocore.TechnoCore;
import technocore.networking.IPacketHandler;
import technocore.networking.PacketHandler;
import technocore.networking.packets.PacketTechno;
import technocore.networking.packets.PacketTileEntity;
import akka.japi.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileFluidPipe extends TileEntity implements IFluidPipe, IUpdatePlayerListBox, IPacketHandler {

	//TODO evtl. Speicherverbrauch senken durch normales Array
	protected List<EnumFacing> neighbors = new ArrayList<EnumFacing>();
	protected List<EnumFacing> allowedFacings = new ArrayList<EnumFacing>();

	protected FluidTank tank;
	private boolean isTempTankRefreshed = true;
	private FluidStack inflow = new FluidStack(FluidRegistry.WATER, 0);
	private short transferRate;

	private boolean placed = false;
	private boolean loaded = false;

	public TileFluidPipe(int tanksize, short transferRate)
	{
		tank = new FluidTank(tanksize);
		this.transferRate = transferRate;
		for(EnumFacing face : EnumFacing.VALUES)
			allowedFacings.add(face);
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill)
	{
		if(!loaded)
			return 0;
		if(!isTempTankRefreshed)
			return 0;
		if(inflow.amount == 0)
		{
			if(resource.amount <= tank.getCapacity() - tank.getFluidAmount())
			{
				if(doFill)
					inflow = resource.copy();
				return resource.amount;
			} 
			else
			{
				if(doFill)
					inflow = new FluidStack(resource.getFluid(), tank.getCapacity() - tank.getFluidAmount());
				return tank.getCapacity() - tank.getFluidAmount();
			}
		}
		else
		{
			if(inflow.isFluidEqual(resource))
			{
				if(resource.amount <= tank.getCapacity() - (tank.getFluidAmount() + inflow.amount))
				{
					if(doFill)
						inflow.amount += resource.amount;
					return resource.amount;
				}
				else
				{
					if(doFill)
						inflow.amount += tank.getCapacity() - (tank.getFluidAmount() + inflow.amount);
					return tank.getCapacity() - (tank.getFluidAmount() + inflow.amount);
				}
			}
		}
		return 0;
	}

	@Override
	public void update()
	{
		if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER))
		{
			if(!placed)
			{
				onPipeInitialized();
				placed = true;
			}
			if(!loaded)
			{
				Swapper.INSTANCE.register(this);
				loaded = true;
			}
		}
	}

	@SideOnly(Side.SERVER)
	public void handleUpdateEnd(ServerTickEvent event)
	{
		if(tank.fill(inflow, true) != inflow.amount)
		{
			TechnoCore.log.warn("Unpossible Inflow Detected at TileFluidPipe please check this!");
		}
		inflow = new FluidStack(FluidRegistry.WATER, 0);
		PacketTechno packet = getPacket();
		try {
			packet.addByte(0);
			packet.writeNBT(tank.writeToNBT(new NBTTagCompound()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PacketHandler.sendToAllAround(packet, worldObj, pos);
	}

	@SideOnly(Side.SERVER)
	public void onPipesChanged() {
		PacketTechno packet = getPacket();
		packet.addByte(1);
		packet.addInt(neighbors.size());
		for(int i = 0; i < neighbors.size(); i++)
			packet.addInt(neighbors.get(i).getIndex());
		PacketHandler.sendToAllAround(packet, worldObj, pos);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handlePacket(PacketTechno packet)
	{
		switch(packet.getByte()) {
			case 0: {
				try {
					tank.readFromNBT(packet.readNBT());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			case 1: {
				neighbors.clear();
				int m = packet.getInt();
				for(int i = 0; i < m; i++)
					neighbors.add(EnumFacing.getFront(packet.getInt()));
				break;
			}
		}
	}

	@SideOnly(Side.SERVER)
	public PacketTechno getPacket()
	{
		PacketTileEntity packet = new PacketTileEntity(pos);
		return packet;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		compound.setBoolean("ittr", isTempTankRefreshed);
		compound.setShort("transferRate", transferRate);
		compound.setBoolean("placed", placed);
		compound.setBoolean("loaded", loaded);
		NBTTagList nbtTagListNeighbors = new NBTTagList();
		for(EnumFacing face : neighbors)
			nbtTagListNeighbors.set(neighbors.indexOf(face), new NBTTagInt(face.getIndex()));
		compound.setTag("neighbours", nbtTagListNeighbors);
		NBTTagList nbtTagListAllowedFacings = new NBTTagList();
		for(EnumFacing face : allowedFacings)
			nbtTagListAllowedFacings.set(allowedFacings.indexOf(face), new NBTTagInt(face.getIndex()));
		compound.setTag("allowed", nbtTagListAllowedFacings);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		tank = tank.readFromNBT(compound.getCompoundTag("tank"));
		isTempTankRefreshed = compound.getBoolean("ittr");
		transferRate = compound.getShort("transferRate");
		placed = compound.getBoolean("placed");
		loaded = compound.getBoolean("loaded");
		NBTTagList nbtTagListNeighbors = (NBTTagList) compound.getTag("neighbours");
		neighbors.clear();
		for(int i = 0; i < nbtTagListNeighbors.tagCount(); i++)
			neighbors.add(EnumFacing.getFront(((NBTTagInt)nbtTagListNeighbors.get(i)).getInt()));
		NBTTagList nbtTagListAllowedFacings = (NBTTagList) compound.getTag("allowed");
		allowedFacings.clear();
		for(int i = 0; i < nbtTagListAllowedFacings.tagCount(); i++)
			allowedFacings.add(EnumFacing.getFront(((NBTTagInt)nbtTagListAllowedFacings.get(i)).getInt()));
	}

	protected void onPipeInitialized()
	{
		EnumFacing[] facings = searchPipes();
		for(EnumFacing face : facings)
			connectPipe(face);
	}

	@Override
	public void onChunkUnload()
	{
		Swapper.INSTANCE.unregister(this);
		loaded = false;
	}

	protected void onAllowedFacingsChanged()
	{
		neighbors.clear();
		EnumFacing[] facings = searchPipes();
		for(EnumFacing face : facings)
			connectPipe(face);
	}

	@Override
	public boolean onNeighbourConnects(EnumFacing from, IFluidPipe pipe)
	{
		if(allowedFacings.contains(from))
			return true;
		return false;
	}

	@Override
	public boolean onNeighbourDisconnects(EnumFacing from, IFluidPipe pipe)
	{
		if(neighbors.contains(from))
			return true;
		return false;
	}

	protected IFluidPipe[] getConnectedPipes(EnumFacing[] sides)
	{
		IFluidPipe[] pipeArray = new IFluidPipe[sides.length];
		for(int i = 0; i < sides.length; i++)
			pipeArray[i] = (IFluidPipe) getWorld().getTileEntity(pos.add(sides[i].getDirectionVec()));
		return pipeArray;
	}

	private IFluidPipe getPipe(EnumFacing face)
	{
		TileEntity tile = getWorld().getTileEntity(pos.offset(face));
		if(tile instanceof IFluidPipe)
			return (IFluidPipe) tile;
		return null;
	}

	private EnumFacing[] searchPipes()
	{
		List<EnumFacing> facings = new ArrayList<EnumFacing>();
		for(EnumFacing face : EnumFacing.VALUES)
			if(canConnect(face))
				facings.add(face);
		return facings.toArray(new EnumFacing[facings.size()]);
	}

	private boolean canConnect(EnumFacing face)
	{
		return getWorld().getTileEntity(pos.offset(face)) instanceof IFluidPipe;
	}

	@Override
	public boolean connectPipe(EnumFacing facing)
	{
		if(neighbors.contains(facing))
			return true;
		if(getPipe(facing).onNeighbourConnects(facing.getOpposite(), this))
		{
			if(allowedFacings.contains(facing))
			{
				neighbors.add(facing);
				onPipesChanged();
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean disconnectPipe(EnumFacing facing) {
		boolean rem = neighbors.remove(facing);
		onPipesChanged();
		return rem;
	}

	@Override
	public EnumFacing[] getConnectedSides()
	{
		return neighbors.toArray(new EnumFacing[neighbors.size()]);
	}

	@Override
	public EnumFacing[] getDirections()
	{
		return allowedFacings.toArray(new EnumFacing[allowedFacings.size()]);
	}

	@Override
	public IFluidTank getTank()
	{
		return tank;
	}

	@Override
	public int get_mBperTick()
	{
		return transferRate;
	}

	@Override
	public BlockPos getBlockPos()
	{
		return pos;
	}

	@Override
	public World getWorld()
	{
		return super.getWorld();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TileFluidPipe)
		{
			TileFluidPipe pipe = (TileFluidPipe) obj;
			if(pipe.pos.equals(this.pos))
				return super.equals(obj);
		}
		return false;
	}

	public int getPipeSize() {
		return 10;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(getPos(), getPos().add(1, 1, 1));
	}

	public static class Swapper
	{
		public static final Swapper INSTANCE = new Swapper();

		private List<TileFluidPipe> registry = new ArrayList<TileFluidPipe>();

		@EventHandler
		public void event(TickEvent.ServerTickEvent event)
		{
			if(event.phase.equals(TickEvent.Phase.END) && event.type.equals(TickEvent.Type.WORLD) && event.side.equals(Side.SERVER))
			{
				for(TileFluidPipe pipe : registry)
				{
					pipe.handleUpdateEnd(event);
				}
			}
		}

		public boolean register(TileFluidPipe pipe)
		{
			return registry.add(pipe);
		}

		public boolean unregister(TileFluidPipe pipe)
		{
			return registry.remove(pipe);
		}
	}
}
