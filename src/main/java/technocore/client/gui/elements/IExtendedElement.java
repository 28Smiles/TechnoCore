package technocore.client.gui.elements;

import java.awt.Dimension;
import java.awt.Point;

import javax.vecmath.Vector2f;

public interface IExtendedElement extends IElement {

	public abstract Point getPosition();

	public abstract Dimension getSize();

	public abstract Point getPositionOffset();

	public abstract void setOffset(Vector2f computePosition);

	public abstract Vector2f getOffset();
}
