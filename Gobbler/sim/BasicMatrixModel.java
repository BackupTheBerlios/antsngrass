package sim;
import java.util.*;
/**
 * This class creates and manages a simple 2D population model.
 */
public abstract class BasicMatrixModel implements MatrixModel {
  private int[][] spots = new int[9][2];  // allocate here so that findEmptyCell doesn't have to
  private static Map cim = new HashMap();  // CritterInfo map so we can tell if we've handed one out
  private int rowCount;
  private int colCount;
  private Critter[][][] cell;
  private ArrayList[][] pen;
  private ArrayList views;
  private int bFrom;  // "from" buffer
  private int bTo;   // "to" buffer
  /**
   * Using the cell counts given, create a new model.
   * @param r number of rows of cells
   * @param c number of columns of cells
   */
  public BasicMatrixModel(int r, int c) {
    rowCount = r;
    colCount = c;
    bFrom = 0;
    bTo = 1;
    
    Critter[][] bugs = initializePopulation(rowCount,colCount);
    
    cell = new Critter[r][c][2];
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        cell[i][j][bFrom] = bugs[i][j];
      }
    }
    pen = new ArrayList[r][c];
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        pen[i][j] = new ArrayList();
      }
    }
    views = new ArrayList();
  }
  /**
   * Add a bunch of Critters to the population list.  This method is called
   * by the BasicMatrixModel constructor during initialization.
   * It can use any technique it likes to decide what types of Critters
   * to add, where to put them, etc.  The returned array must have 
   * the dimensions given, and contain Critter objects and nulls.
   * @param rows number of rows of cells in the model
   * @param columns number of columns of cells in the model
   * @return a doubly dimensioned array of Critters
   */
  protected abstract Critter[][] initializePopulation(int rows, int columns);
  
  /**
   * @see MatrixModel#getDimensions()
   */
  public int[] getDimensions() {
    return new int[] { rowCount, colCount };
  }
  /**
   * @see MatrixModel#addView(MatrixView)
   */
  public void addView(MatrixView view) {
    views.add(view);
    int[] loc = new int[2];
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        Critter bug = cell[i][j][bFrom];
        if (bug != null) {
          loc[0] = i;
          loc[1] = j;
          view.addCritter(bug,loc);
        }
      }
    }
  }
  
  /**
   * @see MatrixModel#step()
   */
  public void step() {
    
    // clear the holding pens
    
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        pen[i][j].clear();
      }
    }
    
    // Everybody is a tick older
    
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        if (cell[i][j][bFrom] != null) {
          
          // age the Critter
          
          Critter bug = cell[i][j][bFrom];
          CritterInfoContainer buggy = (CritterInfoContainer)bug.getCritterInfo();
          buggy.incrementAge();
          buggy.incrementCurCalories(-buggy.getBurnRate());
          if (buggy.getAge() > buggy.getLifeSpan()  ||
              buggy.getCurCalories() == 0) {
            buggy.setCritterIsDead();
          }
        }
      }
    }
    
    // Clean up the bodies
    
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        if (cell[i][j][bFrom] != null) {
          
          Critter bug = cell[i][j][bFrom];
          CritterInfoContainer buggy = (CritterInfoContainer)bug.getCritterInfo();
          if (buggy.isCritterDead()) {
            
            cell[i][j][bFrom] = null;
            
            // spread the calories around to the neighbors
            int portion = bug.getCritterInfo().getCurCalories()/8;
            for (int dR=-1; dR <= 1; dR++) {
              for (int dC = -1; dC <= 1; dC++) {
                Critter otherBug = cell[i][j][bFrom];
                if (otherBug != null) {
                  ((CritterInfoContainer)otherBug.getCritterInfo()).incrementCurCalories(portion);
                }
              }
            }
            
            // take the dead guy out of this world
            Iterator iter = views.iterator();
            while (iter.hasNext()) {
              ((MatrixView) iter.next()).removeCritter(bug);
            }
            discardCritterInfoInstance(bug);
          }
        }
      }
    }
    
    // reproduce the living
    
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        if (cell[i][j][bFrom] != null) {
          Critter bug = cell[i][j][bFrom];
          if (bug.getCritterInfo().getAge() % bug.getCritterInfo().getReproductionSpan() == 0) {
            // it's time for another Critter
            int[] newLoc = findEmptyCell(i,j,bFrom);
            if (newLoc != null) {
              Critter littleBug = bug.reproduce();
              ((CritterInfoContainer)littleBug.getCritterInfo()).incrementAge();
              pen[newLoc[0]][newLoc[1]].add(littleBug);
              Iterator iter = views.iterator();
              while (iter.hasNext()) {
                ((MatrixView) iter.next()).addCritter(littleBug,newLoc);
              }
            }
          }
        }
      }
    }
    
    // decide where each Critter wants to go
    
    int[] curLoc = new int[2];
    int[] nextLoc;
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        if (cell[i][j][bFrom] != null) {
          Critter bug = cell[i][j][bFrom];
          curLoc[0] = i;
          curLoc[1] = j;
          nextLoc = bug.selectNextCell(this,curLoc);
          if (nextLoc == null) {
            nextLoc = new int[] {i,j};
          }
          nextLoc[0] = rowWrap(nextLoc[0]);
          nextLoc[1] = colWrap(nextLoc[1]);
          pen[nextLoc[0]][nextLoc[1]].add(bug);
        }
      }
    }
    
    // resolve multiple Critters per cell
    
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        cell[i][j][bTo] = selectCellSurvivor(pen[i][j]);
      }
    }
    
    // swap buffers
    
    bFrom = bTo;
    bTo = (bTo+1) % 2;
    
    // tell all the views to update the positions of the CritterFaces
    
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        if (cell[i][j][bFrom] != null) {
          Critter bug = cell[i][j][bFrom];
          curLoc[0] = i;
          curLoc[1] = j;
          Iterator iter = views.iterator();
          while (iter.hasNext()) {
            ((MatrixView)iter.next()).moveCritter(bug,curLoc);
          }
        }
      }
    }
  }
  /**
   * Select the fittest survivor from the list of Critters that would
   * like to inhabit this cell.
   * @param bunch ArrayList of Critters
   * @return the Critter judged most likely to survive from this group
   */
  private Critter selectCellSurvivor(ArrayList bunch) {
    if (bunch == null || bunch.isEmpty()) {
      return null;
    }
    
    // Select a survivor
    
    Critter survivor = null;
    for (int i = 0; i < bunch.size(); i++) {
      Critter bug = (Critter)bunch.get(i);
      CritterInfoContainer buggy = (CritterInfoContainer)bug.getCritterInfo();
      if ((survivor == null) || 
          (buggy.getPower() > survivor.getCritterInfo().getPower())) {
        survivor = bug;
      }
    }
    
    // Survivor gets everyone else's calories
    
    CritterInfoContainer survivorBuggy = (CritterInfoContainer)survivor.getCritterInfo();
    for (int i = 0; i < bunch.size(); i++) {
      Critter bug = (Critter)bunch.get(i);
      CritterInfoContainer buggy = (CritterInfoContainer)bug.getCritterInfo();
      if (bug != survivor) {
        survivorBuggy.incrementCurCalories(buggy.getCurCalories());
      }
    }
    
    // erase all traces of the dead
    
    for (int i = 0; i < bunch.size(); i++) {
      Critter bug = (Critter) bunch.get(i);
      CritterInfoContainer buggy =
        (CritterInfoContainer) bug.getCritterInfo();
      if (bug != survivor) {
        Iterator iter = views.iterator();
        while (iter.hasNext()) {
          ((MatrixView) iter.next()).removeCritter(bug);
        }
        discardCritterInfoInstance(bug);
      }
    }
    return survivor;
  }
  /**
   * @see MatrixModel#getCritter(int[])
   */
  public Critter getCritter(int[] loc) {
    loc[0] = rowWrap(loc[0]);
    loc[1] = colWrap(loc[1]);
    return cell[loc[0]][loc[1]][bFrom];
  }
  /**
   * Find a nearby empty cell in one of the cell buffers.  Returns null
   * if no empty cells found.  Picks randomly from the empty candidates.
   * @param row
   * @param col
   * @param bSelect
   * @return an empty location (row,col) address or null
   */
  private int[] findEmptyCell(int row, int col, int bSelect) {
    int spotCount = 0;
    for (int dR = -1; dR<=1; dR++) {
      for (int dC = -1; dC <= 1; dC++) {
        int r = rowWrap(row+dR);
        int c = colWrap(col+dC);
        if (cell[r][c][bSelect] == null) {
          spots[spotCount][0] = r;
          spots[spotCount][1] = c;
          spotCount++; 
        }
      }
    }
    if (spotCount==0) {
      return null;
    } else {
      int idx = (int)(Math.random()*spotCount);
      return spots[idx];
    } 
  }
  /**
   * Wrap row number.
   *
   */
  private int rowWrap(int row) {
    if (row >= rowCount) {
      return 0;
    } else if (row < 0) {
      return rowCount-1;
    } else {
      return row;
    }
  }
  /**
   * Wrap column number.
   *
   */
  private int colWrap(int col) {
    if (col >= colCount) {
      return 0;
    } else if (col < 0) {
      return colCount-1;
    } else {
      return col;
    }
  }
  /**
   * Get a new instance of an object that implements the CritterInfo
   * interface.  This object is used to store a variety of interesting
   * factoids about a Critter.  In addition to the parameters supplied
   * here, the CritterInfo object maintains dynamic values such as age
   * and current calories.
   * <p>This factory method is used instead of a constructor so that a Critter
   * can create only one CritterInfo object to store information about itself.</p>
   * @param me the Critter for which this CritterInfo is being created
   * @param life the life span defined for this Critter, in ticks
   * @param repro time between reproduction events in ticks
   * @param maxCal the maximum number of calories this Critter can store.
   * @param burn the number of calories burned each tick of the clock.  minimum is 1.
   * @param strength the maximum strength of this Critter in arbitrary units
   */
  public static CritterInfo createCritterInfoInstance(
                                                      Critter me,
                                                      int life,int repro,
                                                      int maxCal,int burn,
                                                      int strength) {
    CritterInfo blob = (CritterInfo)cim.get(me);
    if (blob == null) {
      blob = new CritterInfoContainer(life,repro,maxCal,burn,strength);
      cim.put(me,blob);
    }
    return blob;
  }
  /**
   * This is the matching method to createCritterInfoInstance.  It removes 
   * a CritterInfoContainer from the Map when we are done with it.
   * @param c the Critter for which we no longer need the info
   */
  private static void discardCritterInfoInstance(Critter c) {
    cim.remove(c);
  }
  /**
   * 
   */
  //-----------------------------------------------------------------------
  
  /**
   * Define a class that can hold info about each Critter in a secure
   * fashion.  The purpose of this class is to provide read-only access
   * to Critter information for the Critters, while also providing
   * read/write access to this MatrixModel.
   * @see CritterInfo
   */
  private static class CritterInfoContainer implements CritterInfo {
    
    private int age;
    private int lifeSpan;
    private int reproductionSpan;
    private int curCalories;
    private int maxCalories;
    private int burnRate;
    private int strength;
    private boolean dead;
    
    /**
     * Initialize a new CritterInfoContainer with the information given.
     * @param life life span in ticks
     * @param repro time between reproduction events in ticks
     * @param maxCal maximum calories this Critter can hold
     * @param burn the number of calories burned each tick of the clock.  minimum is 1.
     * @param strength the strength of this Critter, in arbitrary units.  minimum is 1.
     */
    private CritterInfoContainer(int life, int repro, int maxCal, int burn, int strength) {
      age = 0;
      lifeSpan = life;
      reproductionSpan = repro;
      maxCalories = maxCal;
      curCalories = maxCalories;
      burnRate = burn;
      this.strength = strength;
      dead = false;
    }
    
    /**
     * @see CritterInfo#getAge()
     */
    public int getAge() {
      return age;
    }
    
    /**
     * @see CritterInfo#getLifeSpan()
     */
    public int getLifeSpan() {
      return lifeSpan;
    }
    
    /**
     * @see CritterInfo#getReproductionSpan()
     */
    public int getReproductionSpan() {
      return reproductionSpan;
    }
    
    /**
     * @see CritterInfo#getBurnRate()
     */
    public int getBurnRate() {
      return burnRate;
    }
    
    /**
     * @see CritterInfo#getCurCalories()
     */
    public int getCurCalories() {
      return curCalories;
    }
    
    /**
     * @see CritterInfo#getMaxCalories()
     */
    public int getMaxCalories() {
      return maxCalories;
    }
    
    /**
     * @see CritterInfo#getStrength()
     */
    public int getStrength() {
      return strength;
    }
    
    /**
     * @see CritterInfo#getPower()
     */
    public int getPower() {
      if (age==0) {
        return 0;
      } else {
        return Math.max(1,(int)(strength*((double)curCalories/(double)maxCalories)));
      }
    }
    
    /**
     * Increment the age of this Critter.
     */
    private void incrementAge() {
      age++;
    }
    /**
     * Set the current calories value of this Critter.  If the
     * given value is outside the 0..maxCalories then curCalories
     * is set to the associated limit.
     * @param cal the new calorie value to use
     */
    private void setCurCalories(int cal) {
      curCalories = cal;
      if (curCalories < 0) {
        curCalories = 0;
      } else if (curCalories > maxCalories) {
        curCalories = maxCalories;
      }
    }
    /**
     * Increment the current calories value of this Critter by the
     * delta given.  Delta can be pos or neg.  If the resulting
     * value is outside the 0..maxCalories then curCalories
     * is set to the associated limit.
     * @param delta the value to increment curCalories by
     */
    private void incrementCurCalories(int delta) {
      setCurCalories(curCalories+delta);
    }
    /**
     * Set the dead flag to true.
     */
    private void setCritterIsDead() {
      dead = true;
    }
    /**
     * @return dead flag, true or false
     */
    private boolean isCritterDead() {
      return dead;
    }
    
  }
  
}
