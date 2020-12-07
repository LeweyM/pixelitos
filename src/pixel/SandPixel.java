package pixel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public class SandPixel extends Pixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(235, 193, 85);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    final Pixel below = m.get(x, y + 1);
    final Pixel belowLeft = m.get(x-1, y + 1);
    final Pixel belowRight = m.get(x+1, y + 1);

    if (below.getClass() == Pixel.class) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x, y + 1, new SandPixel()));
      return changes;
    } else if (belowLeft.getClass() == Pixel.class) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x-1, y + 1, new SandPixel()));
      return changes;
    } else if (belowRight.getClass() == Pixel.class) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x+1, y + 1, new SandPixel()));
      return changes;
    } else {
      return Collections.emptyList();
    }
  }

}
