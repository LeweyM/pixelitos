package pixel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public class SeaWeedPixel extends Pixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(198, 237, 85);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    final Pixel below = m.get(x, y + 1);
    final Pixel belowLeft = m.get(x-1, y + 1);
    final Pixel belowRight = m.get(x+1, y + 1);

    if (blockedByPixel(below)) {
      return Collections.emptyList();
    } else if (blockedByPixel(belowLeft)) {
      return Collections.emptyList();
    } else if (blockedByPixel(belowRight)) {
      return Collections.emptyList();
    } else {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x, y + 1, new SeaWeedPixel()));
      return changes;
    }
  }

  private boolean blockedByPixel(Pixel below) {
    return below.getClass() != EMPTY_PIXEL;
  }

}
