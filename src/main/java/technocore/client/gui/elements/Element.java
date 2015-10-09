package technocore.client.gui.elements;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import technocore.client.gui.TechnoCoreGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Element implements IExtendedElement {

	protected ResourceLocation img;
	protected Point pos;
	protected Point posImg;
	protected Dimension size;
	protected boolean visible = true;
	protected Vector2f offset = new Vector2f();

	public Element(int x, int y, int sx, int sy, int imgx, int imgy)
	{
		pos = new Point(x, y);
		posImg = new Point(imgx, imgy);
		size = new Dimension(sx, sy);
	}

	@Override
	public void draw(TechnoCoreGui parent, int x, int y)
	{
		if(visible)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(img);
			Point topLeft = parent.getTopLeftCorner();
			parent.drawTexturedModalRect(topLeft.x + pos.x + (int)offset.x, topLeft.y + pos.y + (int)offset.y, posImg.x, posImg.y, size.width, size.height);
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

	@Override
	public void drawForegroundLayer(TechnoCoreGui parent, int mouseX, int mouseY) {}

	@Override
	public boolean hasTooltip() {
		return false;
	}

	@Override
	public List<String> getTooltip() {
		return null;
	}

	@Override
	public boolean isMouseOver(TechnoCoreGui parent, int mouseX, int mouseY) {
		if(mouseX >= (int)this.pos.getX() + (int)offset.x && mouseX <= (int)(this.pos.getX() + this.size.getWidth() + offset.x) &&
				mouseY >= (int)this.pos.getY() + (int)offset.y && mouseY <= (int)(this.pos.getY() + this.size.getHeight() + offset.y))
			return true;
		return false;
	}

	@Override
	public Point getPosition() {
		return pos;
	}

	@Override
	public Point getPositionOffset() {
	return new Point((int)offset.x, (int)offset.y);
	}

	@Override
	public void setOffset(Vector2f offset) {
		this.offset = offset;
	}

	@Override
	public Vector2f getOffset() {
		return offset;
	}

	@Override
	public Dimension getSize() {
		return size;
	}
}
