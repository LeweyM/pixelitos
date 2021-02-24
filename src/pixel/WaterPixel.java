package pixel;

import java.util.List;

public class WaterPixel extends Pixel {

  @Override
  public int blue() {
    return 250;
  }

  @Override
  public int red() {
    return 0;
  }

  @Override
  public int green() {
    return 100;
  }

  @Override
  public boolean isSolid() {
    return false;
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {

    // down
    if (m.get(x, y + 1).isEmpty()) {
      return swap(m, x, y, x, y + 1);
    }

    if (System.nanoTime() % 2 != 0) {
      if (m.get(x - 1, y + 1).isEmpty()) {
        return swap(m, x, y, x - 1, y + 1);
      }
      if (m.get(x + 1, y + 1).isEmpty()) {
        return swap(m, x, y, x + 1, y + 1);
      }
      if (m.get(x - 1, y).isEmpty()) {
        return swap(m, x, y, x - 1, y);
      }
      if (m.get(x + 1, y).isEmpty()) {
        return swap(m, x, y, x + 1, y);
      }
    } else {
      if (m.get(x + 1, y + 1).isEmpty()) {
        return swap(m, x, y, x + 1, y + 1);
      }
      if (m.get(x - 1, y + 1).isEmpty()) {
        return swap(m, x, y, x - 1, y + 1);
      }
      if (m.get(x + 1, y).isEmpty()) {
        return swap(m, x, y, x + 1, y);
      }
      if (m.get(x - 1, y).isEmpty()) {
        return swap(m, x, y, x - 1, y);
      }
    }

    return super.process(m, x, y);
  }

  private List<Change> swap(Matrix m, int x, int y, int x2, int y2) {
    return Utils.listOfChanges(
        new Change(x, y, m.get(x2, y2)),
        new Change(x2, y2, m.get(x, y))
    );
  }
}
