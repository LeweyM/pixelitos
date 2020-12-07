package pixel;

import processing.core.PApplet;

public class WallPixel extends Pixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(255, 255, 255);
  }
  
}
