package pixel;

import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public class FallingPixel extends Pixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(200, 100, 200);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    final Pixel below = m.get(x, y + 1);

    if (below.getClass() != EMPTY_PIXEL) {
      return Collections.emptyList();
    } else {
      return Utils.listOfChanges(
          new Change(x, y, new Pixel()),
          new Change(x, y + 1, new FallingPixel())
      );
    }
  }

}
