import java.util.*;
import java.awt.Color;
import uwcse.graphics.*;
import sim.*;
/**
 * This class creates and manages a display view of a MatrixModel.
 */
public class BugWorldView implements MatrixView {
  private int cellWidth;
  private int cellHeight;
  private int rowCount;
  private int colCount;
  private GWindow surface;
  private Map liveBugs;
  /**
   * Create a new display of the given MatrixModel.
   * @param viewRows the height of the view into the cell array.
   * Specify as the number of model cells in the vertical direction
   * to display.
   * @param viewCols the width of the view into the cell array.
   * Specify as the number of model cells in the horizontal direction
   * to display.
   * @param cellWidth the width of each individual Critter cell on the screen
   * in pixels
   * @param cellHeight the height of each individual Critter cell on the screen
   * in pixels
   */
  public BugWorldView(int viewRows, int viewCols,  int cellWidth, int cellHeight) {
    liveBugs = new HashMap();
    colCount = viewCols;
    rowCount = viewRows;
    this.cellWidth = cellWidth;
    this.cellHeight = cellHeight;
    surface = new GWindow("BugWorld View",colCount*cellWidth,rowCount*cellHeight);
    surface.setExitOnClose();
    suspendRepaints();
    if (cellWidth >= 10) {
      for (int i=0; i<rowCount; i++) {
        Shape gridLine = new Line(0,i*cellHeight,colCount*cellWidth,i*cellHeight,Color.BLUE);
        gridLine.addTo(surface);
      }
      
      for (int i=0; i<colCount; i++) {
        Shape gridLine = new Line(i*cellWidth,0,i*cellWidth,rowCount*cellHeight,Color.BLUE);
        gridLine.addTo(surface);
      }
    }
    resumeRepaints();
    
  }
  /**
   * @see MatrixView#getGWindow()
   */
  public GWindow getGWindow() {
    return surface;
  }
  
  /**
   * @see MatrixView#addCritter(Critter, int[])
   */
  public void addCritter(Critter bug,int[] loc) {
    if (!liveBugs.containsKey(bug)) {
      CritterFace face = bug.getFace(cellWidth, cellHeight);
      liveBugs.put(bug,face);
      face.moveTo(loc[1]*cellWidth,loc[0]*cellHeight);
      face.addTo(surface);
    }
  }
  
  /**
   * @see MatrixView#removeCritter(Critter)
   */
  public void removeCritter(Critter bug) {
    CritterFace face = (CritterFace)liveBugs.remove(bug);
    if (face != null) {
      face.removeFromWindow();
    }
  }
  
  /**
   * @see MatrixView#moveCritter(Critter, int[])
   */
  public void moveCritter(Critter bug, int[] newLoc) {
    CritterFace face = (CritterFace)liveBugs.get(bug);
    face.moveTo(newLoc[1]*cellWidth,newLoc[0]*cellHeight);
  }
  
  /**
   * @see MatrixView#resumeRepaints()
   */
  public void resumeRepaints() {
    surface.resumeRepaints();
  }
  
  /**
   * @see MatrixView#suspendRepaints()
   */
  public void suspendRepaints() {
    surface.suspendRepaints();
  }
  
}
