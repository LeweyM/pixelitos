public class Matrix {

  private final int size;
  private Pixel[] pixels;

  public Matrix(int size) {
    pixels = new Pixel[size * size];
    this.size = size;
    for (int i = 0; i < size * size; i++) {
      pixels[i] = new EmptyPixel();
    }
  }

  public Pixel get(int x, int y) {
    return pixels[index(x, y)];
  }

  public void set(int x, int y, Pixel p) {
    pixels[index(x, y)] = p;
  }

  private int index(int x, int y) {
    return x + (y * size);
  }
}
