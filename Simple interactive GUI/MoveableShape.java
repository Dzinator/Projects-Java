import java.awt.*;

/**
 * A shape that can be moved around.
 * @author Yanis
 *
 */
public interface MoveableShape {
	/**
	 * Draws The shape
	 * @param g2 the graphics content 
	 */
	void draw(Graphics2D g2);
	
	/**
	 * Moves the shape by a given amount.
	 * @param dx	the amount to translate in the x-direction
	 * @param dy	the amount to translate in the y-direction
	 */
	void translate(int dx, int dy);
	
}
