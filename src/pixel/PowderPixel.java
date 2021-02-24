package pixel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public abstract class PowderPixel extends SolidPixel {

  @Override
  public int color(PApplet applet) {
    return super.color(applet);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    final Pixel below = m.get(x, y + 1);
    final Pixel belowLeft = m.get(x-1, y + 1);
    final Pixel belowRight = m.get(x+1, y + 1);

    if (!below.isSolid()) {
      return swap(m, x, y, x, y+1);
    }
    if (System.nanoTime() % 2 != 0) {
      if (!belowLeft.isSolid()) {
        return swap(m, x, y, x-1, y+1);
      }
      if (!belowRight.isSolid()) {
        return swap(m, x, y, x+1, y+1);
      }
    } else {
      if (!belowRight.isSolid()) {
        return swap(m, x, y, x+1, y+1);
      }
      if (!belowLeft.isSolid()) {
        return swap(m, x, y, x-1, y+1);
      }
    }

    return Collections.emptyList();
  }

  private List<Change> swap(Matrix m, int x, int y, int x2, int y2) {
    return Utils.listOfChanges(
        new Change(x, y, m.get(x2, y2)),
        new Change(x2, y2, m.get(x, y))
    );
  }
}
