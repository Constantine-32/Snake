import processing.core.PApplet;

public class Sketch extends PApplet {

  static final int W = 400;
  static final int H = 400;
  static final int SIZE = 10;
  static final int COL = W / SIZE;
  static final int ROW = H / SIZE;
  static boolean game = true;

  private int ticks = 0;
  private int mykey = '?';
  private int bgColor = color(0);
  private Snake mySnake = new Snake(this);
  private Apple myApple = new Apple(this);

  public static void main(String[] args) {
    PApplet.main(new String[]{"Sketch"});
  }

  public void settings() {
    size(W, H);
  }

  public void setup() {
    frameRate(60);
    background(bgColor);
    mySnake.show();
    myApple.show();
  }

  public void draw() {
    if (game && ticks % 5 == 0) {

      if ((mySnake.getSize() == 1 || mykey != 's') && key == 'w') mykey = 'w';
      if ((mySnake.getSize() == 1 || mykey != 'd') && key == 'a') mykey = 'a';
      if ((mySnake.getSize() == 1 || mykey != 'w') && key == 's') mykey = 's';
      if ((mySnake.getSize() == 1 || mykey != 'a') && key == 'd') mykey = 'd';

      if (mykey == 'w') mySnake.moveW();
      if (mykey == 'a') mySnake.moveA();
      if (mykey == 's') mySnake.moveS();
      if (mykey == 'd') mySnake.moveD();

      if (mySnake.colision()) game = false;

      if (game) {
        if (mySnake.getX() == myApple.getX() && mySnake.getY() == myApple.getY()) {
          mySnake.eat();
          do { myApple.spawn(); } while (mySnake.isOnTop(myApple.getX(), myApple.getY()));
        }
        background(bgColor);
        mySnake.show();
        myApple.show();
//        gameInfoText();
      } else {
        gameOverText();
      }
    }
    ticks++;
  }

  public void keyPressed() {
    if (key == 'r') {
      game = true;
      mykey = '?';
      mySnake.spawn();
      myApple.spawn();
    }
  }

  private void gameInfoText() {
    textAlign(LEFT);
    fill(255, 255, 255);
    textSize(12);
    text("Apple coords: " + myApple.getX() + ", " + myApple.getY(), 4, 16);
    text("Snake coords: " + mySnake.getX() + ", " + mySnake.getY(), 4, 32);
    text("Snake length: " + mySnake.getSize(), 294, 390);
  }

  private void gameOverText() {
    textAlign(CENTER);
    fill(255, 255, 255);
    textSize(42);
    text("Game Over!", 200, 200);
    textSize(18);
    text("(Press 'R' to restart.)", 200, 220);
  }
}
