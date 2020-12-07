package pixel;

import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public class Pixel {

  public static final Class<Pixel> EMPTY_PIXEL = Pixel.class;

  public int color(PApplet applet) {
    return applet.color(255, 255, 255);
  }

  public List<Change> process(Matrix m, int x, int y) {
    return Collections.emptyList();
  }

  public Pixel resolveConflict(Pixel other) {
    return other;
  }

  public boolean isEmpty() {
    return this.getClass() == EMPTY_PIXEL;
  }

  public boolean isSoil() {
    return this.getClass() == SoilPixel.class;
  }
}
