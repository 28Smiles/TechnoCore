package technocore.multiblock;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;

public class MultiBlockRegistry {
	
	private static List<MultiBlockStructure> multiblocks = new ArrayList<MultiBlockStructure>();
	
	public void registerNewMultiblockStructure(IBlockState[][][] structure) {
		multiblocks.add(new MultiBlockStructure(structure));
	}
	
	public static IBlockState[][][] checkStructures(IBlockState[][][] mbsructure) {
		for(MultiBlockStructure mbs : multiblocks) {
			int i = mbs.contains(mbsructure);
			if(i != -1)
				return mbs.getStructure(i);
		}
		return null;
	}
}
