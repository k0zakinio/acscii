package app.test;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import app.Canvas;

public class TestCanvas {
	Canvas canvas;

	@Before
	public void initialize() {
		canvas = new Canvas();
	}

	@Test
	public void testCanvasIsDefined() {
		assertNotNull("Should not be null", canvas);
	}

	@Test
	public void canCreateAnEmpty10x5Canvas() {
		canvas.createCanvas(10, 5);
		assertEquals(canvas.createRenderString(),
				"------------\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"------------");
	}

	@Test
	public void canDrawAPoint() {
		canvas.createCanvas(10, 5);
		canvas.drawPoint(1,1);
		assertEquals(canvas.createRenderString(),
				"------------\n" +
				"|          |\n" +
				"| x        |\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"------------");
	}

	@Test 
	public void canDrawVerticalLine() {
		canvas.createCanvas(10, 5);
		canvas.drawLine(0, 0, 0 ,4);
		assertEquals(canvas.createRenderString(),
				"------------\n" +
				"|x         |\n" +
				"|x         |\n" +
				"|x         |\n" +
				"|x         |\n" +
				"|x         |\n" +
				"------------");

	}

	@Test 
	public void canDrawHorizontalLine() {
		canvas.createCanvas(10, 5);
		canvas.drawLine(0, 0, 9 ,0);
		assertEquals(canvas.createRenderString(),
				"------------\n" +
				"|xxxxxxxxxx|\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"------------");
	} 

	@Test
	public void canDrawSquare() {
		canvas.createCanvas(10, 5);
		canvas.drawSquare(1,1,3,3);
		assertEquals(canvas.createRenderString(),
				"------------\n" +
				"|          |\n" +
				"| xxx      |\n" +
				"| x x      |\n" +
				"| xxx      |\n" +
				"|          |\n" +
				"------------");
	}

	@Test
	public void canBucketFill() {
		canvas.createCanvas(10, 5);
		canvas.drawSquare(1,1,6,3);
		canvas.bucketFill(0,0,"o", null);
		assertEquals(canvas.createRenderString(),
				"------------\n" +
				"|oooooooooo|\n" +
				"|oxxxxxxooo|\n" +
				"|ox    xooo|\n" +
				"|oxxxxxxooo|\n" +
				"|oooooooooo|\n" +
				"------------");
	}

	@Test
	public void canBucketFillInsideSquare() {
		canvas.createCanvas(10, 5);
		canvas.drawSquare(1,1,6,3);
		canvas.bucketFill(2,2,"M",null);
		assertEquals(canvas.createRenderString(),
				"------------\n" +
				"|          |\n" +
				"| xxxxxx   |\n" +
				"| xMMMMx   |\n" +
				"| xxxxxx   |\n" +
				"|          |\n" +
				"------------");
	}


	@Test
	public void canUndoLastAction() {
		canvas.createCanvas(10, 5);
		canvas.drawPoint(3,3);
		canvas.bucketFill(0,0,"P",null);
		canvas.undo();
		assertEquals(canvas.createRenderString(),
				"------------\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"|   x      |\n" +
				"|          |\n" +
				"------------");
	}

	@Test
	public void canClearTheCanvas() {
		canvas.createCanvas(10,5);
		canvas.bucketFill(0,0,"Z",null);
		canvas.clearCanvas();
		assertEquals(canvas.createRenderString(),
				"------------\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"|          |\n" +
				"------------");
	}
}
