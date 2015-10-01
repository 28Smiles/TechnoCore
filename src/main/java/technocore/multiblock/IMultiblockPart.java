package technocore.multiblock;

import java.util.List;

import technocore.datavalues.BlockMeta;
import technocore.datavalues.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IMultiblockPart {
	
	public abstract IBlockState getBlockState();
	
	public abstract BlockPos getPos();
	
	public abstract void setMultiblockParts(BlockPos[] other);
	
	public abstract BlockPos[] getMultiBlockParts();
	
	public default List<BlockPos> searchForMultiblockParts(World world, List<BlockPos> list) {
		if(!list.contains(getPos())) {
			list.add(getPos());
			for(BlockPos pos : getPos().getNeighbors()) {
				TileEntity tile = world.getTileEntity(pos);
				if(tile instanceof IMultiblockPart)
					list = ((IMultiblockPart)tile).searchForMultiblockParts(world, list);
			}
		}
		return list;
	}

	public abstract boolean isMultiblockPart();

	public abstract void destroyMultiblock();

	public abstract void setMultiblockTile(IMultiblock multiblockTile);

	public abstract IMultiblock getMultiblockTile();
}
