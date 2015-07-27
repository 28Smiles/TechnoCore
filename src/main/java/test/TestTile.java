package test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.client.FMLClientHandler;
import technocore.block.tileentity.TechnoCoreTileEntity;
import technocore.gui.slot.GuiSlot;
import technocore.networking.packets.PacketTechno;

public class TestTile extends TechnoCoreTileEntity {

	@Override
	public void handleGuiPacket(PacketTechno packet) {
		
	}

	@Override
	public void handleDescriptionPacket(PacketTechno packet) {
		
	}

	@Override
	public Gui getGui(IInventory player) {
		return new GuiTest(this, player);
	}

	@Override
	public Container getContainer(InventoryPlayer player) {
		return new ContainerTest(this, player).addSlot(new GuiSlot(20, 20, 0, 0, player));
	}

}
