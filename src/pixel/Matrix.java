package pixel;

public interface Matrix {

  Matrix next();

  Pixel get(int x, int y);

  void set(int x, int y, Pixel p);
}
