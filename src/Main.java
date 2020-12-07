import pixel.FallingPixel;
import pixel.Matrix;
import processing.core.PApplet;

public class Main extends PApplet{

  private Matrix matrix;
  private final int size = 10;
  private final int resolution = 400;
  private final int d = resolution / size;

  public void settings(){
    size(resolution, resolution);
  }

  @Override
  public void setup() {
    super.setup();
    matrix = new Matrix(size);
  }

  public void draw(){
    matrix = matrix.next();
    background(0);
    drawCells();
    if (mousePressed) {
      ellipse(mouseX, mouseY, 20, 20);
      matrix.set(mouseX/d, mouseY/d, new FallingPixel());
    }
  }

  private void drawCells() {
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        draw(x, y, matrix.get(x, y).color(this));
      }
    }
  }

  private void draw(int x, int y, int color) {
    fill(color);
    noStroke();
    rect(x* d, y* d, d, d);
  }


  public static void main(String... args){
    PApplet.main("Main");
  }
}
