import processing.core.PApplet;
import java.util.ArrayList;

public class Snake {

  private PApplet p;
  private ArrayList<Cell> body;

  public Snake(PApplet p) {
    this.p = p;
    spawn();
  }

  public int getX() {
    return body.get(0).getX();
  }

  public int getY() {
    return body.get(0).getY();
  }

  public int getSize() {
    return body.size();
  }

  public boolean colision() {
    for (int i = 1; i < getSize(); i++) {
      if (getX() == body.get(i).getX() && getY() == body.get(i).getY()) return true;
    }
    return false;
  }

  public boolean isOnTop(int x, int y) {
    for (Cell a : body) {
      if (x == a.getX() && y == a.getY()) return true;
    }
    return false;
  }

  public void eat() {
    for (int i = 0; i < 5; i++) {
      body.add(new Cell(p, body.get(getSize()-1).getX(), body.get(getSize()-1).getY()));
    }
  }

  private void updateTail() {
    for (int i = getSize() - 1; i > 0; i--) {
      body.get(i).setX(body.get(i-1).getX());
      body.get(i).setY(body.get(i-1).getY());
    }
  }

  public void moveW() {
    updateTail();
    body.get(0).moveW();
  }

  public void moveA() {
    updateTail();
    body.get(0).moveA();
  }

  public void moveS() {
    updateTail();
    body.get(0).moveS();
  }

  public void moveD() {
    updateTail();
    body.get(0).moveD();
  }

  public void spawn() {
    body = new ArrayList<>();
    body.add(new Cell(p, 1, 1));
  }

  private void updateLine() {
    for (Cell a : body) a.resetLine();
    for (int i = 0; i < getSize() - 1; i++) {
      int x = body.get(i).getX() - body.get(i+1).getX();
      if (x == 1) {
        body.get(i).hiddeLef();
        body.get(i+1).hiddeRig();
      } else if (x == -1) {
        body.get(i).hiddeRig();
        body.get(i+1).hiddeLef();
      }
      int y = body.get(i).getY() - body.get(i+1).getY();
      if (y == 1) {
        body.get(i).hiddeTop();
        body.get(i+1).hiddeBot();
      } else if (y == -1) {
        body.get(i).hiddeBot();
        body.get(i+1).hiddeTop();
      }
      if (x == 0 && y == 0) {
        body.get(i+1).hiddeRig();
        body.get(i+1).hiddeLef();
        body.get(i+1).hiddeBot();
        body.get(i+1).hiddeTop();
      }
    }
  }

  public void show() {
    updateLine();
    for (Cell a : body) a.show();
  }
}
