import sim.CritterFace;
import java.awt.*;
import uwcse.graphics.GWindow;
import uwcse.graphics.ImageShape;
import uwcse.graphics.Shape;
/*
 * Created on Mar 4, 2004
 *
 */
/**
 * @author Kleb
 *
 */
public abstract class Face implements CritterFace {
	private Shape face;
	/**
	 * Initialize the variables needed for this PatternElement.
	 * @param w width in pixels of the space available
	 * @param h height in pixels of the space available
	 */

	public Face(int w, int h, String name) {
		Image image = Toolkit.getDefaultToolkit().getImage(name);
		face = new ImageShape(image,0,0);
	}

	/**
	 * @see CritterFace#addTo(uwcse.graphics.GWindow)
	 */
	public void addTo(GWindow gw) {
		face.addTo(gw);
	}
	
	/**
	 * @see CritterFace#removeFromWindow()
	 */
	public void removeFromWindow() {
		face.removeFromWindow();
	}
	
	/**
	 * @see CritterFace#moveTo(int, int)
	 */
	public void moveTo(int x, int y) {
		face.moveTo(x,y);
	}
	
	
	
	
}
