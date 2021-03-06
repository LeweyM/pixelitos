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

    if (below.getClass() == EMPTY_PIXEL) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x, y + 1, this));
      return changes;
    } else if (belowLeft.getClass() == EMPTY_PIXEL) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x-1, y + 1, this));
      return changes;
    } else if (belowRight.getClass() == EMPTY_PIXEL) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x+1, y + 1, this));
      return changes;
    } else {
      return Collections.emptyList();
    }
  }

}
