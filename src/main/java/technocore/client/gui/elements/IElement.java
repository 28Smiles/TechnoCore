package technocore.client.gui.elements;

import java.util.List;

import technocore.client.gui.TechnoCoreGui;

public interface IElement {

	/**
	 * Draw-method
	 * @param parent Parent
	 * @param mouseX Mouse-Coord-X
	 * @param mouseY Mouse-Coord-Y
	 */
	public abstract void draw(TechnoCoreGui parent, int mouseX, int mouseY);

	/**
	 * Draw-method for the foreground
	 * @param parent Parent
	 * @param mouseX Mouse-Coord-X
	 * @param mouseY Mouse-Coord-Y
	 */
	public abstract void drawForegroundLayer(TechnoCoreGui parent, int mouseX, int mouseY);

	/**
	 * Should return true if a Tooltip should be displayed
	 * @return true/false for render/don't render
	 */
	public boolean hasTooltip();

	/**
	 * Returns the Tooltip of an Element
	 * @return Tooltip
	 */
	public List<String> getTooltip();

	/**
	 * Should return true if the mouse is over the Element
	 * @param parent Parent
	 * @param mouseX Mouse-Coord-X
	 * @param mouseY Mouse-Coord-Y
	 * @return
	 */
	public boolean isMouseOver(TechnoCoreGui parent, int mouseX, int mouseY);

}
