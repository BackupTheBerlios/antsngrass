import sim.*;
import uwcse.graphics.*;
/*
 * Implement the display representation of a Grass Critter.
 */

public class InsectFace implements CritterFace {
  private Shape face;
  /**
   * Initialize the variables needed for this PatternElement.
   * @param w width in pixels of the space available
   * @param h height in pixels of the space available
   */
  public InsectFace(int w,int h) {
    face = new Oval(0,0,w,h,java.awt.Color.RED,true);
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
