package technocore.mechanic.fluid.pipe;

import scala.actors.threadpool.Arrays;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public interface IFluidPipe {

	public abstract int fill(EnumFacing from, FluidStack resource, boolean doFill);

	public abstract IFluidTank getTank();

	public abstract int get_mBperTick();

}
