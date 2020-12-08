package pixel;

import processing.core.PApplet;

public class WallPixel extends SolidPixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(10, 10, 10);
  }

}
