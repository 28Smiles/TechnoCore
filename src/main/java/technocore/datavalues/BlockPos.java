package technocore.datavalues;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class BlockPos extends net.minecraft.util.BlockPos {

	public BlockPos(double x, double y, double z)
	{
		super(x, y, z);
	}

	public BlockPos(Entity entity)
	{
		super(entity);
	}

	public BlockPos(int x, int y, int z)
	{
		super(x, y, z);
	}

	public BlockPos(Vec3 vector)
	{
		super(vector);
	}

	public BlockPos(Vec3i vector)
	{
		super(vector);
	}

	public BlockPos goToSide(EnumFacing facing)
	{
		return (BlockPos) this.add(facing.getDirectionVec());
	}

	public BlockPos[] getNeighbors()
	{
		return new BlockPos[] {
				new BlockPos(this.getX() + 1, this.getY(), this.getZ()),
				new BlockPos(this.getX() - 1, this.getY(), this.getZ()),
				new BlockPos(this.getX(), this.getY() + 1, this.getZ()),
				new BlockPos(this.getX(), this.getY() - 1, this.getZ()),
				new BlockPos(this.getX(), this.getY(), this.getZ() + 1),
				new BlockPos(this.getX(), this.getY(), this.getZ() - 1)
		};
	}

	public BlockPos copy()
	{
		return new BlockPos(getX(), getY(), getZ());
	}
	
	@Override
	public int hashCode()
	{
		return getX() * getY() * getZ();
	}
}
