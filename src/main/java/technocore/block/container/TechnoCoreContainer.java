package technocore.block.container;

import java.util.ArrayList;
import java.util.List;

import technocore.block.tileentity.TechnoCoreTileEntity;
import technocore.gui.slot.GuiSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class TechnoCoreContainer extends Container {

	protected final TechnoCoreTileEntity technoTile;
	protected final InventoryPlayer inventoryPlayer;

	protected final List<GuiSlot> slots = new ArrayList<GuiSlot>();

	public TechnoCoreContainer(TechnoCoreTileEntity technoTile, InventoryPlayer inventory)
	{
		super();
		this.technoTile = technoTile;
		this.inventoryPlayer = inventory;
		addPlayerInventory(inventoryPlayer);
	}

	protected void addPlayerInventory(InventoryPlayer paramInventoryPlayer)
	{
	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 9; j++) {
	            addSlotToContainer(new Slot(paramInventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
	        }
	    }
	    for (int i = 0; i < 9; i++)
	        addSlotToContainer(new Slot(paramInventoryPlayer, i, 8 + i * 18, 142));
	}

	public boolean canInteractWith(EntityPlayer paramEntityPlayer)
	{
	      return this.technoTile == null ? true : this.technoTile.isUseable(paramEntityPlayer);
	}

	public void detectAndSendChanges()
	{
	    super.detectAndSendChanges();
	    if (this.technoTile == null) {
	        return;
	    }
	    for (int i = 0; i < this.crafters.size(); i++)
	        this.technoTile.sendGuiNetworkData(this, (ICrafting)this.crafters.get(i));
	}

	public List<GuiSlot> getSlots() {
		return slots;
	}

	public TechnoCoreContainer addSlot(GuiSlot slot) {
		slots.add(slot);
		addSlotToContainer(slot.getSlot());
		return this;
	}
}
