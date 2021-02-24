import static pixel.Pixel.EMPTY_PIXEL;

import java.time.Duration;
import java.time.Instant;
import pixel.ButterFlyPixel;
import pixel.MarkedBufferMatrix;
import pixel.Pixel;
import pixel.SandPixel;
import pixel.SeedPixel;
import pixel.SoilPixel;
import pixel.WallPixel;
import pixel.WaterPixel;
import pixel.WormPixel;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.KeyEvent;

public class Main extends PApplet {

  private MarkedBufferMatrix matrix;
  private final int size = 100;
  private final int aspectRatio = 2;
  private final int resolution = 800;
  private final int pixelSize = resolution / size;
  private Pixel defaultPixel;
  private boolean eraseMode = false;
  private Instant startedThrottling = Instant.now();
  private long throttleSpeed;
  private PixelTypeButton[] buttons;
  private boolean freeze;
  private boolean isInStepperMode;

  public void settings() {
    size(resolution * aspectRatio, resolution, JAVA2D);
  }

  @Override
  public void setup() {
    super.setup();
    this.buttons = new PixelTypeButton[]{
        new PixelTypeButton(new SoilPixel(), 1),
        new PixelTypeButton(new SeedPixel(), 100),
        new PixelTypeButton(new SandPixel()),
        new PixelTypeButton(new WallPixel()),
        new PixelTypeButton(new WormPixel(), 100),
        new PixelTypeButton(new ButterFlyPixel(), 100),
        new PixelTypeButton(new WaterPixel(), 1),
    };
    defaultPixel = new SandPixel();
    matrix = new MarkedBufferMatrix(size * aspectRatio, size);
    seedWithSoil(2);
//    frameRate(10);
    isInStepperMode = false;
  }

  public void draw() {
    final PGraphics graphics = createGraphics(width, height, JAVA2D);
    graphics.noStroke();
    graphics.beginDraw();
    if (freeze) {
      matrix = (MarkedBufferMatrix) matrix.onlyPaint(graphics, pixelSize);
    } else {
      matrix = (MarkedBufferMatrix) matrix.next(graphics, pixelSize);
    }
    graphics.endDraw();
    image(graphics, 0, 0);

    stepperMode();

    final boolean hasClicked = buttons();
    if (!hasClicked) {
      handleClick();
    }

    printFrameRate();
  }

  private void stepperMode() {
    if (isInStepperMode) {
      freeze = true;
    }
  }

  private void printFrameRate() {
    fill(200);
    text(frameRate, 20, 60);
  }

  private void seedWithSoil(int soilDensity) {
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size * aspectRatio; x++) {
        if ((x + y) % Math.round((Math.random() + 1) * soilDensity) == 0) {
          matrix.set(x, y, new SoilPixel());
        }
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent event) {
    if (event.getKeyCode() == 10) {
      isInStepperMode = !isInStepperMode;
    }
    freeze = false;
  }

  private boolean resetButton() {
    fill(100, 100, 100);
    ellipse(resolution - 30, 30, 15, 15);
    if (mousePressed && dist(mouseX, mouseY, resolution - 30, 30) < 15) {
      setup();
      return true;
    }
    return false;
  }

  private void handleClick() {
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
    return Instant.now()
        .isBefore(this.startedThrottling.plus(Duration.ofMillis(this.throttleSpeed)));
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

  private boolean buttons() {
    boolean hasClicked;
    hasClicked = resetButton();
    int x = 15;
    for (PixelTypeButton button : buttons) {
      fill(button.pixel.red(), button.pixel.green(), button.pixel.blue());
      ellipse(x, 30, 15, 15);
      if (mousePressed && dist(mouseX, mouseY, x, 30) <= 15.0) {
        this.defaultPixel = button.pixel;
        this.throttleSpeed = button.throttleSpeed();
        hasClicked = true;
      }
      x += 30;
    }
    return hasClicked;
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

  public static void main(String... args) {
    PApplet.main("Main");
  }
}
