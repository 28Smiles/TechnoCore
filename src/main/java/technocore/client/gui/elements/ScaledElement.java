package technocore.client.gui.elements;

import java.awt.Point;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

import technocore.client.gui.TechnoCoreGui;

public class ScaledElement extends Element {

	protected int scale = 0;
	protected ScaleType type = ScaleType.HorizontalUp;

	public ScaledElement(int x, int y) {
		super(x, y, 24, 16, 0, 0);
	}

	@Override
	public void draw(TechnoCoreGui parent, int x, int y) {
		if(visible)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(img);
			Point topLeft = parent.getTopLeftCorner();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			parent.drawSizedTexturedModalRect(topLeft.x + pos.x, topLeft.y + pos.y, 0, 0, 24, 16, 48F, 16F);
			if(type.equals(ScaleType.HorizontalUp))
				parent.drawSizedTexturedModalRect(topLeft.x + pos.x, (topLeft.y + pos.y) - scale, posImg.x + 24, posImg.y - scale, size.width, scale, 48F, 16F);
			if(type.equals(ScaleType.VerticalRight))
				parent.drawSizedTexturedModalRect(topLeft.x + pos.x, topLeft.y + pos.y, posImg.x + 24, posImg.y, scale, size.height, 48F, 16F);
		}
	}

	public ScaledElement setScaleType(ScaleType type) {
		this.type = type;
		return this;
	}

	public ScaledElement scale(int s) {
		this.scale = s;
		return this;
	}

	public enum ScaleType {
		HorizontalUp, VerticalRight
	}
}
