package technocore.mechanic.system;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface INetworkPart {

	public abstract EnumFacing[] getConnectedSides();

	public abstract World getWorld();

	public abstract BlockPos getPos();
}