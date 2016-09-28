package com.company.springer;
import java.util.ArrayList;

public class Cell {
	int x, y;
	String val;

	public Cell(int x, int y) {
		this.val = " ";
		this.x = x;
		this.y = y;
	}

	public void point(String val) {
		this.val = val;
	}

	public void clear() {
		this.val = " ";
	}
}
