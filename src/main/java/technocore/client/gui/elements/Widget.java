package technocore.client.gui.elements;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2f;

import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import technocore.client.gui.TechnoCoreGui;

public class Widget implements IElement {

	private static final Point imgPosClosed = new Point(93, 0);
	private static final Dimension imgSizeClosed = new Dimension(19, 22);
	private static final Point imgPosOpen = new Point(0, 0);
	private static final Dimension imgSizeOpen = new Dimension(112, 140);
	private static final float openTime = 1.2F;
	private Vector2f computeVector;

	private final ResourceLocation image;
	private final Point position;

	private boolean open = false;
	private Vector2f computePosition = new Vector2f(0, 0);
	private Dimension computeSize = imgSizeClosed;
	private long lastTime;

	private List<IExtendedElement> content = new ArrayList<IExtendedElement>();
	private List<String> tooltip;

	public Widget(Point position, List<String> tooltip, ResourceLocation image) {
		this.position = position;
		this.image = image;
		this.tooltip = tooltip;
		computeVector = new Vector2f(imgSizeOpen.width - imgSizeClosed.width, -position.y);
		computeVector.scale(1F/openTime);
	}

	public void draw(TechnoCoreGui parent, int mouseX, int mouseY)
	{
		if(open)
		{
			if(computePosition.x < imgSizeOpen.width)
			{
				float scale = ((float)System.nanoTime() - lastTime) / 1000000000F;
				Vector2f scaledVector = new Vector2f(computeVector);
				scaledVector.scale(scale);
				if(scaledVector.length() + computePosition.length() >= new Vector2f(imgSizeOpen.width - imgSizeClosed.width, -position.y).length())
					computePosition = new Vector2f(imgSizeOpen.width - imgSizeClosed.width, -position.y);
				else
					computePosition.add(scaledVector);
			}
		}
		else
		{
			if(computePosition.length() > 0)
			{
				float scale = ((float)System.nanoTime() - lastTime) / 1000000000F;
				Vector2f scaledVector = new Vector2f(computeVector);
				scaledVector.scale(-scale * 2);
				if(computePosition.length() - scaledVector.length() < 0)
					computePosition = new Vector2f(0, 0);
				else
					computePosition.add(scaledVector);
			}
		}
		lastTime = System.nanoTime();

		parent.mc.getTextureManager().bindTexture(image);
		Point topLeft = parent.getTopLeftCorner();
		parent.drawSizedTexturedModalRect((int)topLeft.getX() + (int)position.getX(), (int)topLeft.getY() + (int)position.getY() + (int)computePosition.getY(), (int)imgPosClosed.getX() - (int)computePosition.getX(), 0, (int)imgSizeClosed.getWidth() + (int)computePosition.getX(), (int)imgSizeOpen.getHeight(), (float)imgSizeOpen.getWidth(), (float)imgSizeOpen.getHeight());

		Vector2f offset = new Vector2f(computePosition);
		offset.add(new Vector2f(position.x - imgSizeOpen.width + imgSizeClosed.width, position.y));
		offset = new Vector2f((int)offset.x, (int)offset.y);

		for(IExtendedElement e : content)
		{
			e.setOffset(offset);
			if(e.getPosition().x >= (imgSizeOpen.width - imgSizeClosed.width) - computePosition.x - e.getSize().width)
				e.draw(parent, mouseX, mouseY);
		}
		for(IExtendedElement e : content)
		{
			if(e.getPosition().x >= (imgSizeOpen.width - imgSizeClosed.width) - computePosition.x - e.getSize().width)
				e.drawForegroundLayer(parent, mouseX, mouseY);
		}
	}

	public void drawForegroundLayer(TechnoCoreGui parent, int mouseX, int mouseY)
	{
		for(IExtendedElement e : content)
			if(e.getPosition().x >= (imgSizeOpen.width - imgSizeClosed.width) - computePosition.x - e.getSize().width)
				if(e.hasTooltip() && e.isMouseOver(parent, mouseX, mouseY) && e.getTooltip() != null)
					parent.renderToolTip(e.getTooltip(), mouseX, mouseY);
	}

	public Widget addElement(IExtendedElement element)
	{
		content.add(element);
		return this;
	}

	public boolean hasTooltip()
	{
		return tooltip != null;
	}

	public List<String> getTooltip()
	{
		return tooltip;
	}

	public void onClick()
	{
		open = !open;
	}

	public void close()
	{
		open = false;
	}

	public boolean getOpen() {
		return open;
	}

	public Vector2f getComputePos() {
		return computePosition;
	}

	public boolean isMouseOver(TechnoCoreGui parent, int mouseX, int mouseY)
	{
		if((int)(position.getX() + computePosition.getX()) <= mouseX && (int)(position.getX() + computePosition.getX() + imgSizeClosed.getWidth()) >= mouseX && mouseY >= (int)(position.getY() + computePosition.getY()) && mouseY <= (int)(position.getY() + computePosition.getY() + imgSizeClosed.getHeight()))
			return true;
		return false;
	}
}
