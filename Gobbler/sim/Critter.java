package sim;
/**
 * This interface describes the methods that a MatrixModel creature must
 * implement.
 */
public interface Critter {
  /**
   * This method implements the logic of this Critter that decides where
   * it wants to move to next.  The MatrixModel is provided so that this
   * Critter can ask the model about its neighbors, if it wants to use
   * that information in making the decision.
   * @param model the MatrixModel that this Critter inhabits
   * @param curLoc a 2-element int array containing the current cell location
   * of this Critter.  The row address is in element [0] and
   * the column address is in element [1].
   * @return a 2-element int array to hold the cell location
   * this Critter would like to move to.  The row address is in element [0]
   * and the column address is in element [1].
   * @see MatrixModel#getCritter(int[])
   */
  public int[] selectNextCell(MatrixModel model, int[] curLoc);
  /**
   * This method returns a CritterFace (a displayable collection of Shapes)
   * that can be used to represent this Critter in a MatrixView.  The width
   * and height of the available space are provided so that the Shapes can
   * be scaled to fit the space.  It's also possible to create a different face
   * depending on the amount of space available.  For example, a very small 
   * space might just get a dot, but a larger space could get a small drawing.
   * @param w the width in pixels of the space the Shapes will 
   * occupy on screen
   * @param h the height in pixels of the space the Shapes will 
   * occupy on screen
   * @return the CritterFace to display for this Critter
   */
  public CritterFace getFace(int w,int h);
  /**
   * This method returns the CritterInfo object that is storing information
   * about this Critter.
   * @return an object that implements the CritterInfo interface and holds
   * current information about the Critter
   */
  public CritterInfo getCritterInfo();
  /**
   * This method creates another object of the same type as this Critter.
   * @return a new Critter object of the same type as this Critter
   */
  public Critter reproduce();
}
