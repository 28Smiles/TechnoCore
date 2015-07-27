package technocore.multiblock.events;

import java.util.ArrayList;
import java.util.List;

import technocore.TechnoCore;
import technocore.datavalues.BlockPos;
import technocore.multiblock.IMultiblock;
import technocore.multiblock.MultiBlockRegistry;
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
			if(event.multiBlockTile.getMultiBlock() != null)
				if(event.multiBlockTile.getMultiBlock().hashCode() == posList.toArray(new BlockPos[posList.size()]).hashCode())
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
			IBlockState[][][] multiblock = MultiBlockRegistry.checkStructures(potentiualMultiblock);
			if(multiblock != null) {
				for(BlockPos pos : posList) {
					((IMultiblock)event.world.getTileEntity(pos)).disableMultiblock();
					((IMultiblock)event.world.getTileEntity(pos)).setMultiblock(posList.toArray(new BlockPos[posList.size()]));
					((IMultiblock)event.world.getTileEntity(pos)).activateMultiblock();
				}
				((IMultiblock)event.world.getTileEntity(posList.get(0))).setHost();
			}
		} else {
			TechnoCore.log.warn("MultiblockPartPlacedEvent on wrong side detected!");
			return;
		}
	}
	
	@SubscribeEvent
	public void multiblockPartDelete(MultiblockPartDeleteEvent event) {
		if(event.multiBlockTile.isMultiblockPart())
			for(BlockPos pos : event.multiBlockTile.getMultiBlock()) {
				TileEntity tile = event.world.getTileEntity(pos);
					if(tile != null && tile instanceof IMultiblock)
							((IMultiblock)tile).disableMultiblock();
			}
		for(BlockPos pos : event.multiBlockTile.getPos().getNeighbors()) {
			TileEntity tile = event.world.getTileEntity(pos);
			if(tile instanceof IMultiblock) {
				((IMultiblock)tile).disableMultiblock();
				tile.validate();
			}
		}
	}
}
