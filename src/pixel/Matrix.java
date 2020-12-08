package pixel;

import processing.core.PGraphics;

public interface Matrix {

  Matrix next(PGraphics graphics, int pixSize);

  Pixel get(int x, int y);

  void set(int x, int y, Pixel p);
}
