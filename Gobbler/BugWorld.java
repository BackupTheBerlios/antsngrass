import uwcse.graphics.*;
import sim.*;
import java.util.*;

/**
 * This class creates and manages an active surface for the Gobbler program.
 */
public class BugWorld implements MatrixController, GWindowEventHandler {
  private MatrixModel world;
  private ArrayList views;
  private GWindow window;
  private int frameTime;  // current animation frame period in milliseconds
  private boolean animating; // true while we are animating
  /**
   * Using the cell counts given, create a new window.
   * @param r number of rows of cells
   * @param c number of columns of cells
   */
  public BugWorld(int r,int c) {
    frameTime = 100;
    animating = false;
    world = new BugWorldModel(r,c);
    views = new ArrayList();
    views.add(new BugWorldView(r,c,20,20));
    window = null;
    Iterator iter = views.iterator();
    while (iter.hasNext()) {
      MatrixView view = (MatrixView) iter.next();
      // hopefully, at least one of the views has a GWindow because
      // we need one so that we can be a GWindowEventHandler
      if (window == null) {
        window = view.getGWindow();
      }
      view.suspendRepaints();
      world.addView(view);
      view.resumeRepaints();
      view.getGWindow().addEventHandler(this);
    }
  }
  /**
   * @see MatrixController#step()
   */
  public synchronized void step() {
    Iterator iter;
    MatrixView view;
    iter = views.iterator();
    while (iter.hasNext()) {
      view = (MatrixView) iter.next();
      view.suspendRepaints();
    }
    world.step();
    iter = views.iterator();
    while (iter.hasNext()) {
      view = (MatrixView) iter.next();
      view.resumeRepaints();
    }
  }
  /**
   * @see MatrixController#animate(int)
   */
  public synchronized void animate(int millis) {
    if (window != null) {
      animating = true;
      window.stopTimerEvents();
      window.startTimerEvents(millis);
    }
  }
  /**
   * @see uwcse.graphics.GWindowEventHandler#mousePressed(uwcse.graphics.GWindowEvent)
   */
  public void mousePressed(GWindowEvent arg0) {
    //  System.out.println(arg0);
  }
  /**
   * @see uwcse.graphics.GWindowEventHandler#mouseReleased(uwcse.graphics.GWindowEvent)
   */
  public void mouseReleased(GWindowEvent arg0) {
    //  System.out.println(arg0);
  }
  /**
   * @see uwcse.graphics.GWindowEventHandler#mouseDragged(uwcse.graphics.GWindowEvent)
   */
  public void mouseDragged(GWindowEvent arg0) {
  }
  /**
   * @see uwcse.graphics.GWindowEventHandler#timerExpired(uwcse.graphics.GWindowEvent)
   */
  public void timerExpired(GWindowEvent arg0) {
    step();
  }
  /**
   * @see uwcse.graphics.GWindowEventHandler#keyPressed(uwcse.graphics.GWindowEvent)
   */
  public synchronized void keyPressed(GWindowEvent arg0) {
      System.out.println(arg0);
    if (arg0.getKey() == 's') {
      if (window != null) {
        animating = false;
        window.stopTimerEvents();
      }
      step();
    } else if (arg0.getKey() == 'a') {
      if (animating) {
        frameTime = Math.max(50,frameTime/2);
      }
      animate(frameTime);
    } else if (arg0.getKey() == 'A') {
      if (animating) {
        frameTime = Math.min(5000,frameTime*2);
      }
      animate(frameTime);
    } else if (arg0.getKey() == 'q') {
      System.exit(0);
    } 
  }
  /**
   * @see uwcse.graphics.GWindowEventHandler#keyReleased(uwcse.graphics.GWindowEvent)
   */
  public void keyReleased(GWindowEvent arg0) {
    //  System.out.println(arg0);
  }
}
