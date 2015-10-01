package technocore.multiblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.attribute.HashAttributeSet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MultiBlockRegistry {

	public static MultiBlockRegistry INSTANCE = new MultiBlockRegistry();

	private List<MultiBlockStructure> multiblocks = new ArrayList<MultiBlockStructure>();

	private List<IMultiblock> activeMultiblocks = new ArrayList<IMultiblock>();

	private HashMap<String, IMultiblock> multiblockTiles = new HashMap<String, IMultiblock>();

	public void registerNewMultiblockStructure(IBlockState[][][] structure, IMultiblock mb)
	{
		multiblocks.add(new MultiBlockStructure(structure, mb));
		multiblockTiles.put(mb.getUnlocalizedName(), mb);
	}
	
	public MultiBlockStructure checkStructures(IBlockState[][][] mbsructure)
	{
		for(MultiBlockStructure mbs : multiblocks)
		{
			int i = mbs.contains(mbsructure);
			if(i != -1)
				return mbs;
		}
		return null;
	}

	public IMultiblock getMultiblockFromName(String name)
	{
		return multiblockTiles.get(name).createNewInstance();
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void multiblockTick(TickEvent.WorldTickEvent event)
	{
		if(event.phase.equals(TickEvent.Phase.END))
		{
			for(IMultiblock multiblock : activeMultiblocks)
				multiblock.update();
		}
	}

	@SubscribeEvent
	public void multiblockSave(WorldEvent.Save event)
	{
		MultiblockSavedWorldData data = MultiblockSavedWorldData.forWorld(event.world);
		data.resetData();
		int counter = 0;
		for(IMultiblock multiblock : activeMultiblocks)
		{
			if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER))
			//if(multiblock.getWorld().getWorldInfo().getWorldName().equals(event.world.getWorldInfo().getWorldName()))
			{
				NBTTagCompound multiblockTag = new NBTTagCompound();

				NBTTagCompound multiblockDataTag = new NBTTagCompound();
				multiblock.writeToNBT(multiblockDataTag);
				multiblockTag.setTag("mbData", multiblockDataTag);

				multiblockTag.setString("type", multiblock.getUnlocalizedName());

				NBTTagCompound mutiblockParts = new NBTTagCompound();
				for(int i = 0; i < multiblock.getParts().length; i++)
					mutiblockParts.setLong("" + i, multiblock.getParts()[i].toLong());
				mutiblockParts.setInteger("length", multiblock.getParts().length);
				multiblockTag.setTag("mbParts", mutiblockParts);

				data.getData().setTag("" + counter, multiblockTag);
				counter++;
			}
		}
		data.getData().setInteger("size", counter);
		data.markDirty();
	}

	@SubscribeEvent
	public void multiblockLoad(WorldEvent.Load event)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER))
		{
			MultiblockSavedWorldData data = MultiblockSavedWorldData.forWorld(event.world);
			int counter = data.getData().getInteger("size");
			for(int i = 0; i < counter; i++)
			{
				NBTTagCompound multiblockTag = data.getData().getCompoundTag("" + i);
				String type = multiblockTag.getString("type");
				IMultiblock multiblock = getMultiblockFromName(type);

				multiblock.readFromNBT(multiblockTag.getCompoundTag("mbData"));

				NBTTagCompound mutiblockParts = multiblockTag.getCompoundTag("mbParts");
				int l = mutiblockParts.getInteger("length");
				BlockPos[] parts = new BlockPos[l];
				for(int k = 0; k < l; k++)
				{
					parts[k] = BlockPos.fromLong(mutiblockParts.getLong("" + k));
				}
				multiblock.setParts(parts);
				multiblock.setWorld(event.world);
				for(BlockPos partPos : parts)
				{
					TileEntity tile = event.world.getTileEntity(partPos);
					if(tile != null)
					if(tile instanceof IMultiblockPart && multiblock != null)
					{
						((IMultiblockPart)tile).setMultiblockTile(multiblock);
						((IMultiblockPart)tile).setMultiblockParts((technocore.datavalues.BlockPos[])parts);
					} else multiblock = null;
				}
				if(multiblock == null)
				{
					for(BlockPos partPos : parts)
					{
						TileEntity tile = event.world.getTileEntity(partPos);
						if(tile != null)
						if(tile instanceof IMultiblockPart)
						{
							((IMultiblockPart)tile).destroyMultiblock();
						}
					}
				}
				if(multiblock != null)
					activeMultiblocks.add(multiblock);
			}
		}
	}

	public static class MultiblockSavedWorldData extends WorldSavedData
	{
		private NBTTagCompound data = new NBTTagCompound();
		private static final String key = "tcmultiblocks";

	    public MultiblockSavedWorldData(String tagName)
	    {
	        super(tagName);
	    }

	    public static MultiblockSavedWorldData forWorld(World world)
	    {
	    	MapStorage storage = world.getPerWorldStorage();
	    	MultiblockSavedWorldData result = (MultiblockSavedWorldData)storage.loadData(MultiblockSavedWorldData.class, key);
	   		if (result == null)
	   		{
			   	result = new MultiblockSavedWorldData(key);
		   		storage.setData(key, result);
	   		}
	    	return result;
	    }

	    @Override
	    public void readFromNBT(NBTTagCompound compound)
	    {
	    	data = compound.getCompoundTag("TCMultiblocks");
	    }

	    @Override
	    public void writeToNBT(NBTTagCompound compound)
	    {
	        compound.setTag("TCMultiblocks", data);
	    }

	    public NBTTagCompound getData()
	    {
	        return data;
	    }

	    public void resetData()
	    {
	    	data = new NBTTagCompound();
	    }
	}
}
