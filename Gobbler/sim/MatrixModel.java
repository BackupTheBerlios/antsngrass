package sim;
/**
 * This interface is implemented by any class that is a MatrixModel.
 */
public interface MatrixModel {
  /**
   * This method returns an int array containing the dimensions of
   * the Matrix being modelled.  There are as many elements in the 
   * array as there are dimensions in the model, and the value of 
   * each element is the number of cells along that dimension.  For 
   * a 2-dimensional model, this method always returns a 2-element 
   * array.  Element [0] contains the number of rows, and
   * element [1] contains the number of columns.
   * @return an array containing the dimensions of the model
   */
  public int[] getDimensions();
  /**
   * Add a MatrixView to the current list of views that are looking
   * at this model and want to hear about changes.
   * @param view the MatrixView to add to the list for this model
   */
  public void addView(MatrixView view);
  /**
   * Step the model one simulation step.
   */
  public void step();
  /**
   * Get the Critter at the given (row,col) address in the cell array
   * @param loc a 2-element int array containing the address of the desired cell.
   * The row address is in element [0] and the column address is in element [1].
   * @return the Critter at the given address or null.
   */
  public Critter getCritter(int[] loc);
  
}
