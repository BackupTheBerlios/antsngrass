package sim;
import uwcse.graphics.*;
/**
 * This interface defines the methods that a Critter class must implement
 * in order to be managed by the CSE 142 MatrixView project code.
 * 
 * <p>The purpose of a class that implements CritterFace is to provide
 * the Shapes that represent a particular instance of a particular type
 * of Critter.  Thus, if there is a "class Insect implements Critter", then
 * there must be a related class (for example InsectFace) that implements
 * this CritterFace interface.  The CritterFace manages a small set of 
 * Shapes (one or more) that can be used to draw a single Critter of a 
 * particular type.</p>
 * 
 * <p>Note that this class is similar in purpose to the PatternElement
 * interface in the previous project, and a class that implements this
 * interface will have methods similar to those of the classes that 
 * implemented PatternElement, although there are fewer methods to 
 * implement.</p>
 */
public interface CritterFace {
  /**
   * Add this pattern to a graphics window.  The implementing class
   * must go through all the Shape objects it controls and add them
   * to the window using the Shape method addTo(GWindow gw).
   * @param gw the graphics window to add this image to
   */
  public void addTo(GWindow gw);
  /**
   * Remove this pattern from the window it is shown in.  The 
   * implementing class must go through all the Shape objects it
   * controls and remove them from the window using the Shape
   * method removeFromWindow().
   */
  public void removeFromWindow();
  
  /**
   * Change this pattern's position.  x and y give the new position
   * in pixels of the upper left corner of this pattern's bounding box.
   * @param x new X coordinate
   * @param y new Y coordinate
   */
  public void moveTo(int x, int y);
  
}
