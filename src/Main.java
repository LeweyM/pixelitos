import processing.core.PApplet;

public class Main extends PApplet{

  private Matrix matrix;
  private int size;

  public void settings(){
    size(200, 200);
  }

  @Override
  public void setup() {
    super.setup();
    size = 10;
    matrix = new Matrix(size);
  }

  public void draw(){
    background(0);
    drawCells();
    ellipse(mouseX, mouseY, 20, 20);
  }

  private void drawCells() {
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        draw(x, y, matrix.get(x, y));
      }
    }
  }

  private void draw(int x, int y, int i) {
    final int d = 200 / size;
    rect(x*d, y*d, d, d);
  }


  public static void main(String... args){
    PApplet.main("Main");
  }
}
