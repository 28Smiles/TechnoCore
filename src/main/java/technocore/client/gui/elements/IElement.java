package technocore.client.gui.elements;

import java.util.List;

import technocore.client.gui.TechnoCoreGui;

public interface IElement {

	public abstract void draw(TechnoCoreGui parent, int mouseX, int mouseY);

	public abstract void drawForegroundLayer(TechnoCoreGui parent, int mouseX, int mouseY);

	public boolean hasTooltip();

	public List<String> getTooltip();

	public boolean isMouseOver(TechnoCoreGui parent, int mouseX, int mouseY);

}
