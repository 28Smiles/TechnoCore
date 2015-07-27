package test;

import net.minecraft.inventory.IInventory;
import technocore.TechnoCore;
import technocore.block.tileentity.TechnoCoreTileEntity;
import technocore.client.gui.TechnoCoreGui;
import technocore.client.gui.elements.ScaledElement;
import technocore.client.gui.elements.ScaledElement.ScaleType;

public class GuiTest extends TechnoCoreGui {

	public GuiTest(TechnoCoreTileEntity tile, IInventory player) {
		super(tile, player);
		elements.add(new ScaledElement(50, 50).scale(10).setScaleType(ScaleType.VerticalRight).setTexture(TechnoCore.MODID, "textures/gui/elements/arrow_right.png"));
	}

}
