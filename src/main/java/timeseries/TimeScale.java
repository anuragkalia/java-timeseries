package timeseries;

/**
 * Wraps a {@link TimeUnit} together with an integer unit length, allowing one to specify a broader
 * range of time periods than using a TimeUnit alone.
 * This class is immutable and thread-safe.
 * @author Jacob Rachiele
 *
 */
public final class TimeScale {
  
  private final TimeUnit timeUnit;
  private final int unitLength;
  
  /**
   * Construct a new TimeScale with the given unit of time and unit length.
   * @param timeUnit the unit of time underlying this time scale
   * @param unitLength the length of this unit of time relative to the given time scale.
   */
  public TimeScale(final TimeUnit timeUnit, final int unitLength) {
    this.timeUnit = timeUnit;
    this.unitLength = unitLength;
  }
  
  /**
   * The time scale underlying this unit of time.
   * @return the time scale underlying this unit of time.
   */
  public final TimeUnit timeUnit() {
    return this.timeUnit;
  }
  
  /**
   * The length of this unit of time relative to the underlying time scale.
   * @return the length of this unit of time relative to the underlying time scale.
   */
  public final int unitLength() {
    return this.unitLength;
  }
  
  /**
   * Create and return a new TimeScale representing exactly one year.
   * @return a new TimeScale representing exactly one year.
   */
  public static final TimeScale oneYear() {
    return new TimeScale(TimeUnit.YEAR, 1);
  }
  
  /**
   * Compute and return the number of times this TimeScale occurs in the given TimeScale.
   * <p>
   * For example, if this time scale is a month and the given time scale is half a year, the
   * return value should equal 6, since a month occurs 6 times in one year. Note that
   * for practical purposes the double returned by this method will very often be
   * coerced to a long or integer.
   * </p>
   * @param otherTimeScale the time scale for which the frequency of occurrence of this time scale 
   * is to be found.
   * @return the number of times this TimeScale occurs in the provided TimeScale.
   */
  public double frequencyPer(final TimeScale otherTimeScale) {
    return otherTimeScale.totalDuration() / this.totalDuration();
  }
  
  /**
   * The total amount of time in this time scale measured in seconds, the base SI unit of time.
   * @return the total amount of time in this time scale measured in seconds.
   */
  public final double totalDuration() {
    
    double thisDuration = this.timeUnit.totalDuration();
    
    // Since the duration is measured in seconds and is treated by the Duration class as a long, we need
    //     to treat time scales less than one second as special cases and return the values ourselves.
    switch (this.timeUnit) {
      case NANOSECOND:
        return 1E-9 * this.unitLength;
      case MICROSECOND:
        return 1E-6 * this.unitLength;
      case MILLISECOND:
        return 1E-3 * this.unitLength;
      default:
        return thisDuration * this.unitLength;
    }
    
  }

}
