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
import java.util.EnumMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Jeff
 */
public class Maze {
	private Cell[][] grid;
	private int rows;
	private int cols;
	
	private Player player;
	private Cell start, goal;
	
	private Instant startTime;
	
	public static int CELL_WIDTH = 15;
	public static int CELL_HEIGHT = 15;
	
	public Maze(int r, int c){
		rows = r;
		cols = c;
		grid = new Cell[rows][cols];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				grid[i][j] = new Cell(i, j);
		start = grid[0][0];
		goal = grid[rows-1][cols-1];
		player = new Player(this);
		MazeGenerator.generateMaze(this);
		startTime = Instant.now();
	}
	
	public int getRows(){
		return rows;
	}
	public int getCols(){
		return cols;
	}
	
	public Cell getCell(int x, int y){
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
	
	public void addPlayer(Player p){
		player = p;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void restart(){
		player.restart();
	}
	
	public void update(){
		if(player.pos == goal){
			Instant finishTime = Instant.now();
			Duration solveTime = Duration.between(startTime, finishTime);
			JOptionPane.showMessageDialog(null, "You finished the maze in "+solveTime.getSeconds()+" seconds.");
		}
	}
	
	public void paint(Graphics2D g){
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++){
				if(grid[i][j] != null) grid[i][j].paint(g);
			}
		player.paint(g);
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
		
		public boolean hasNeighbor(Direction dir){
			return getNeighbor(dir) != null;
		}
		public Cell getNeighbor(Direction dir){
			if(dir == Direction.NORTH && posY > 0)
				return grid[posX][posY-1];
			if(dir == Direction.SOUTH && posY < cols-1)
				return grid[posX][posY+1];
			if(dir == Direction.EAST && posX < rows-1)
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
		private void breakWall(){
		}
		
		public void paint(Graphics2D g){
			if( this == start) g.setColor(Color.green);
			else if( this == goal) g.setColor(Color.red);
			else g.setColor(Color.white);
			
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
			if(wall.get(Direction.WEST))
				g.drawLine(posX*CELL_WIDTH, posY*CELL_HEIGHT,
					posX*CELL_WIDTH, (posY+1)*CELL_HEIGHT);
		}
	}
}
