package pixel;

import java.util.List;
import java.util.Stack;

public class MarkedBufferMatrix implements Matrix {

  private final int size;
  private final Pixel[] pixels;

  public MarkedBufferMatrix(int size) {
    pixels = new Pixel[size * size];
    this.size = size;
    for (int i = 0; i < size * size; i++) {
      pixels[i] = new Pixel();
    }
  }

  private MarkedBufferMatrix(int size, Pixel[] pixels) {
    this.size = size;
    this.pixels = pixels;
  }

  public Matrix next() {
    Stack<List<Change>> changes = new Stack<>();
    boolean[] marked = new boolean[size*size];

    Matrix next = copy();

    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        changes.add(get(x, y).process(this, x, y));
      }
    }

    changes.forEach(change -> {
      if (change.size() == 2 && change.get(0).pixel.isEmpty()) {
        // swap
        final Change destination = change.get(1);
        boolean destinationIsMarked = marked[index(destination.x(), destination.y())];
        if (destinationIsMarked) {
          // todo: density/water/etc.
        } else {
          // todo: conflict resolution
          change.forEach(c -> next.set(c.x(), c.y(), c.pixel));
          marked[index(destination.x(), destination.y())] = true;
        }
      } else {
        change.forEach(c -> next.set(c.x(), c.y(), c.pixel));
      }
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

  private MarkedBufferMatrix copy() {
    Pixel[] nextCells = new Pixel[size*size];
    System.arraycopy(pixels, 0, nextCells, 0, pixels.length);
    return new MarkedBufferMatrix(size, nextCells);
  }
}
