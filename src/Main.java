import static pixel.Pixel.EMPTY_PIXEL;

import java.util.ArrayList;
import java.util.List;
import pixel.BufferMatrix;
import pixel.FallingPixel;
import pixel.Matrix;
import pixel.Pixel;
import pixel.SandPixel;
import pixel.SeaWeedPixel;
import pixel.SeedPixel;
import pixel.SoilPixel;
import pixel.WallPixel;
import processing.core.PApplet;

public class Main extends PApplet {

  private Matrix matrix;
  private final int size = 50;
  private final int resolution = 400;
  private final int d = resolution / size;
  private List<PixelTypeButton> pixelButtons;
  private Pixel defaultPixel;
  private boolean eraseMode = false;
  private Pixel[] pixelTypes;

  public void settings() {
    size(resolution, resolution);
  }

  @Override
  public void setup() {
    super.setup();
    pixelTypes = new Pixel[]{
        new SeedPixel(),
        new SoilPixel(),
        new FallingPixel(),
        new SandPixel(),
        new WallPixel(),
        new SeaWeedPixel(),
    };
    defaultPixel = new SandPixel();
    matrix = new BufferMatrix(size);
    setupButtons();
//    frameRate(10);
  }

  public void draw() {
    matrix = matrix.next();
    background(0);
    drawCells();
    paintMouse();
    click();
    buttons();
  }

  private void paintMouse() {
    fill(100, 100, 100);
    ellipse(mouseX, mouseY, 15, 15);
  }

  private void click() {
    if (mousePressed) {
      final int x = mouseX / d;
      final int y = mouseY / d;
      if (this.eraseMode) {
        matrix.set(x, y, new Pixel());
      } else {
        matrix.set(x, y, this.defaultPixel);
      }
    }
  }

  @Override
  public void mousePressed() {
    super.mousePressed();
    final int x = mouseX / d;
    final int y = mouseY / d;
    if (matrix.get(x, y).getClass() != EMPTY_PIXEL) {
      this.eraseMode = true;
    }
  }

  @Override
  public void mouseReleased() {
    super.mouseReleased();
    this.eraseMode = false;
  }

  private void buttons() {
    for (PixelTypeButton button: pixelButtons) {
      fill(button.pixel.color(this));
      ellipse(button.x, button.y, 15, 15);
      if (mousePressed && dist(mouseX, mouseY, button.x, button.y) <= 15.0) {
        this.defaultPixel = button.pixel;
      }
    }
  }

  private void setupButtons() {
    pixelButtons = new ArrayList<>();
    for (int i = 0; i < pixelTypes.length; i++) {
      setPixelTypeButton(30 * i + 30, 30, pixelTypes[i]);
    }
  }

  private void setPixelTypeButton(int x, int y, Pixel pixel) {
    pixelButtons.add(new PixelTypeButton(x, y, 15, pixel));
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
    rect(x * d, y * d, d, d);
  }

  public static void main(String... args) {
    PApplet.main("Main");
  }
}
