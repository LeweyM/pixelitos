package pixel;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import processing.core.PApplet;

public class WormPixel extends Pixel {

  int life = 0;
  int direction;

  public WormPixel() {
    final int rand = new Random().nextInt(2) - 1;
    if (rand < 0) {
      this.direction = -1;
    } else {
      this.direction = 1;
    }
  }

  private WormPixel(int direction) {
    this.direction = direction;
  }

  @Override
  public int red() {
    return 200;
  }

  @Override
  public int green() {
    return 10;
  }

  @Override
  public int blue() {
    return 25;
  }

  @Override
  public int color(PApplet applet) {
    return applet.color(200, 10, 25);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    life++;

    final Pixel below = m.get(x, y + 1);
    final Pixel side = m.get(x - direction, y);
    final Pixel sideAndUp = m.get(x - direction, y - 1);

    // die
    if (life > 20) {
      return Utils.listOfChanges(new Change(x, y, new Pixel()));
    }

    if (life == 10) {

      // lay egg
      if (sideAndUp.isCanopy()) {
        return Utils.listOfChanges(new Change(x - direction, y - 2, new LaveePixel()));
      }

      // climb tree
      if (side.getClass() == TreePixel.class) {
        return Utils.listOfChanges(new Change(x, y - 1, new WormPixel(direction)));
      }

      if (below.isSolid()) {
        // walk
        if (side.isEmpty()) {
          return Utils.listOfChanges(new Change(x - direction, y, new WormPixel(direction)));
        }
        //climb
        if (sideAndUp.isEmpty()) {
          return Utils.listOfChanges(new Change(x - direction, y - 1, new WormPixel(direction)));
        }
      }
    }

    //hold onto tree
    if (side.getClass() == TreePixel.class) {
      return Collections.emptyList();
    }

    if (!below.isEmpty()) {
      return Collections.emptyList();
    } else {
      return Utils.listOfChanges(
          new Change(x, y, new Pixel()),
          new Change(x, y + 1, new WormPixel(direction))
      );
    }
  }
}
