package sim;
/**
 * This interface is implemented by any class that coordinates
 * a MatrixModel and one or more MatrixViewers.
 */
public interface MatrixController {
  /**
   * Step the model one simulation step.
   * This method is responsible in part for managing performance,
   * so it should tell the views to suspend and resume repaints
   * before and after the model is stepped.
   */
  public void step();
  /**
   * Step the model continuously with a pause in between 
   * each step.  Stepping will continue until some other event
   * brings it to a stop.
   * @param millis the number of milliseconds between each clock tick
   */
  public void animate(int millis);
}
