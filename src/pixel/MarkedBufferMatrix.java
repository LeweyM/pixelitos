package pixel;

import static java.awt.geom.Point2D.distance;

import com.sun.jmx.remote.internal.ArrayQueue;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import processing.core.PGraphics;

public class MarkedBufferMatrix implements Matrix {

  private final int size;
  private final int width;
  private final int height;
  private final Pixel[] pixels;

  public MarkedBufferMatrix(int width, int height) {
    pixels = new Pixel[width * height];
    this.size = width * height;
    this.width = width;
    this.height = height;
    for (int i = 0; i < size; i++) {
      pixels[i] = new Pixel();
    }
  }

  private MarkedBufferMatrix(int width, int height, Pixel[] pixels) {
    this.size = width * height;
    this.width = width;
    this.height = height;
    this.pixels = pixels;
  }

  public Matrix next(PGraphics chunk, int pixSize) {
    boolean[] marked = new boolean[size];

    Matrix next = copy();

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        List<List<Change>> changes = new Stack<>();
        changes.add(get(x, y).process(this, x, y));
        processChanges(changes, marked, next);
      }
    }

    paint(chunk, pixSize);

    return next;
  }

  public Matrix onlyPaint(PGraphics chunk, int pixSize) {
    paint(chunk, pixSize);
    return this;
  }

  private void paint(PGraphics chunk, int pixSize) {
    final Sky sky = new Sky(height, width);
    chunk.noStroke();

    final int moonY = sky.moonY();
    final int moonX = sky.moonX();
    final int brightness = (int) (sky.brightness() * 255);
    final int horizon = height / 2;

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Pixel pixel = get(x, y);
        if (pixel.isEmpty()) {
          if (distance(x, y, moonX, moonY) < 5) {
            chunk.fill(255, 255, 255);
          } else {
            int distanceFromBottom = height - y;
            int r = horizon - Math.min(distanceFromBottom, horizon);
            chunk.fill(r + brightness, brightness, brightness);
          }
          chunk.rect(x * pixSize, y * pixSize, pixSize, pixSize);
        } else {
          chunk.fill(pixel.red(), pixel.green(), pixel.blue());
          chunk.rect(x * pixSize, y * pixSize, pixSize, pixSize);
        }
      }
    }
  }

  private void processChanges(List<List<Change>> changeSets, boolean[] marked, Matrix next) {
    changeSets.forEach(changeSet -> {
      if (changeSet.size() == 2) {
        // todo: if denser obj is going down, swap, else do nothing
        // swap
        final Change a = changeSet.get(0);
        final Change b = changeSet.get(1);
        if (!marked[index(a.x(), a.y())] && !marked[index(b.x(), b.y())]) {
          next.set(a.x(), a.y(), a.pixel);
          next.set(b.x(), b.y(), b.pixel);
          marked[index(a.x(), a.y())] = true;
          marked[index(b.x(), b.y())] = true;
        }
      } else {
        changeSet.forEach(c -> next.set(c.x(), c.y(), c.pixel));
      }
    });
  }

  public Pixel get(int x, int y) {
    if (y >= height || y < 0 || x >= width || x < 0) {
      return new WallPixel();
    }
    return pixels[index(x, y)];
  }

  public void set(int x, int y, Pixel p) {
    if (y >= height || y < 0 || x >= width || x < 0) {
      return;
    }
    pixels[index(x, y)] = p;
  }

  private int index(int x, int y) {
    return x + (y * width);
  }

  private MarkedBufferMatrix copy() {
    Pixel[] nextCells = new Pixel[size];
    System.arraycopy(pixels, 0, nextCells, 0, pixels.length);
    return new MarkedBufferMatrix(width, height, nextCells);
  }
}
