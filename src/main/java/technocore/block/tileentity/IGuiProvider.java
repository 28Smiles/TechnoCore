package technocore.block.tileentity;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiProvider {

	@SideOnly(Side.CLIENT)
	public abstract Gui getGui(IInventory player);

	public abstract Container getContainer(InventoryPlayer player);
}
