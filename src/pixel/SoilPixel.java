package pixel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public class SoilPixel extends Pixel {

  private int lifePoints;

  @Override
  public int color(PApplet applet) {
    return applet.color(62, 49 + (lifePoints / 2), 23);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    final Pixel oneUp = m.get(x, y - 1);
    final Pixel twoUp = m.get(x, y - 2);
    final Pixel oneDown = m.get(x, y + 1);
    final Pixel twoDown = m.get(x, y + 2);
    final Pixel belowLeft = m.get(x-1, y + 1);
    final Pixel belowRight = m.get(x+1, y + 1);

    if (oneUp.isEmpty() && oneDown.isSoil() && twoDown.isSoil()) {
      grow();
    } else if (twoUp.isEmpty() && oneDown.isSoil() && twoDown.isSoil()) {
      limitedGrow() ;
    }else {
      die();
    }

    if (oneDown.isEmpty()) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x, y + 1, new SoilPixel()));
      return changes;
    } else if (belowLeft.isEmpty()) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x-1, y + 1, new SoilPixel()));
      return changes;
    } else if (belowRight.isEmpty()) {
      final ArrayList<Change> changes = new ArrayList<>();
      changes.add(new Change(x, y, new Pixel()));
      changes.add(new Change(x+1, y + 1, new SoilPixel()));
      return changes;
    } else {
      return Collections.emptyList();
    }
  }

  private void limitedGrow() {
    this.lifePoints = Math.min(this.lifePoints + 1, 80);
  }

  private void die() {
    this.lifePoints = Math.max(0, this.lifePoints - 4);
  }

  private void grow() {
    this.lifePoints = Math.min(this.lifePoints + 1, 200);
  }

}
