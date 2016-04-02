package app;
import java.util.Scanner;
import java.util.HashSet;

public class Canvas {
  int height, width;
  Cell[] cells;
  String[] undoStr;

  public void createCanvas(int w, int h) {
    this.height = h;
    this.width = w;
    generateCellList();
    storeCellVals();
  }

  public void clearCanvas() {
    for(Cell c: this.cells) {
      c.clear();
    }
  }

  public void undo() {
    for(int i = 0; i < this.cells.length; i++) {
      String oldVal = this.undoStr[i];
      this.cells[i].point(oldVal);
    }
  }

  private void storeCellVals() {
    this.undoStr = new String[this.height * this.width];
    for(int i = 0; i < this.cells.length; i++) {
      this.undoStr[i] = this.cells[i].val;
    }
  }

  // Find a cell based off x and y coordinates
  private Cell findCell(int x, int y) {
    for(Cell c: this.cells) {
      if(c.x == x && c.y == y) {
	return c;
      }
    }
    return null;
  }

  // Create a flat array, which assigns an x and y coordinates to a new Cell (based on it's index)
  private void generateCellList() {
    int size = this.width * this.height;
    Cell[] cellList = new Cell[size];

    for(int index = 0; index < cellList.length; index++) {
      int y = (int) (Math.floor(index/this.width));
      int x = (index - (y * this.width));
      cellList[index] = new Cell(x, y);
    }
    this.cells = cellList;
  }

  // Draws the canvas
  public String renderCanvas() {
    String result = "";
    String vertRow = "";

    for(int col = 0; col < this.width + 2; col++) {
      vertRow += "-";
    }

    result += vertRow + "\n";

    for(int y = 0; y < this.height; y++) {
      String rowString = "|";
      for(int x = 0; x < this.width; x++) {
	rowString += findCell(x, y).val;
      }
      rowString += "|\n";
      result += rowString;
    }

    result += vertRow;
    return result;
  }

  // Checks if the x or y coordinate is out of bounds, if not find the cell and draw a point
  public void drawPoint(int x, int y) {
    if(x < 0 || x >= this.width || y < 0 || y >= this.height) {
      int maxWidth = this.width - 1, maxHeight = this.height - 1;
      throw new IllegalArgumentException("Unable to draw point x:" + x + ", y:" + y + ".  Outside of canvas (max x:" + maxWidth  + ", max y: " + maxHeight + ")");
    }
    findCell(x,y).point("x");
  }

  // Checks if the line is horizontal or vertical, then draws the points between the start and end points
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

  // Draws lines to all four corners of the square (also draws rectangles)
  public void drawSquare(int x1, int y1, int x2, int y2) {
    drawLine(x1, y1, x2, y1);
    drawLine(x1, y1, x1, y2);
    drawLine(x2, y1, x2, y2);
    drawLine(x1, y2, x2, y2);
  }

  // Checks if a previous hash has been provided, identfies the neighbouring cells
  // runs through the neighbouring cells, filling providing they are fillable
  // and not already filled - recursively calls until no more cells to fill
  public void bucketFill(int x, int y, String filler, HashSet<Cell> hset) {
    HashSet<Cell> prevHash;
    if(hset != null) {
      prevHash = hset;
    } else {
      prevHash = new HashSet<Cell>();
    }
    int[][] neighbours = new int[][] {
        			      	{0,-1},// N
        			      	{1,-1},// NE
        			      	{1,0}, // E
        				{1,1}, // SE
        			      	{0,1}, // S
        			      	{-1,1},// SW
        			      	{-1,0},// W
        			      	{-1,-1}// NW
        			     };
    for(int[] n: neighbours) {
      Cell toFill = findCell(x + n[0], y + n[1]);
      if(toFill != null && canFill(toFill.x, toFill.y) && !prevHash.contains(toFill)) {
        prevHash.add(toFill);
	toFill.point(filler);
        bucketFill(toFill.x, toFill.y, filler, prevHash);
      }
    }
  }

  // Checks if the cell is out of bounds, or that that the cell does not contain a point "x"
  private boolean canFill(int x, int y) {
    if(x < this.width && x >= 0 && y < this.height && y >= 0 && !findCell(x,y).val.equals("x")) {
      return true;
    }
    return false;
  }

  // Interprets the user input to make calls to the methods, splits the user input string
  public void parseInput(String input) {
    String[] splitInput = input.split(" ");
    String command = splitInput[0];
    if(command.equals("C")) {
      int h = Integer.parseInt(splitInput[1]);
      int w = Integer.parseInt(splitInput[2]);
      createCanvas(h, w);
      System.out.println(renderCanvas());
    }
    if(command.equals("P")) {
      storeCellVals();
      int x1 = Integer.parseInt(splitInput[1]);
      int y1 = Integer.parseInt(splitInput[2]);
      drawPoint(x1, y1);
      System.out.println(renderCanvas());
    }
    if(command.equals("L")) {
      storeCellVals();
      int x1 = Integer.parseInt(splitInput[1]);
      int y1 = Integer.parseInt(splitInput[2]);
      int x2 = Integer.parseInt(splitInput[3]);
      int y2 = Integer.parseInt(splitInput[4]);
      drawLine(x1, y1, x2, y2);
      System.out.println(renderCanvas());
    }
    if(command.equals("S")) {
      storeCellVals();
      int x1 = Integer.parseInt(splitInput[1]);
      int y1 = Integer.parseInt(splitInput[2]);
      int x2 = Integer.parseInt(splitInput[3]);
      int y2 = Integer.parseInt(splitInput[4]);
      drawSquare(x1, y1, x2, y2);
      System.out.println(renderCanvas());
    }
    if(command.equals("B")) {
      storeCellVals();
      String filler = splitInput[3];
      int x1 = Integer.parseInt(splitInput[1]);
      int y1 = Integer.parseInt(splitInput[2]);
      bucketFill(x1, y1, filler, null);
      System.out.println(renderCanvas());
    }
    if(command.equals("E")) {
      storeCellVals();
      clearCanvas();
      System.out.println(renderCanvas());
    }
    if(command.equals("U")) {
      undo();
      System.out.println(renderCanvas());
    }
    if(command.equals("Q")) {
      System.exit(0);
    }
  }

  public static void main(String[] args) {
    Scanner keyboard = new Scanner(System.in);
    Canvas instance = new Canvas();
    while(true) {
      System.out.print("Enter command: ");
      instance.parseInput(keyboard.nextLine());
    }
  }
}