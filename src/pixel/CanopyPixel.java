package pixel;

import static pixel.Utils.listOfChanges;

import java.util.List;
import java.util.stream.Stream;
import processing.core.PApplet;

public class CanopyPixel extends SolidPixel {
  int age = 0;

  @Override
  public int red() {
    return 102;
  }

  @Override
  public int green() {
    return 187;
  }

  @Override
  public int blue() {
    return 0;
  }

  @Override
  public int color(PApplet applet) {
    return applet.color(102, 187, 0);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    age++;

    final Pixel rightDown = m.get(x + 1, y + 1);
    final Pixel rightRightDown = m.get(x + 2, y + 1);
    final Pixel leftDown = m.get(x - 1, y + 1);
    final Pixel leftLeftDown = m.get(x - 2, y + 1);
    final Pixel below = m.get(x, y + 1);

    if (age == 10000) {
      return listOfChanges(new Change(x, y, new FirePixel()));
    }

    if (age % 50 == 0) {
      if (below.getClass() == TreePixel.class) {
        return listOfChanges(
            new Change(x + 1, y - 1, new CanopyPixel()),
            new Change(x - 1, y - 1, new CanopyPixel())
        );
      }

      if (Stream.of(leftDown, leftLeftDown).anyMatch(Pixel::isCanopy)) {
        return listOfChanges(new Change(x + 1, y, new CanopyPixel()));
      }

      if (Stream.of(rightDown, rightRightDown).anyMatch(Pixel::isCanopy)) {
        return listOfChanges(new Change(x - 1, y, new CanopyPixel()));
      }
    }

    return super.process(m, x, y);
  }
}
