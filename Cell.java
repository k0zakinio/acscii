public class Cell {
  int x, y;
  char val;
  public Cell(int x, int y) {
    this.val = ' ';
    this.x = x;
    this.y = y;
  }

  public void point() {
    this.val = 'x';
  }


}
