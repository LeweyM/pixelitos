import processing.core.PApplet;

public class WallPixel implements Pixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(255, 255, 255);
  }
}
