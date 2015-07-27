package technocore.client.gui.elements.tooltip;

public interface ITooltip {

	public boolean shouldDisplayTooltip(int mouseX, int mouseY);

	public String[] getTooltip();
}
