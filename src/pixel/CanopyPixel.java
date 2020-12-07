package pixel;

import static pixel.Utils.listOfChanges;

import java.util.List;
import java.util.stream.Stream;
import processing.core.PApplet;

public class CanopyPixel extends Pixel {

  @Override
  public int color(PApplet applet) {
    return applet.color(102, 187, 0);
  }

  @Override
  public List<Change> process(Matrix m, int x, int y) {
    final Pixel leftOne = m.get(x - 1, y);
    final Pixel leftTwo = m.get(x - 2, y);
    final Pixel downOneLeftOne = m.get(x - 1, y + 1);
    final Pixel downOneLeftTwo = m.get(x - 2, y + 1);

    if (m.get(x, y + 1).getClass() == TreePixel.class) {
      return listOfChanges(
          new Change(x + 1, y - 1, new CanopyPixel()),
          new Change(x - 1, y - 1, new CanopyPixel())
      );
    }

    if (Stream.of(leftOne, leftTwo, downOneLeftOne, downOneLeftTwo).anyMatch(Pixel::isCanopy)) {
      return listOfChanges(new Change(x - 1, y, new CanopyPixel()));
    }

    return super.process(m, x, y);
  }
}
