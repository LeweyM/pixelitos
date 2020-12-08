package pixel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public class ButterFlyPixel extends Pixel {

  int age;
  int xDirection;
  int yDirection;
  private final List<Integer> changes;

  public ButterFlyPixel() {
    age = 0;
    changes = Arrays
        .asList(1, 1, 1, 1, 1, -1, -1, -1, -1, -1);
    Collections.shuffle(changes);
    xDirection = changes.get(0);
    yDirection = changes.get(1);
  }

  @Override
  public int color(PApplet applet) {
    return applet.color(200);
  }

  @Override
  public int red() {
    return 200;
  }

  @Override
  public int green() {
    return 200;
  }

  @Override
  public int blue() {
    return 200;
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    age++;

    if (age == 1000) {
      return Utils.listOfChanges(new Change(x, y, new Pixel()),
          new Change(x, y, new SeedPixel()));
    }

    if (age % 10 == 0 && m.get(x + xDirection, y + yDirection).isEmpty()) {
      return Utils.listOfChanges(
          new Change(x, y, new Pixel()),
          new Change(x + xDirection, y + yDirection, this)
      );
    }

    if (age % 89 == 0) {

      xDirection *= changes.get((age + 1) % changes.size());
      yDirection *= changes.get(age % changes.size());
    }

    return super.process(m, x, y);
  }
}
