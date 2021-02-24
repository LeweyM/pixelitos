package pixel;

import java.time.Duration;
import java.time.Instant;

public class Sky {

  private static final double DAY_IN_MILLIS = 5000.0;
  public static Instant startTime = Instant.now();
  private final int zenith;
  private final int arcWidth;
  private final int screenHeight;
  private final int screenWidth;

  public Sky(int height, int width) {
    this.arcWidth = (width / 5) * 3;
    this.zenith = (height / 5) * 4;
    this.screenHeight = height;
    this.screenWidth = width;
  }

  public static int time() {
    return Math.toIntExact(Duration.between(startTime, Instant.now()).toMillis());
  }

  // between 0 and 1
  public double brightness() {
    return (Math.sin((time() / DAY_IN_MILLIS)) + 1) / 2 ;
  }

  public int moonX() {
    final double cosOfElapsedTime = Math.cos((time() / DAY_IN_MILLIS));
    return ((int) (cosOfElapsedTime * -arcWidth)) + (screenWidth / 2);
  }

  public int moonY() {
    final double sinOfElapsedTime = Math.sin((time() / DAY_IN_MILLIS));
    final int arc = (int) (sinOfElapsedTime * -zenith);
    return screenHeight - arc - (screenHeight / 4);
  }

}
