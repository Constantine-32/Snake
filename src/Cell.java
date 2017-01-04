import processing.core.PApplet;
import processing.core.PConstants;

public class Cell {

  private PApplet p;
  private int x;
  private int y;
  private boolean[] line;

  public Cell(PApplet p, int x, int y) {
    this.p = p;
    this.x = x;
    this.y = y;
    line = new boolean[]{true, true, true, true};
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void moveW() {
    y--;
//    if (y < 0) y = Sketch.ROW - 1;
    if (y < 0) Sketch.game = false;
  }

  public void moveA() {
    x--;
//    if (x < 0) x = Sketch.COL - 1;
    if (x < 0) Sketch.game = false;
  }

  public void moveS() {
    y++;
//    if (y >= Sketch.ROW) y = 0;
    if (y >= Sketch.ROW) Sketch.game = false;
  }

  public void moveD() {
    x++;
//    if (x >= Sketch.COL) x = 0;
    if (x >= Sketch.COL) Sketch.game = false;
  }

  public void resetLine() {
    line = new boolean[]{true, true, true, true};
  }

  public void hiddeTop() {
    line[0] = false;
  }

  public void hiddeRig() {
    line[1] = false;
  }

  public void hiddeBot() {
    line[2] = false;
  }

  public void hiddeLef() {
    line[3] = false;
  }

  public void show() {
    int x0 = x * Sketch.SIZE;
    int x1 = x0 + Sketch.SIZE;
    int y0 = y * Sketch.SIZE;
    int y1 = y0 + Sketch.SIZE;

    p.noStroke();
    p.fill(255);
    p.rect(x0, y0, Sketch.SIZE, Sketch.SIZE);

    p.stroke(0);
    if (line[0]) p.line(x0, y0, x1, y0);
    if (line[1]) p.line(x1, y0, x1, y1);
    if (line[2]) p.line(x1, y1, x0, y1);
    if (line[3]) p.line(x0, y1, x0, y0);
  }
}
