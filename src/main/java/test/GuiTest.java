package test;

import java.awt.Point;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import technocore.TechnoCore;
import technocore.block.tileentity.TechnoCoreTileEntity;
import technocore.client.gui.TechnoCoreGui;
import technocore.client.gui.elements.ElementFluidTank;
import technocore.client.gui.elements.ScaledElement;
import technocore.client.gui.elements.ScaledElement.ScaleType;

public class GuiTest extends TechnoCoreGui {

	public GuiTest(TechnoCoreTileEntity tile, IInventory player) {
		super(tile, player);
		elements.add(new ScaledElement(50, 50).scale(10).setScaleType(ScaleType.VerticalRight).setTexture(TechnoCore.MODID, "textures/gui/elements/arrow_right.png"));
		elements.add(new ElementFluidTank(new Point(100, 20), new FluidTank(new FluidStack(TestMod.TestFluid.instance, 3000), 10000)));
	}

}
