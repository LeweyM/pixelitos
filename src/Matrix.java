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

  public void set(int x, int y, int val) {
    cells[index(x, y)] = val;
  }

  private int index(int x, int y) {
    return x + (y * size);
  }
}
