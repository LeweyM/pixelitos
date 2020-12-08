package pixel;

import java.util.Stack;

public class BufferMatrix implements Matrix {

  private final int size;
  private final Pixel[] pixels;

  public BufferMatrix(int size) {
    pixels = new Pixel[size * size];
    this.size = size;
    for (int i = 0; i < size * size; i++) {
      pixels[i] = new Pixel();
    }
  }

  private BufferMatrix(int size, Pixel[] pixels) {
    this.size = size;
    this.pixels = pixels;
  }

  public Matrix next() {
    Stack<Change> changes = new Stack<>();

    Matrix next = copy();
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        changes.addAll(get(x, y).process(this, x, y));
      }
    }

    changes.forEach(c-> {
      final Pixel nextPixel = next.get(c.x(), c.y()).resolveConflict(c.pixel());
      next.set(c.x(), c.y(), nextPixel);
    });

    return next;
  }

  public Pixel get(int x, int y) {
    if (y >= size || y < 0 || x >= size || x < 0) return new WallPixel();
    return pixels[index(x, y)];
  }

  public void set(int x, int y, Pixel p) {
    if (y >= size || y < 0 || x >= size || x < 0) return;
    pixels[index(x, y)] = p;
  }

  private int index(int x, int y) {
    return x + (y * size);
  }

  private BufferMatrix copy() {
    Pixel[] nextCells = new Pixel[size*size];
    System.arraycopy(pixels, 0, nextCells, 0, pixels.length);
    return new BufferMatrix(size, nextCells);
  }
}
