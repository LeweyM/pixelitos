package pixel;

import processing.core.PApplet;

public abstract class SolidPixel extends Pixel {

  @Override
  public int color(PApplet applet) {
    return super.color(applet);
  }

  @Override
  public boolean isSolid() {
    return true;
  }
}
