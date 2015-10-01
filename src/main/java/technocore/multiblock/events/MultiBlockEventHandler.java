package technocore.multiblock.events;

import java.util.ArrayList;
import java.util.List;

import technocore.TechnoCore;
import technocore.datavalues.BlockPos;
import technocore.multiblock.IMultiblock;
import technocore.multiblock.IMultiblockPart;
import technocore.multiblock.MultiBlockRegistry;
import technocore.multiblock.MultiBlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class MultiBlockEventHandler {

	public static MultiBlockEventHandler instance = new MultiBlockEventHandler();

	@SubscribeEvent
	public void multiblockPartPlaced(MultiblockPartPlacedEvent event) {
		if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER)) {
			List<BlockPos> posList = event.multiBlockTile.searchForMultiblockParts(event.world, new ArrayList<BlockPos>());
			if(event.multiBlockTile.getMultiBlockParts() != null)
				if(event.multiBlockTile.getMultiBlockParts().hashCode() == posList.toArray(new BlockPos[posList.size()]).hashCode())
					return;
			BlockPos minPos;
			BlockPos maxPos;
			minPos = posList.get(0).copy();
			maxPos = posList.get(0).copy();
			
			int maxX = 0, maxY = 0, maxZ = 0;
			int minX = 0, minY = 0, minZ = 0;
			
			for(BlockPos pos : posList) {
				if(pos.getX() > maxX)
					maxX = pos.getX();
				if(pos.getY() > maxY)
					maxY = pos.getY();
				if(pos.getZ() > maxZ)
					maxZ = pos.getZ();
				if(pos.getX() < minX)
					minX = pos.getX();
				if(pos.getY() < minY)
					minY = pos.getY();
				if(pos.getZ() < minZ)
					minZ = pos.getZ();
			}
			IBlockState[][][] potentiualMultiblock = new IBlockState[maxPos.getX() - minPos.getX() + 1][maxPos.getY() - minPos.getY() + 1][maxPos.getZ() - minPos.getZ() + 1];
			for(IBlockState[][] subArray : potentiualMultiblock) {
				subArray = new IBlockState[maxPos.getY() - minPos.getY() + 1][maxPos.getZ() - minPos.getZ() + 1];
				for(IBlockState[] subsubArray : subArray)
					subsubArray = new IBlockState[maxPos.getZ() - minPos.getZ() + 1];
			}
			
			for(BlockPos pos : posList)
				potentiualMultiblock[pos.getX() - minPos.getX()][pos.getY() - minPos.getY()][pos.getZ() - minPos.getZ()] = event.world.getBlockState(pos);
			MultiBlockStructure multiblock = MultiBlockRegistry.INSTANCE.checkStructures(potentiualMultiblock);
			IMultiblock multiblockTile = multiblock.multiblockTile.createNewInstance();
			multiblockTile.setParts(posList.toArray(new BlockPos[posList.size()]));
			multiblockTile.setWorld(event.world);
			multiblockTile.initialize();
			if(multiblock != null) {
				for(BlockPos pos : posList) {
					((IMultiblockPart)event.world.getTileEntity(pos)).setMultiblockParts(posList.toArray(new BlockPos[posList.size()]));
					((IMultiblockPart)event.world.getTileEntity(pos)).setMultiblockTile(multiblockTile);
				}
			}
		} else {
			TechnoCore.log.warn("MultiblockPartPlacedEvent on wrong side detected!");
			return;
		}
	}
	
	@SubscribeEvent
	public void multiblockPartDelete(MultiblockPartDeleteEvent event) {
		if(event.multiBlockTile.isMultiblockPart())
			for(BlockPos pos : event.multiBlockTile.getMultiBlockParts()) {
				TileEntity tile = event.world.getTileEntity(pos);
					if(tile != null && tile instanceof IMultiblockPart)
							((IMultiblockPart)tile).destroyMultiblock();
			}
		for(BlockPos pos : event.multiBlockTile.getPos().getNeighbors()) {
			TileEntity tile = event.world.getTileEntity(pos);
			if(tile instanceof IMultiblockPart) {
				((IMultiblockPart)tile).destroyMultiblock();
				tile.validate();
			}
		}
	}
}
