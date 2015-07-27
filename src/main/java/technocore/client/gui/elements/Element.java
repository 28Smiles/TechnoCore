package technocore.client.gui.elements;

import java.awt.Dimension;
import java.awt.Point;

import org.lwjgl.opengl.GL11;

import technocore.client.gui.TechnoCoreGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Element {

	protected ResourceLocation img;
	protected Point pos;
	protected Point posImg;
	protected Dimension size;
	protected boolean visible = true;

	public Element(int x, int y, int sx, int sy, int imgx, int imgy)
	{
		pos = new Point(x, y);
		posImg = new Point(imgx, imgy);
		size = new Dimension(sx, sy);
	}

	public void draw(TechnoCoreGui parent, int x, int y)
	{
		if(visible)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(img);
			Point topLeft = parent.getTopLeftCorner();
			parent.drawTexturedModalRect(topLeft.x + pos.x, topLeft.y + pos.y, posImg.x, posImg.y, size.width, size.height);
		}
	}

	public Element setTexture(String modid, String texture) {
		img = new ResourceLocation(modid, texture);
		return this;
	}

	public Element setTexture(ResourceLocation texture) {
		img = texture;
		return this;
	}

	public Element setSize(Dimension size) {
		this.size = size;
		return this;
	}

	public Element setSize(int sizeX, int sizeY) {
		this.size = new Dimension(sizeX, sizeY);
		return this;
	}

	public Element setVisible(boolean v) {
		this.visible = v;
		return this;
	}
}
