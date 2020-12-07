package pixel;

import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public class Pixel {

  public int color(PApplet applet) {
    return applet.color(0, 0, 0);
  }

  public List<Change> process(Matrix m, int x, int y) {
    return Collections.emptyList();
  }

  public Pixel resolveConflict(Pixel other) {
    return other;
  }
}
