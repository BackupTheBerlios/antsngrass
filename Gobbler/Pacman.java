import sim.*;
/**
 * This class implements a basic critter that lives in BugWorld.
 */
public class Pacman implements Critter {
  private CritterInfo myInfo;
  /**
   * Initialize and remember the CritterInfo for this Critter.
   */
  public Pacman() {
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
    System.out.println(curLoc[0]);
    for(int place = 1; place < 8;place+=2) { 
    	nextLoc[0]=(curLoc[0] - 1) + (place / 3);  //row
    	nextLoc[1]=(curLoc[1] - 1) + (place % 3);  //column
        Critter bug = model.getCritter(nextLoc);
        if(bug!=null) {
        if (bug.getClass()==Pellet.class) {
		System.out.println(true);
        	return nextLoc;
        }}
/*        if (bug != null && bug.getCritterInfo().getStrength() < myInfo.getStrength()) return nextLoc;*/
      
    }

    return curLoc;
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
