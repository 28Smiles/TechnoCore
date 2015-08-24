package technocore.client.gui.elements;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;
import technocore.TechnoCore;
import technocore.client.gui.TechnoCoreGui;

public class ElementFluidTank implements IElement {

	public static final ResourceLocation tankImgRL = new ResourceLocation(TechnoCore.MODID, "textures/gui/elements/tank.png");
	protected final Point position;
	protected final IFluidTank fluidTank;

	/**
	 * Creates an Element 
	 * @param position
	 * @param tank
	 */
	public ElementFluidTank(Point position, IFluidTank tank) {
		this.fluidTank = tank;
		this.position = position;
	}

	@Override
	public void draw(TechnoCoreGui parent, int mouseX, int mouseY) {
		Minecraft.getMinecraft().renderEngine.bindTexture(tankImgRL);
		Point topLeft = parent.getTopLeftCorner();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		parent.drawSizedTexturedModalRect((int)(position.getX() + topLeft.getX()), (int)(position.getY() + topLeft.getY()), 0, 0, 18, 60, 36F, 60F);
		
		ResourceLocation fluidTexture = fluidTank.getFluid().getFluid().getStill(fluidTank.getFluid());
		fluidTexture = new ResourceLocation(fluidTexture.getResourceDomain(), "textures/" + fluidTexture.getResourcePath() + ".png");
		Minecraft.getMinecraft().renderEngine.bindTexture(fluidTexture);
		parent.setGLColorFromInt(fluidTank.getFluid().getFluid().getColor());
		parent.drawSizedTexturedModalRect((int)position.getX() + 1 + (int)topLeft.getX(), (int)position.getY() + (int)topLeft.getY() + 59 - (int)((58F / 10F) * (((float)fluidTank.getFluidAmount()) / 1000F)), 0, 0, 16, (int)((58F / 10F) * (((float)fluidTank.getFluidAmount()) / 1000F)), 16F, 16F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(tankImgRL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		parent.drawSizedTexturedModalRect((int)(position.getX() + topLeft.getX()), (int)(position.getY() + topLeft.getY()), 18, 0, 18, 60, 36F, 60F);
	}

	@Override
	public void drawForegroundLayer(TechnoCoreGui parent, int mouseX, int mouseY) {
		
	}

	@Override
	public boolean hasTooltip() {
		return true;
	}

	@Override
	public List<String> getTooltip() {
		ArrayList<String> tooltip = new ArrayList<String>();
		tooltip.add(ChatFormatting.GRAY + fluidTank.getInfo().fluid.getFluid().getLocalizedName(fluidTank.getInfo().fluid));
		tooltip.add("amount: " + fluidTank.getFluidAmount() + "mB");
		return tooltip;
	}

	@Override
	public boolean isMouseOver(TechnoCoreGui parent, int mouseX, int mouseY) {
		if(mouseX >= (int)this.position.getX() && mouseX <= (int)(this.position.getX() + 18) &&
				mouseY >= (int)this.position.getY() && mouseY <= (int)(this.position.getY() + 60))
			return true;
		return false;
	}
}
