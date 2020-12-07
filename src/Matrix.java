import processing.core.PApplet;

public class Matrix {

  private final int size;
  private int[] cells;

  public Matrix(int size) {
    cells = new int[size * size];
    this.size = size;
    for (int i = 0; i < size * size; i++) {
      cells[i] = 0;
    }
  }

  public int get(int x, int y) {
    return cells[index(x, y)];
  }

  public int getColor(int x, int y, PApplet applet) {
    switch (get(x, y)) {
      case 1: return applet.color(255, 255, 255);
      default: return applet.color(0, 0, 0);
    }
  }

  public void set(int x, int y, int val) {
    cells[index(x, y)] = val;
  }

  private int index(int x, int y) {
    return x + (y * size);
  }
}
