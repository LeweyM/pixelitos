package pixel;

import java.util.List;
import processing.core.PApplet;

public class SandPixel extends PowderPixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(235, 193, 85);
  }

  @Override
  public int red() {
    return 233;
  }

  @Override
  public int green() {
    return 193;
  }

  @Override
  public int blue() {
    return 85;
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    return super.process(m,x,y);
  }

}
