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

    final Pixel other = m.get(x, y + 1);
    if (other.isEmpty()) {
      return Utils.listOfChanges(
          new Change(x, y, m.get(x, y + 1)),
          new Change(x, y + 1, new WaterPixel())
      );
    }

    for (int i = -1; i <= 1; i++) {
      for (int j = 0; j <= 1; j++) {
        if (m.get(x + i, y + j).isEmpty()) {
          return Utils.listOfChanges(
              new Change(x, y, m.get(x, y + 1)),
              new Change(x + i, y + j, new WaterPixel())
          );
        }
      }
    }

    return super.process(m, x, y);
  }
}
