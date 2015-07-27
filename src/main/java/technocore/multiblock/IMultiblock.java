package technocore.multiblock;

import java.util.List;

import technocore.datavalues.BlockMeta;
import technocore.datavalues.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IMultiblock {
	
	public abstract IBlockState getBlockState();
	
	public abstract BlockPos getPos();
	
	public abstract void setMultiblock(BlockPos[] other);
	
	public abstract BlockPos[] getMultiBlock();
	
	public default List<BlockPos> searchForMultiblockParts(World world, List<BlockPos> list) {
		if(!list.contains(getPos())) {
			list.add(getPos());
			for(BlockPos pos : getPos().getNeighbors()) {
				TileEntity tile = world.getTileEntity(pos);
				if(tile instanceof IMultiblock)
					list = ((IMultiblock)tile).searchForMultiblockParts(world, list);
			}
		}
		return list;
	}
	
	public abstract void buildPartReference();
	
	public abstract boolean isMultiblockPart();
	
	public abstract void activateMultiblock();
	
	public abstract void disableMultiblock();
	
	public abstract void multiblockTick();

	public abstract void setHost();
}
