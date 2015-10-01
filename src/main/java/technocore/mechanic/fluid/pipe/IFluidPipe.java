package technocore.mechanic.fluid.pipe;

import scala.actors.threadpool.Arrays;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public interface IFluidPipe {

	/**
     * 
     * @param resource
     *            FluidStack attempting to fill the tank.
     * @param doFill
     *            If false, the fill will only be simulated.
     * @return Amount of fluid that was accepted by the tank.
     */
	public abstract int fill(EnumFacing from, FluidStack resource, boolean doFill);

	public abstract EnumFacing[] getDirections();

	public abstract EnumFacing[] getConnectedSides();

	public abstract IFluidTank getTank();

	public abstract World getWorld();

	public abstract BlockPos getBlockPos();

	public abstract int get_mBperTick();

	public abstract boolean disconnectPipe(EnumFacing facing);

	public abstract boolean connectPipe(EnumFacing facing);

	public abstract boolean onNeighbourConnects(EnumFacing from, IFluidPipe pipe);

	public abstract boolean onNeighbourDisconnects(EnumFacing from, IFluidPipe pipe);
}
