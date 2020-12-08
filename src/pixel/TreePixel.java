package pixel;

import static pixel.Utils.listOfChanges;

import java.util.List;
import processing.core.PApplet;

public class TreePixel extends SolidPixel {

  private int lifePoints;

  @Override
  public int color(PApplet applet) {
    return applet.color(10, 10, 10);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    lifePoints+=10;

    if (canGrow() && m.get(x, y-1).isEmpty()) {
      if (blooms()) {
        return listOfChanges(new Change(x, y, new CanopyPixel()));
      } else {
        return listOfChanges(new Change(x, y-1, new TreePixel()));
      }
    } else {
      return super.process(m, x, y);
    }
  }

  private boolean blooms() {
    // todo: optimize
    return Math.random() > 0.9;
  }

  private boolean canGrow() {
    return this.lifePoints > 150;
  }
}
