import sim.*;
/**
 * This is a simple Critter implementation.
 */
public class Grass implements Critter {
  private CritterInfo myInfo;
  /**
   * Initialize and remember the CritterInfo for this Critter.
   */
  public Grass() {
    int life = 50;
    int repro = 10;
    int maxCal = 100;
    int burn = 1;
    int strength = 1;
    myInfo = BasicMatrixModel.createCritterInfoInstance(this,life,repro,maxCal,burn,strength);
  }
  /**
   * @see Critter#selectNextCell(MatrixModel, int[])
   */
  public int[] selectNextCell(MatrixModel model,int[] curLoc) {
    return new int[] {curLoc[0],curLoc[1]};
  }
  /**
   * @see Critter#getFace(int, int)
   */
  public CritterFace getFace(int w,int h) {
    return new GrassFace(w,h);
  }
  
  /**
   * @see Critter#reproduce()
   */
  public Critter reproduce() {
    return new Grass();
  }
  
  /**
   * @see Critter#getCritterInfo()
   */
  public CritterInfo getCritterInfo() {
    return myInfo;
  }
  
}
