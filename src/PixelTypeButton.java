import pixel.Pixel;

public class PixelTypeButton {

  public final int x;
  public final int y;
  public final int size;
  public Pixel pixel;

  public PixelTypeButton(int x, int y, int size, Pixel pixel) {
    this.x = x;
    this.y = y;
    this.size = size;
    this.pixel = pixel;
  }
}
