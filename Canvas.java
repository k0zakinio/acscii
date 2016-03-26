public class Canvas {
  int height, width;
  String[][] canvas;
  public Canvas(int h, int w) {
    this.height = h;
    this.width = w;
    this.generateCanvas();
  }

  public void generateCanvas() {
    this.canvas = new String[this.height][this.width];
    for(int row = 0; row < this.height; row++) {
      for(int col = 0; col < this.width; col++) {
	this.canvas[row][col] = " ";
      }
    }
  }

  public void renderCanvas() {
    String result = "";
    String vertRow = "";

    for(int col = 0; col < this.width + 2; col++) {
      vertRow += "-";
    }
    
    result += vertRow + "\n";
    
    for(int row = 0; row < this.height; row++) {
      String rowString = "|";
      for(int col = 0; col < this.width; col++) {
	rowString += this.canvas[row][col];
      }
      rowString += "|\n";
      result += rowString;
    }
    
    result += vertRow;
    System.out.println(result);
  }

  public void drawPoint(int x, int y) {
    canvas[y][x] = "x";
  }

  public void drawLine(int x1, int y1, int x2, int y2) {
    if(x1 == x2) {
      for(int row = y1; row <= y2; row++) {
	drawPoint(x1, row);
      }
    } else {
      for(int col = x1; col <= x2; col++) {
	drawPoint(col, y1);
      }
    }
  }

  public void drawSquare(int x1, int y1, int x2, int y2) {
    drawLine(x1, y1, x2, y1);
    drawLine(x1, y1, x1, y2);
    drawLine(x2, y1, x2, y2);
    drawLine(x1, y2, x2, y2);
  }

  public void parseInput(String input) {
    String[] splitInput = input.split(" ");
    String command = splitInput[0];
    if(command.equals("P")) {
      int x1 = Integer.parseInt(splitInput[1]);  
      int y1 = Integer.parseInt(splitInput[2]);
      this.drawPoint(x1, y1);
    }
    if(command.equals("L")) {
      int x1 = Integer.parseInt(splitInput[1]);  
      int y1 = Integer.parseInt(splitInput[2]);  
      int x2 = Integer.parseInt(splitInput[3]);  
      int y2 = Integer.parseInt(splitInput[4]);  
      this.drawLine(x1, y1, x2, y2);
    }
    if(command.equals("S")) {
      int x1 = Integer.parseInt(splitInput[1]);  
      int y1 = Integer.parseInt(splitInput[2]);  
      int x2 = Integer.parseInt(splitInput[3]);  
      int y2 = Integer.parseInt(splitInput[4]);  
      this.drawSquare(x1, y1, x2, y2);
    }
  }

  public static void main(String[] args) {
    Canvas test = new Canvas(5,10);
    test.renderCanvas();
    test.drawPoint(3,2);
    test.renderCanvas();
    test.drawLine(9, 0, 9, 4);
    test.renderCanvas();
    test.parseInput("P 0 1");
    test.renderCanvas();
    test.parseInput("L 0 0 9 0");
    test.renderCanvas();
    Canvas newTest = new Canvas(5, 10);
    newTest.parseInput("S 1 1 4 4");
    newTest.renderCanvas();
  }
}
