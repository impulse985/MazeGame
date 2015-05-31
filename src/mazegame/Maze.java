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
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * A class that stores and manipulates a maze. The maze is represented by a grid
 * of Cells with walls in between them. The maze is made by knocking down the
 * walls between the Cells to create a path between the start and end.
 * 
 * The maze uses a MazeGenerator to 
 * @author Jeff
 */
public class Maze {
	private Cell[][] grid;
	private int rowX; // Number of cells in a row
	private int colY; // Number of cells in a column
	
	private Cell start, goal;
	
	public static int CELL_WIDTH = 16;
	public static int CELL_HEIGHT = 16;
	
	public Maze(int r, int c){
		rowX = r;
		colY = c;
		grid = new Cell[rowX][colY];
		for(int i = 0; i < rowX; i++)
			for(int j = 0; j < colY; j++)
				grid[i][j] = new Cell(i, j);
		start = grid[0][0];
		goal = grid[rowX-1][colY-1];
		
		MazeGenerator.generateMaze(this);
	}
	
	public int getRows(){
		return rowX;
	}
	public int getCols(){
		return colY;
	}
	
	/**
	 * Gets the Cell of the maze at position (x,y). The x position is the 
	 * @param x x position of Cell
	 * @param y y position of Cell
	 * @return 
	 */
	public Cell getCell(int x, int y){
		if(x >= rowX || y >= colY || x < 0 || y < 0) return null;
		return grid[x][y];
	}
	
	public Cell getStartCell(){
		return start;
	}
	public void setStartCell(Cell c){
		start = c;
	}
	
	public Cell getGoalCell(){
		return goal;
	}
	public void setGoalCell(Cell c){
		goal = c;
	}
	
	public void paint(Graphics2D g){
		for(int i = 0; i < rowX; i++)
			for(int j = 0; j < colY; j++){
				if(grid[i][j] != null) grid[i][j].paint(g);
			}
	}
	
	public class Cell {
		Map<Direction, Boolean> wall;
		int posX;
		int posY;
		boolean visited;
		
		public Cell(int i, int j){
			wall = new EnumMap(Direction.class);
			for(Direction dir:Direction.values())
				wall.put(dir, Boolean.TRUE);
			posX = i;
			posY = j;
			visited = false;
		}
		
		public int getX(){
			return posX;
		}
		public int getY(){
			return posY;
		}
		public Map<Direction, Boolean> getWalls(){
			return wall;
		}
		
		public boolean hasNeighbor(Direction dir){
			return getNeighbor(dir) != null;
		}
		public Cell getNeighbor(Direction dir){
			if(dir == Direction.NORTH && posY > 0)
				return grid[posX][posY-1];
			if(dir == Direction.SOUTH && posY < colY-1)
				return grid[posX][posY+1];
			if(dir == Direction.EAST && posX < rowX-1)
				return grid[posX+1][posY];
			if(dir == Direction.WEST && posX > 0)
				return grid[posX-1][posY];
			return null;
		}
		
		public Map<Direction,Cell> getNeighbors() {
			Map<Direction,Cell> neighbors = new EnumMap(Direction.class);
			for(Direction dir : Direction.values())
				if(hasNeighbor(dir)) neighbors.put(dir, getNeighbor(dir));
			return neighbors;
		}
		/**
		 * Checks to see if a cell has in the given direction.
		 * @param dir Direction to check for wall
		 * @return true if there is a wall, false otherwise
		 */
		public boolean hasWall(Direction dir){
			return wall.get(dir);
		}
		
		public void breakWall(Direction dir){
			wall.put(dir, Boolean.FALSE);
			if(hasNeighbor(dir)){
				getNeighbor(dir).wall.put(dir.opposite(), Boolean.FALSE);
				getNeighbor(dir).visited = true;
			}
			visited = true;
		}
		
		public void paint(Graphics2D g){
			g.setColor(Color.white);
			g.fillRect(posX*CELL_WIDTH+1, posY*CELL_HEIGHT+1, 
					CELL_WIDTH, CELL_HEIGHT);
			g.setColor(Color.black);
			//draw checkerboard goal
			if(this == goal){
				for(int i = 0; i < 4; i++) 
					for(int j = 0; j < 4; j++)
						if(i%2==j%2)
							g.fillRect(posX*CELL_WIDTH+i*CELL_WIDTH/4, posY*CELL_HEIGHT+j*CELL_HEIGHT/4,
									CELL_WIDTH/4, CELL_HEIGHT/4);
			}
			if(wall.get(Direction.NORTH))
				g.drawLine(posX*CELL_WIDTH, posY*CELL_HEIGHT,
					(posX+1)*CELL_WIDTH, posY*CELL_HEIGHT);
			if(wall.get(Direction.EAST))
				g.drawLine((posX+1)*CELL_WIDTH, posY*CELL_HEIGHT,
					(posX+1)*CELL_WIDTH, (posY+1)*CELL_HEIGHT);
			if(wall.get(Direction.SOUTH))
				g.drawLine(posX*CELL_WIDTH, (posY+1)*CELL_HEIGHT,
					(posX+1)*CELL_WIDTH, (posY+1)*CELL_HEIGHT);
			if(wall.get(Direction.WEST))
				g.drawLine(posX*CELL_WIDTH, posY*CELL_HEIGHT,
					posX*CELL_WIDTH, (posY+1)*CELL_HEIGHT);
		}
	}
}
