import sim.*;
/**
 * This class creates and manages a simple 2D population model.
 */
public class BugWorldModel extends BasicMatrixModel {
  /**
   * Using the cell counts given, create a new model.
   * @param r number of rows of cells
   * @param c number of columns of cells
   */
  public BugWorldModel(int r, int c) {
    super(r,c);
  }
  /**
   * @see BasicMatrixModel#initializePopulation(int, int)
   */
  protected Critter[][] initializePopulation(int rows, int columns) {
    Critter[][] bugs = new Critter[rows][columns];
    for (int i = 0; i < (rows*columns)/4; i++) {
      int r = (int) (Math.random() * rows);
      int c = (int) (Math.random() * columns);
      bugs[r][c] = new Pellet();
    }
    for (int i = 0; i < 5; i++) {
      int r = (int) (Math.random() * rows);
      int c = (int) (Math.random() * columns);
      bugs[r][c] = new Pacman();
    }
    return bugs;
  }
}
