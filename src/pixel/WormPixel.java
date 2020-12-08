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
  public int color(PApplet applet) {
    return applet.color(200, 10, 25);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    life++;

    final Pixel below = m.get(x, y + 1);
    final Pixel side = m.get(x - direction, y);
    final Pixel sideAndUp = m.get(x-direction, y-1);

    if (life > 20) {
      return Utils.listOfChanges(new Change(x, y, new Pixel()));
    }

    if (below.isSolid()) {
      if (side.isEmpty()) {
        if (life == 10) {
          return Utils.listOfChanges(new Change(x - direction, y, new WormPixel(direction)));
        }
      }
      if (sideAndUp.isEmpty()) {
        if (life == 10) {
          return Utils.listOfChanges(new Change(x - direction, y-1, new WormPixel(direction)));
        }
      }
    }

    if (!below.isEmpty()) {
      return Collections.emptyList();
    } else {
      return Utils.listOfChanges(
          new Change(x, y, new Pixel()),
          new Change(x, y + 1, new WormPixel())
      );
    }
  }
}
