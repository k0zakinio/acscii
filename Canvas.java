import java.util.Scanner;
import java.util.HashSet;

public class Canvas {
  int height, width;
  String[][] canvas;
  Cell[] cells;

  public void createCanvas(int w, int h) {
    this.height = h;
    this.width = w;
    generateCellList();
  }

  public Cell findCell(int x, int y) {
    for(Cell c: this.cells) {
      if(c.x == x && c.y == y) {
	return c;
      }
    }
    return null;
  }

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

  public void renderCanvas() {
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
    System.out.println(result);
  }

  public void drawPoint(int x, int y) {
    if(x < 0 || x >= this.width || y < 0 || y >= this.height) {
      int maxWidth = this.width - 1, maxHeight = this.height - 1;
      throw new IllegalArgumentException("Unable to draw point x:" + x + ", y:" + y + ".  Outside of canvas (max x:" + maxWidth  + ", max y: " + maxHeight + ")");
    }
    findCell(x,y).point("x");
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

  public void bucketFill(int x, int y, String filler, HashSet<Cell> hset) {
    // CHECK IF A PREVIOUS ARRAY HAS BEEN SUPPLIED, IF NOT MAKE A NEW ONE
    HashSet<Cell> prevHash;
    if(hset != null) {
      prevHash = hset;
    } else {
      prevHash = new HashSet<Cell>();
    }
    // CHECK THE SURROUNDING CELLS FOR POSSIBLE FILLS
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

  private boolean canFill(int x, int y) {
    if(x < this.width && x >= 0 && y < this.height && y >= 0 && !findCell(x,y).val.equals("x")) {
      return true;
    }
    return false;
  }

  public void parseInput(String input) {
    String[] splitInput = input.split(" ");
    String command = splitInput[0];
    if(command.equals("C")) {
      int h = Integer.parseInt(splitInput[1]);
      int w = Integer.parseInt(splitInput[2]);
      this.createCanvas(h, w);
      this.renderCanvas();
    }
    if(command.equals("P")) {
      int x1 = Integer.parseInt(splitInput[1]);  
      int y1 = Integer.parseInt(splitInput[2]);
      this.drawPoint(x1, y1);
      this.renderCanvas();
    }
    if(command.equals("L")) {
      int x1 = Integer.parseInt(splitInput[1]);  
      int y1 = Integer.parseInt(splitInput[2]);  
      int x2 = Integer.parseInt(splitInput[3]);  
      int y2 = Integer.parseInt(splitInput[4]);  
      this.drawLine(x1, y1, x2, y2);
      this.renderCanvas();
    }
    if(command.equals("S")) {
      int x1 = Integer.parseInt(splitInput[1]);  
      int y1 = Integer.parseInt(splitInput[2]);  
      int x2 = Integer.parseInt(splitInput[3]);  
      int y2 = Integer.parseInt(splitInput[4]);  
      this.drawSquare(x1, y1, x2, y2);
      this.renderCanvas();
    }
    if(command.equals("B")) {
      String filler = splitInput[3];
      int x1 = Integer.parseInt(splitInput[1]);
      int y1 = Integer.parseInt(splitInput[2]);
      this.bucketFill(x1, y1, filler, null);
      this.renderCanvas();
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
