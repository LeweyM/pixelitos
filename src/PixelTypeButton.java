import pixel.Pixel;

public class PixelTypeButton {

  public int x;
  public int y;
  public final int size;
  public Pixel pixel;
  private final long throttleSpeed;

  public PixelTypeButton(Pixel pixel, long throttleSpeed) {
    this.pixel = pixel;
    x = 0;
    y = 0;
    size = 15;
    this.throttleSpeed = throttleSpeed;
  }

  public PixelTypeButton(Pixel pixel) {
    this.pixel = pixel;
    x = 0;
    y = 0;
    size = 15;
    this.throttleSpeed = 10;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public long throttleSpeed() {
    return throttleSpeed;
  }
}
