package pixel;

import java.util.List;

public class SoilPixel extends PowderPixel {

  private static final int MAX_LIFEPOINTS = 200;
  private int lifePoints;

  @Override
  public int red() {
    return 62;
  }

  @Override
  public int green() {
    return 49 + (lifePoints / 2);
  }

  @Override
  public int blue() {
    return 23;
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    final Pixel oneUp = m.get(x, y - 1);
    final Pixel twoUp = m.get(x, y - 2);
    final Pixel oneDown = m.get(x, y + 1);
    final Pixel twoDown = m.get(x, y + 2);

    if (fullyAlive()) {
      // todo: optimize
      if (Math.random() > 0.9999) {
        return Utils.listOfChanges(new Change(x, y-1, new WormPixel()));
      }
    }

    if (oneUp.isEmpty() && oneDown.isSoil() && twoDown.isSoil()) {
      grow();
    } else if (twoUp.isEmpty() && oneDown.isSoil() && twoDown.isSoil()) {
      limitedGrow() ;
    } else {
      die();
    }

    return super.process(m, x, y);
  }

  public boolean fullyAlive() {
    return lifePoints == MAX_LIFEPOINTS;
  }

  private void limitedGrow() {
    this.lifePoints = Math.min(this.lifePoints + 1, 80);
  }

  private void die() {
    this.lifePoints = Math.max(0, this.lifePoints - 4);
  }

  private void grow() {
    this.lifePoints = Math.min(this.lifePoints + 1, MAX_LIFEPOINTS);
  }

}
