package pixel;

import static pixel.Utils.listOfChanges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import processing.core.PApplet;

public class SeedPixel extends FallingPixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(100, 0, 0);
  }

  @Override
  public int red() {
    return 100;
  }

  @Override
  public int green() {
    return 0;
  }

  @Override
  public int blue() {
    return 0;
  }
  @Override
  public List<Change> process(Matrix m, int x, int y) {
    final Pixel below = m.get(x, y + 1);

    if (!below.isEmpty() && !below.isSoil()) {
      return listOfChanges(new Change(x, y, new Pixel()));
    }

    if (below.isSoil()) {
      SoilPixel soil = (SoilPixel) below;
      if (soil.fullyAlive() && germinates()) {
        return Utils.listOfChanges(new Change(x, y + 1, new TreePixel()));
      }
    }

    if (!below.isEmpty()) {
      return Utils.listOfChanges(new Change(x, y, new Pixel()));
    }

    return super.process(m,x,y);
  }

  private boolean germinates() {
    return Math.random() > 0.8;
  }

}
