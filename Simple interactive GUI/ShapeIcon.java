import java.awt.*;
import javax.swing.*;


/**
 * An icon that contains a moveable shape
 * @author Yanis
 *
 */
public class ShapeIcon implements Icon {
	
	public ShapeIcon(MoveableShape shape, int width, int height)
	{
		this.shape = shape;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		shape.draw(g2);

	}

	@Override
	public int getIconWidth() {
		return width;
	}

	@Override
	public int getIconHeight() {
		return height;
	}
	
	private int width;
	private int height;
	private MoveableShape shape;

}
