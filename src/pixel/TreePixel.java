package pixel;

import static pixel.Utils.listOfChanges;

import java.util.List;
import processing.core.PApplet;

public class TreePixel extends SolidPixel {

  private final int direction;

  public TreePixel() {
    direction = 0;
  }

  private TreePixel(int i) {
    direction = i;
  }

  private int lifePoints;

  @Override
  public int red() {
    return 100;
  }

  @Override
  public int green() {
    return 10;
  }

  @Override
  public int blue() {
    return 10;
  }

  @Override
  public int color(PApplet applet) {
    return applet.color(10, 10, 10);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    lifePoints += 1;

    if (canGrow() && m.get(x + direction, y - 1).isEmpty()) {
//      if (branches()) {
//        if (System.nanoTime() % 2 == 0) {
//          return listOfChanges(
//              new Change(x + 1, y - 1, new TreePixel(1)),
//              new Change(x, y - 1, new TreePixel(direction))
//          );
//        } else {
//          return listOfChanges(
//              new Change(x + 1, y - 1, new TreePixel(-1)),
//              new Change(x, y - 1, new TreePixel(direction))
//          );
//        }
//      }
      if (blooms()) {
        return listOfChanges(new Change(x, y, new CanopyPixel()));
      } else {
        return listOfChanges(new Change(x + direction, y - 1, new TreePixel(direction)));
      }
    } else {
      return super.process(m, x, y);
    }
  }

  private boolean branches() {
    return Math.random() > 0.9;
  }

  private boolean blooms() {
    // todo: optimize
    return Math.random() > 0.9;
  }

  private boolean canGrow() {
    return this.lifePoints == 15;
  }
}
