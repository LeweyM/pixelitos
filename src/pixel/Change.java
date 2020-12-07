package pixel;

public class Change {

  int x, y;
  Pixel pixel;

  public Change(int x, int y, Pixel pixel) {
    this.x = x;
    this.y = y;
    this.pixel = pixel;
  }

  public int x() {
    return x;
  }

  public int y() {
    return y;
  }

  public Pixel pixel() {
    return pixel;
  }
}
