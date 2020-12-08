import static pixel.Pixel.EMPTY_PIXEL;

import java.time.Duration;
import java.time.Instant;
import pixel.MarkedBufferMatrix;
import pixel.Matrix;
import pixel.Pixel;
import pixel.SandPixel;
import pixel.SeedPixel;
import pixel.SoilPixel;
import pixel.WallPixel;
import pixel.WormPixel;
import processing.core.PApplet;

public class Main extends PApplet {

  private Matrix matrix;
  private final int size = 50;
  private final int resolution = 800;
  private final int d = resolution / size;
  private Pixel defaultPixel;
  private boolean eraseMode = false;
  private Instant startedThrottling = Instant.now();
  private long throttleSpeed;
  private PixelTypeButton[] buttons;

  public void settings() {
    size(resolution, resolution);
  }

  @Override
  public void setup() {
    super.setup();
    this.buttons = new PixelTypeButton[]{
        new PixelTypeButton(new SoilPixel()),
        new PixelTypeButton(new SeedPixel(), 100),
        new PixelTypeButton(new SandPixel()),
        new PixelTypeButton(new WallPixel()),
        new PixelTypeButton(new WormPixel(), 100),
    };
    defaultPixel = new SandPixel();
    matrix = new MarkedBufferMatrix(size);
//    frameRate(10);
  }

  public void draw() {
    matrix = matrix.next();
    background(0);
    drawCells();
    click();
    buttons();
    resetButton();
  }

  private void resetButton() {
    fill(100, 100, 100);
    ellipse(resolution - 30, 30, 15, 15);
    if (mousePressed && dist(mouseX, mouseY, resolution - 30, 30) < 15) {
      setup();
    }
  }

  private void click() {
    if (!mouseThrottled() && mousePressed) {
      layBrick();
      this.startedThrottling = Instant.now();
    }
  }

  private void layBrick() {
    final int x = mouseX / d;
    final int y = mouseY / d;
    if (this.eraseMode) {
      matrix.set(x, y, new Pixel());
    } else {
      matrix.set(x, y, getNewPixel());
    }
  }

  private boolean mouseThrottled() {
    return Instant.now().isBefore(this.startedThrottling.plus(Duration.ofMillis(this.throttleSpeed)));
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
    int x = 15;
    for (PixelTypeButton button: buttons) {
      fill(button.pixel.color(this));
      ellipse(x, 30, 15, 15);
      if (mousePressed && dist(mouseX, mouseY, x, 30) <= 15.0) {
        this.defaultPixel = button.pixel;
        this.throttleSpeed = button.throttleSpeed();
      }
      x += 30;
    }
  }

  private Pixel getNewPixel() {
    Pixel pixel = null;
    try {
      pixel = (Pixel) Class.forName(this.defaultPixel.getClass().getName()).newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return pixel;
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
