package pixel;

import java.util.List;
import processing.core.PApplet;

public class LaveePixel extends SolidPixel {

  private int age = 0;

  @Override
  public int red() {
    return 150;
  }

  @Override
  public int green() {
    return 150;
  }

  @Override
  public int blue() {
    return 150;
  }

  @Override
  public int color(PApplet applet) {
    return applet.color(200);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    if (age++ == 100) {
      return Utils.listOfChanges(
          new Change(x, y, new Pixel()),
          new Change(x, y-1, new ButterFlyPixel())
      );
    }

    return super.process(m, x, y);
  }
}
