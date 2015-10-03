package technocore.client.gui;

import technocore.block.tileentity.IGuiProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final GuiHandler instance = new GuiHandler();

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if(tile instanceof IGuiProvider)
			return ((IGuiProvider)tile).getGui(player.inventory);
		return null;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if(tile instanceof IGuiProvider)
			return ((IGuiProvider)tile).getContainer(player.inventory);
		return null;
	}

}
