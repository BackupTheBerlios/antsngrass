import sim.*;
/**
 * Run the Gobble program, yet another variation on Life.
 */
public class Gobble {
  /**
   * This main method creates the MatrixController that creates
   * the new world, populates it, builds views of it, and then
   * runs it according to user commands.
   * @param args ignored
   */
  public static void main(String[] args) {
    MatrixController control = new BugWorld(40,40);
  }
}
