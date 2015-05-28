/*
 * The MIT License
 *
 * Copyright 2015 Jeff Thompson.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package mazegame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Jeff
 */
public class Maze {
	Cell[][] grid;
	int rows;
	int cols;
	
	public static final int CELL_WIDTH = 15;
	public static final int CELL_HEIGHT = 15;
	
	public Maze(int r, int c){
		rows = r;
		cols = c;
		grid = new Cell[rows][cols];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				grid[i][j] = new Cell(i, j);
	}
	
	public int getRows(){
		return rows;
	}
	public int getCols(){
		return cols;
	}
	
	public class Cell {
		Map<Direction, Boolean> wall;
		int posX;
		int posY;
		public Cell(int i, int j){
			wall = new EnumMap(Direction.class);
			for(Direction dir:Direction.values())
				wall.put(dir, Boolean.TRUE);
			posX = i;
			posY = j;
		}
		
		/**
		 * Checks to see if a cell has a given wall.
		 * @param i
		 * @return 
		 */
		public boolean hasWall(Direction i){
			return wall.get(i);
		}
		
		public void breakWall(Direction i){
			wall.put(i, Boolean.FALSE);
		}
		
		public void paint(Graphics2D g){
			g.setColor(Color.white);
			g.fillRect(posX*CELL_WIDTH, posY*CELL_HEIGHT, 
					CELL_WIDTH, CELL_HEIGHT);
			g.setColor(Color.black);
			if(wall.get(Direction.NORTH))
				g.drawLine(posX*CELL_WIDTH, posY*CELL_HEIGHT,
					(posX+1)*CELL_WIDTH, posY*CELL_HEIGHT);
			if(wall.get(Direction.EAST))
				g.drawLine((posX+1)*CELL_WIDTH, posY*CELL_HEIGHT,
					(posX+1)*CELL_WIDTH, (posY+1)*CELL_HEIGHT);
			if(wall.get(Direction.SOUTH))
				g.drawLine(posX*CELL_WIDTH, (posY+1)*CELL_HEIGHT,
					(posX+1)*CELL_WIDTH, (posY+1)*CELL_HEIGHT);
			if(wall.get(Direction.NORTH))
				g.drawLine(posX*CELL_WIDTH, posY*CELL_HEIGHT,
					posX*CELL_WIDTH, (posY+1)*CELL_HEIGHT);
		}
	}
	
	public void paint(Graphics2D g){
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++){
				grid[i][j].paint(g);
			}
	}
}
