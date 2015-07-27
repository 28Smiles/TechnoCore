package technocore.block.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

public interface ISecureable {

	public abstract List<UUID> getAllowedUsers();
	
	public abstract void setAllowedUsers(List<UUID> users);

	public default void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("amountAllowedUsers", getAllowedUsers().size());
		int i = 0;
		for(UUID id : getAllowedUsers())
		{
			nbt.setString("user_" + i, id.toString());
			i++;
		}
	}
	
	public default void readFromNBT(NBTTagCompound nbt)
	{
		int n = nbt.getInteger("amountAllowedUsers");
		List<UUID> users = new ArrayList<UUID>();
		for(int i = 0; i < n; i++)
		{
			users.add(UUID.fromString(nbt.getString("user_" + i)));
		}
	}

	public default boolean canPlayerOpen(UUID playerID)
	{
		return getAllowedUsers().contains(playerID);
	}

	public default void addSecurePlayer(UUID playerID)
	{
		getAllowedUsers().add(playerID);
	}

	public default void removeSecurePlayer(UUID playerID)
	{
		getAllowedUsers().remove(playerID);
	}
}
