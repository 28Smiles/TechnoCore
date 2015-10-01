package technocore.multiblock;

import technocore.networking.packets.PacketMultiblock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IMultiblock {

	public abstract IMultiblock createNewInstance();

	public abstract void initialize();

	public abstract void update();

	public abstract void writeToNBT(NBTTagCompound tag);

	public abstract void readFromNBT(NBTTagCompound tag);

	public abstract void setParts(BlockPos[] other);

	public abstract BlockPos[] getParts();

	public abstract World getWorld();

	public abstract void setWorld(World world);

	public abstract String getUnlocalizedName();

	public abstract void handlePacket(PacketMultiblock packet);
}
