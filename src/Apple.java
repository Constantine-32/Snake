import processing.core.PApplet;

public class Apple {

  private PApplet p;
  private int x;
  private int y;

  public Apple(PApplet p) {
    this.p = p;
    spawn();
  }

  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }

  public void spawn() {
    x = (int)p.random(40);
    y = (int)p.random(40);
  }

  public void show() {
    p.stroke(0);
    p.fill(255, 0, 0);
    p.rect(x*Sketch.SIZE, y*Sketch.SIZE, Sketch.SIZE, Sketch.SIZE);
  }
}
