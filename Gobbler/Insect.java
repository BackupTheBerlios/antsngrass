import sim.*;
/**
 * This class implements a basic critter that lives in BugWorld.
 */
public class Insect implements Critter {
  private CritterInfo myInfo;
  /**
   * Initialize and remember the CritterInfo for this Critter.
   */
  public Insect() {
    int life = 50;
    int repro = 20;
    int maxCal = 500;
    int burn = 5;
    int strength = 50;
    myInfo = BasicMatrixModel.createCritterInfoInstance(this,life,repro,maxCal,burn,strength);
  }
  /**
   * @see Critter#selectNextCell(MatrixModel, int[])
   */
  public int[] selectNextCell(MatrixModel model, int[] curLoc) {
    int[] nextLoc = new int[2];
    
    for (int dR=-1; dR<=1; dR++) {
      for (int dC=-1; dC<=1; dC++) {
        if (dR == 0 && dC == 0) continue;
        nextLoc[0] = curLoc[0]+dR;
        nextLoc[1] = curLoc[1]+dC;
        Critter bug = model.getCritter(nextLoc);
        if (bug != null && bug.getCritterInfo().getStrength() < myInfo.getStrength()) return nextLoc;
      }
    }
    nextLoc[0] = curLoc[0];
    nextLoc[1] = curLoc[1]; 
    return nextLoc;
  }
  /**
   * @see Critter#getFace(int, int)
   */
  public CritterFace getFace(int w,int h) {
    return new InsectFace(w,h);
  }
  
  /**
   * @see Critter#getCritterInfo()
   */
  public CritterInfo getCritterInfo() {
    return myInfo;
  }
  
  /**
   * @see Critter#reproduce()
   */
  public Critter reproduce() {
    return new Insect();
  }
  
}
