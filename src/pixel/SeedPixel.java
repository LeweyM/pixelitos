package pixel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public class SeedPixel extends Pixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(100, 0, 0);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    final Pixel below = m.get(x, y + 1);

    if (below.isSoil()) {

      SoilPixel soil = (SoilPixel) below;
      if (soil.fullyAlive() && germinates()) {
        final ArrayList<Change> changes = new ArrayList<>();
        changes.add(new Change(x, y + 1, new TreePixel()));
        return changes;
      }
    }

    if (!below.isEmpty()) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      return changes;
    } else {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x, y + 1, new SeedPixel()));
      return changes;
    }
  }

  private boolean germinates() {
    return Math.random() > 0.8;
  }

}
