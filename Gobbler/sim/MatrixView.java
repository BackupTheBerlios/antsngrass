package sim;
import uwcse.graphics.GWindow;
/**
 * This interface is implemented by any class that wishes
 * to provide a view of a MatrixModel.
 */
public interface MatrixView {
  /**
   * Get the GWindow associated with this view for use by a MatrixController.
   * @return the GWindow associated with this view, or null if no window.
   */
  public GWindow getGWindow();
  /**
   * Add the given Critter to the collection of Critters we are displaying.
   * If the Critter is already in the collection, the request is ignored.
   * Note that a cell location is not the same as a pixel location.  
   * One of the tasks that a MatrixView does is translate between the cell
   * location and the screen location.
   * @param bug the Critter to add
   * @param curLoc a 2-element int array containing the current cell location
   * of this Critter.  The row address is in element [0] and
   * the column address is in element [1].
   */
  public void addCritter(Critter bug,int[] curLoc);
  /**
   * Remove the given Critter from the collection of Critters we are displaying.
   * @param bug the Critter to remove
   */
  public void removeCritter(Critter bug);
  /**
   * Update the display to reflect a new position for the given Critter.
   * Note that a cell location is not the same as a pixel location.  
   * One of the tasks that a MatrixView does is translate between the cell
   * location and the screen location.
   * @param bug the Critter to move
   * @param newLoc a 2-element int array containing the new cell location
   * of this Critter.  The row address is in element [0] and
   * the column address is in element [1].
   */
  public void moveCritter(Critter bug,int[] newLoc);
  /**
   * Suspend repainting this view's window because we 
   * are going to do a bunch of updates.
   */
  public void suspendRepaints();
  /**
   * Resume repainting this view's window.
   */
  public void resumeRepaints();
}
