package pixel;

import java.util.ArrayList;
import java.util.List;

public class FirePixel extends Pixel {
  private int age;

  @Override
  public int red() {
    return 200;
  }

  @Override
  public int green() {
    return 0;
  }

  @Override
  public int blue() {
    return 0;
  }

  @Override
  public boolean isSolid() {
    return false;
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    age++;

    if (age % 10 == 0) {
      final ArrayList<Change> trees = new ArrayList<>();
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          final Pixel p = m.get(x + i, y + j);
          if (p.getClass() == TreePixel.class || p.getClass() == CanopyPixel.class) {
            trees.add(new Change(x+i, y+j, new FirePixel()));
          }
        }
      }
      // burn tree
      if (trees.size() > 0) return trees;
    }

    if (age == 50) {
      final ArrayList<Change> spread = new ArrayList<>();
      for (int i = -1; i <= 1; i++) {
          if (m.get(x+i, y+1).isEmpty()) {
            spread.add(new Change(x+i, y+1, new FirePixel()));
          }
      }
      return spread;
    }

    if (age == 100) {
      return Utils.listOfChanges(new Change(x, y, new Pixel()));
    }

    return super.process(m, x, y);
  }
}
