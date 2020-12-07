import processing.core.PApplet;

public class EmptyPixel implements Pixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(0, 0, 0);
  }
}
