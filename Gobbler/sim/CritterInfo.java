package sim;
/**
 * This interface provides access to information about a
 * particular Critter.  The initial values for some of
 * these parameters are provided when a Critter constructor
 * creates a new CritterInfo instance using createCritterInfoInstance.
 * The other parameters are calculated and updated dynamically
 * as the model runs.
 * @see BasicMatrixModel#createCritterInfoInstance(Critter, int, int, int,int, int)
 )
 */
public interface CritterInfo {
  /**
   * Get the current age of this Critter, in ticks.
   * @return the current age of this Critter, in ticks
   */
  public int getAge();
  /**
   * Get the life span defined for this Critter, in ticks.
   * @return the life span defined for this Critter, in ticks
   */
  public int getLifeSpan();
  
  /**
   * Get the number of ticks between reproduction events.
   * @return the number of ticks between reproduction events
   */
  public int getReproductionSpan();
  
  /**
   * Get the number of calories this Critter has at this time
   * @return the number of calories this Critter has at this time.
   */
  public int getCurCalories();
  
  /**
   * Get the maximum number of calories this Critter can store
   * @return the maximum number of calories this Critter can store.
   */
  public int getMaxCalories();
  
  /**
   * Get the rate at which energy is used up, in calories per step.
   * @return the rate at which energy is used up, in calories per step.
   */
  public int getBurnRate();
  /**
   * Get the maximum strength of this Critter, in arbitrary units
   * @return the maximum strength of this Critter, in arbitrary units.
   */
  public int getStrength();
  /**
   * Get the current power of this Critter, in arbitrary units.  The
   * Critter's power is the strength scaled by (curCalories / maxCalories).
   * @return the current power of this Critter, in arbitrary units.
   */
  public int getPower();
  
}
