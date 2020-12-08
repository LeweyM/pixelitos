import static pixel.Pixel.EMPTY_PIXEL;

import java.time.Duration;
import java.time.Instant;
import pixel.ButterFlyPixel;
import pixel.MarkedBufferMatrix;
import pixel.Matrix;
import pixel.Pixel;
import pixel.SandPixel;
import pixel.SeedPixel;
import pixel.SoilPixel;
import pixel.WallPixel;
import pixel.WormPixel;
import processing.core.PApplet;
import processing.core.PGraphics;

public class Main extends PApplet {

  private Matrix matrix;
  private final int size = 50;
  private final int aspectRatio = 2;
  private final int resolution = 400;
  private final int pixelSize = resolution / size;
  private Pixel defaultPixel;
  private boolean eraseMode = false;
  private Instant startedThrottling = Instant.now();
  private long throttleSpeed;
  private PixelTypeButton[] buttons;

  public void settings() {
    size(resolution * aspectRatio, resolution);
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
        new PixelTypeButton(new ButterFlyPixel(), 100),
    };
    defaultPixel = new SandPixel();
    matrix = new MarkedBufferMatrix(size*aspectRatio, size);
//    frameRate(10);
  }

  public void draw() {
    matrix = matrix.next();
    paintBackground();
    drawCells();
    click();
    buttons();
    resetButton();
    fill(200);
    text(frameRate,20,60);
  }

  private void paintBackground() {
    int y = 0;
    while (y < resolution) {
      fill(0 + y, 214, 255);
      rect(0, y, width, 50);
      y += 50;
    }
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
    final int x = mouseX / pixelSize;
    final int y = mouseY / pixelSize;
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
    final int x = mouseX / pixelSize;
    final int y = mouseY / pixelSize;
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
      fill(button.pixel.red(), button.pixel.green(), button.pixel.blue());
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

  private void drawCellsCHUNK() {
    final PGraphics chunk = createGraphics(width, height);
    chunk.beginDraw();
    chunk.noStroke();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        final Pixel pixel = matrix.get(x, y);
        if (pixel.isEmpty()) {
          drawCHUNK(x, y, color(174 + (y * 3), 214, 255), chunk);
        } else {
          drawCHUNK(x, y, pixel.color(this), chunk);
        }
      }
    }
    chunk.endDraw();
    image(chunk, 0, 0, width, height);
  }

  private void drawCHUNK(int x, int y, int color, PGraphics chunk) {
    chunk.fill(color);
    chunk.rect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
  }

  private void drawCells() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        final Pixel pixel = matrix.get(x, y);
        if (!pixel.isEmpty()) {
          if (pixel.red() != 255) {
            drawFAST(x, y, pixel);
          } else {
            draw(x, y, pixel.color(this));
          }
        }
      }
    }
  }
  private void drawFAST(int x, int y, Pixel pixel) {
    fill(pixel.red(), pixel.green(), pixel.blue());
    noStroke();
    rect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
  }

  @Deprecated
  private void draw(int x, int y, int color) {
    fill(color);
    noStroke();
    rect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
  }

  public static void main(String... args) {
    PApplet.main("Main");
  }
}
