/*000
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
import java.util.ArrayList;
import java.util.List;
import java.util.EnumMap;
import java.util.Map;
import mazegame.MazeOptions.Algorithm;

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
	private MazeOptions options;
	
	public static int CELL_SIZE = 16;
	
	/**
	 * Creates a Maze object with the given MazeOptions. The size of the maze
	 * is given by the MazeOptions object, and the actual maze is automatically
	 * generated using the Algorithm from the MazeOptions object.
	 * @param o MazeOptions for the maze
	 */
	public Maze(MazeOptions o){
		this.options = o;
		grid = new Cell[options.getSizeX()][options.getSizeY()];
		for(int i = 0; i < options.getSizeX(); i++)
			for(int j = 0; j < options.getSizeY(); j++)
				grid[i][j] = new Cell(i, j);
		
		MazeGenerator.generateMaze(this, options.getAlgorithm());
	}
	
	/**
	 * Gets the MazeOptions object of this maze.
	 * @return MazeOptions of this maze.
	 */
	public MazeOptions getOptions(){
		return options;
	}
	
	/**
	 * Gets the Cell of the maze at position (x,y).
	 * @param x horizontal position of Cell
	 * @param y vertical position of Cell
	 * @return Cell at position (x,y)
	 */
	public Cell getCell(int x, int y){
		if(x >= options.getSizeX() || y >= options.getSizeY() || x < 0 || y < 0) return null;
		return grid[x][y];
	}
	/**
	 * Gets the Cell of the maze at Point p.
	 * @param p Point of the Cell
	 * @return Cell at Point p
	 */
	public Cell getCell(Point p){
		if(p.x >= options.getSizeX() || p.y >= options.getSizeY() || p.x < 0 || p.y < 0) return null;
		return grid[p.x][p.y];
	}
	
	/**
	 * Gets a List of all the unvisited Cells in the maze. This method checks
	 * each Cell's visited flag and adds it to the List.
	 * @return List of all unvisited Cells
	 */
	public List<Cell> getUnvisitedCells(){
		List<Cell> unvisited = new ArrayList();
		for(int i = 0; i < options.getSizeX(); i++)
			for(int j = 0; j < options.getSizeY(); j++)
				if(!grid[i][j].visited) unvisited.add(grid[i][j]);
		return unvisited;
	}
	
	/**
	 * Paints the maze using the Graphics2D object g. This method calls each
	 * Cell's paint method.
	 * @param g Graphics2D object to draw onto
	 */
	public void paint(Graphics2D g){
		for(int i = 0; i < options.getSizeX(); i++)
			for(int j = 0; j < options.getSizeY(); j++){
				if(grid[i][j] != null) grid[i][j].paint(g);
			}
	}
	
	/**
	 * A class for defining a single Cell in a maze. Each Cell has four walls in
	 * each cardinal direction. It also has a flag for storing whether or not it
	 * has been visited by the generation algorithm. Upon completion of a
	 * perfect maze generation algorithm, each Cell should have been visited.
	 */
	public class Cell {
		Map<Direction, Boolean> wall;
		Point pos;
		boolean visited;
		
		/**
		 * Creates a Cell object with its position set to (x,y). This Cell will
		 * have all four walls intact and be unvisited.
		 * @param x horizontal position of the Cell
		 * @param y vertical position of the Cell
		 */
		public Cell(int x, int y){
			wall = new EnumMap(Direction.class);
			for(Direction dir:Direction.values())
				wall.put(dir, Boolean.TRUE);
			pos = new Point(x,y);
			visited = false;
		}
		/**
		 * Creates a Cell object with its position set to Point p. This cell
		 * will have all four walls intact and be unvisited.
		 * @param p 
		 */
		public Cell(Point p){
			wall = new EnumMap(Direction.class);
			for(Direction dir:Direction.values())
				wall.put(dir, Boolean.TRUE);
			pos = p;
			visited = false;
		}
		
		/**
		 * Gets the position of the Cell as a Point.
		 * @return position of the Cell
		 */
		public Point getPos(){
			return pos;
		}
		
		/**
		 * Gets the walls of the Cell. The walls are represented as a map
		 * between the Direction of the wall and a boolean flag representing its
		 * presence. If the flag is set to true, the wall is still there.If the 
		 * flag is set to false, the wall has been broken down.
		 * @return Map of the Cell walls
		 */
		public Map<Direction, Boolean> getWalls(){
			return wall;
		}
		
		/**
		 * Checks to see if the Cell has a neighboring Cell in the given
		 * direction.
		 * @param dir Direction of possible neighbor
		 * @return true if there is a non-null neighboring Cell, false otherwise
		 */
		public boolean hasNeighbor(Direction dir){
			return getNeighbor(dir) != null;
		}
		/**
		 * Gets the neighboring Cell in the given Direction. If there is no
		 * neighbor Cell in that Direction, this method returns null.
		 * @param dir Direction of neighboring Cell
		 * @return Cell in given direction, null if there is no neighbor
		 */
		public Cell getNeighbor(Direction dir){
			if(dir == Direction.NORTH && pos.y > 0)
				return grid[pos.x][pos.y-1];
			if(dir == Direction.SOUTH && pos.y < options.getSizeY()-1)
				return grid[pos.x][pos.y+1];
			if(dir == Direction.EAST && pos.x < options.getSizeX()-1)
				return grid[pos.x+1][pos.y];
			if(dir == Direction.WEST && pos.x > 0)
				return grid[pos.x-1][pos.y];
			return null;
		}
		
		/**
		 * Gets all the neighbors of a cell. The neighboring Cells are stored
		 * in a Map between the Direction and the neighboring Cell itself. If
		 * there is no neighbor in a certain direction, it is not added to the
		 * Map
		 * @return all neighboring Cells
		 */
		public Map<Direction,Cell> getNeighbors() {
			Map<Direction,Cell> neighbors = new EnumMap(Direction.class);
			for(Direction dir : Direction.values())
				if(hasNeighbor(dir)) neighbors.put(dir, getNeighbor(dir));
			return neighbors;
		}
		/**
		 * Checks to see if a cell has a wall in the given direction.
		 * @param dir Direction to check for wall
		 * @return true if there is a wall, false otherwise
		 */
		public boolean hasWall(Direction dir){
			return wall.get(dir);
		}
		
		/**
		 * Removes a wall from this Cell and the Cell in the given Direction.
		 * This method is used to connect two Cells together. The walls between
		 * the two Cells are removed, creating a passage between them. This
		 * method also sets the visited flag to true for both Cells.
		 * @param dir Direction to break wall
		 */
		public void breakWall(Direction dir){
			wall.put(dir, Boolean.FALSE);
			if(hasNeighbor(dir)){
				getNeighbor(dir).wall.put(dir.opposite(), Boolean.FALSE);
				getNeighbor(dir).visited = true;
			}
			visited = true;
		}
		
		/**
		 * Checks if this Cell is equal to another Cell. This is done by checking
		 * their Point position's equality.
		 * @param o
		 * @return 
		 */
		@Override
		public boolean equals(Object o){
			if(this == o) return true;
			if(o.getClass() != Cell.class) return false;
			return ((Cell)o).pos.equals(this.pos);
		}
		
		/**
		 * Paints the Cell to the specified Graphics2D object. 
		 * @param g 
		 */
		public void paint(Graphics2D g){
			//Cell background
			g.setColor(Color.white);
			g.fillRect(pos.x*CELL_SIZE+1, pos.y*CELL_SIZE+1, 
					CELL_SIZE, CELL_SIZE);
			
			//draw checkerboard goal
			g.setColor(Color.black);
			if(this == getCell(options.getGoal())){
				for(int i = 0; i < 4; i++) 
					for(int j = 0; j < 4; j++)
						if(i%2==j%2)
							g.fillRect(pos.x*CELL_SIZE+i*CELL_SIZE/4, pos.y*CELL_SIZE+j*CELL_SIZE/4,
									CELL_SIZE/4, CELL_SIZE/4);
			}
			//draw corner pixels
			g.fillRect(pos.x*CELL_SIZE, pos.y*CELL_SIZE, 1, 1); //top left
			g.fillRect((pos.x+1)*CELL_SIZE-1, pos.y*CELL_SIZE, 1, 1); //top right
			g.fillRect((pos.x+1)*CELL_SIZE-1, (pos.y+1)*CELL_SIZE-1, 1, 1); //bottom right
			g.fillRect(pos.x*CELL_SIZE, (pos.y+1)*CELL_SIZE-1, 1, 1); //bottom left
			//draw walls
			if(wall.get(Direction.NORTH))
				g.drawLine(pos.x*CELL_SIZE, pos.y*CELL_SIZE,
					(pos.x+1)*CELL_SIZE-1, pos.y*CELL_SIZE);
			if(wall.get(Direction.EAST))
				g.drawLine((pos.x+1)*CELL_SIZE-1, pos.y*CELL_SIZE,
					(pos.x+1)*CELL_SIZE-1, (pos.y+1)*CELL_SIZE-1);
			if(wall.get(Direction.SOUTH))
				g.drawLine(pos.x*CELL_SIZE, (pos.y+1)*CELL_SIZE-1,
					(pos.x+1)*CELL_SIZE-1, (pos.y+1)*CELL_SIZE-1);
			if(wall.get(Direction.WEST))
				g.drawLine(pos.x*CELL_SIZE, pos.y*CELL_SIZE,
					pos.x*CELL_SIZE, (pos.y+1)*CELL_SIZE-1);
		}
	}
}
