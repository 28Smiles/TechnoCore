package technocore.gui.slot;

import java.awt.Point;

import org.lwjgl.opengl.GL11;

import technocore.TechnoCore;
import technocore.client.gui.TechnoCoreGui;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiSlot {

	public static final ResourceLocation slot_textures = new ResourceLocation(TechnoCore.MODID, "textures/gui/slots.png");

	protected int x, y, type, id;
	protected IInventory inventory;
	protected Point posImg;

	public GuiSlot(int x, int y, int type, int id, IInventory inventory)
	{
		this.x = x;
		this.y = y;
		this.type = type;
		this.id = id;
		this.inventory = inventory;
		this.posImg = new Point((type % 13) * 18, (type / 13) * 18);
	}

	@SideOnly(Side.CLIENT)
	public void draw(TechnoCoreGui parent, int x, int y)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(slot_textures);
		Point topLeft = parent.getTopLeftCorner();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		parent.drawTexturedModalRect(topLeft.x + this.x, topLeft.y + this.y, posImg.x, posImg.y, 18, 18);
	}

	public Slot getSlot()
	{
		return new Slot(inventory, id, x + 1, y + 1);
	}
}
