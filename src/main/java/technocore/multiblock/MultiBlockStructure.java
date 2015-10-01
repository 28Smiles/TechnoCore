package technocore.multiblock;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/**
 * 0,0,0 is the Top, South, West Block (Facing North)
 * @author Leon aka. 28Smiles
 */
public class MultiBlockStructure {
	
	public IBlockState[][][][] multiblock;
	public IMultiblock multiblockTile;
	
	public MultiBlockStructure(IBlockState[][][] multiblock, IMultiblock mb) {
		this.multiblock = new IBlockState[4][][][];
		this.multiblock[0] = multiblock.clone();
		this.multiblock[1] = rotatePlainRight(multiblock).clone();
		this.multiblock[2] = rotatePlainRight(this.multiblock[1]).clone();
		this.multiblock[3] = rotatePlainRight(this.multiblock[2]).clone();
		multiblockTile = mb;
	}
	
	public int contains(IBlockState[][][] multiblock) {
		for(int i = 0; i < this.multiblock.length; i++)
			if(equals(this.multiblock[i], multiblock))
				return i;
		return -1;
	}
	
	public boolean equals(IBlockState[][][] multiblockA, IBlockState[][][] multiblockB) {
		if(multiblockA.length == multiblockB.length) {
			for(int j = 0; j < multiblockA.length; j++)
				if(multiblockA[j].length == multiblockB[j].length) {
					for(int k = 0; k < multiblockA[j].length; k++)
						if(multiblockA[j][k].length == multiblockB[j][k].length) {
							for(int l = 0; l < multiblockA[j][k].length; l++)
								if(multiblockA[j][k][l] != null) {
									if(multiblockB[j][k][l] != null) {
										if(!multiblockA[j][k][l].equals(multiblockB[j][k][l]))
											return false;
									} else
										return false;
								} else
									if(multiblockB[j][k][l] != null)
										return false;
						} else return false;
				} else return false;
		} else return false;
		return true;
	}
	
	public IBlockState[][][] getStructure(int n) {
		return multiblock[n];
	}
	
	public IBlockState[][][] rotatePlainRight(IBlockState[][][] multiblocks) {
		int zMax = 0;
		for(int i = 0; i < multiblocks.length; i++)
			for(int j = 0; j < multiblocks[i].length; j++)
				if(multiblocks[i][j].length > zMax)
					zMax = multiblocks[i][j].length;
		IBlockState[][][] bma = new IBlockState[zMax][][];
		for(int i = 0; i < zMax; i++) {
			bma[i] = new IBlockState[multiblocks[0].length][];
			for(int j = 0; j < multiblocks[0].length; j++)
				bma[i][j] = new IBlockState[multiblocks.length];
		}
		
		for(int i = 0; i < multiblocks.length; i++)
			for(int j = 0; j < multiblocks[i].length; j++) {
				int l = multiblocks[i][j].length;
				for(int k = 0; k < multiblocks[i][j].length; k++) {
					l--;
					if(multiblocks[i][j][l] != null)
						bma[k][j][i] = multiblocks[i][j][l];
					else
						bma[k][j][i] = null;
				}
			}
		return bma;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MultiBlockStructure) {
			MultiBlockStructure mbs = (MultiBlockStructure) obj;
			if(mbs.multiblock.length == this.multiblock.length) {
				for(int i = 0; i < mbs.multiblock.length; i++)
					if(mbs.multiblock[i].length == this.multiblock[i].length) {
						for(int j = 0; j < mbs.multiblock[i].length; j++)
							if(mbs.multiblock[i][j].length == this.multiblock[i][j].length) {
								for(int k = 0; k < mbs.multiblock[i][j].length; k++)
									if(mbs.multiblock[i][j][k].length == this.multiblock[i][j][k].length) {
										for(int l = 0; l < mbs.multiblock[i][j][k].length; l++)
											if(mbs.multiblock[i][j][k][l] != null) {
												if(this.multiblock[i][j][k][l] != null)
													if(!mbs.multiblock[i][j][k][l].equals(this.multiblock[i][j][k]))
													return false;
											} else
												if(this.multiblock[i][j][k][l] != null)
													return false;
									} else return false;
							} else return false;
					} else return false;
			} else return false;
			
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return multiblock.hashCode();
	}
	
	@Override
	public MultiBlockStructure clone() {
		return new MultiBlockStructure(multiblock[0].clone(), multiblockTile.createNewInstance());
	}
}
