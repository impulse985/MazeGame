/*
 * The MIT License
 *
 * Copyright 2015 Jeffery Thompson.
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


/**
 * A class that stores options for a maze, including size, start and end points,
 * and a generation algorithm. A MazeOptions object must be constructed with
 * at least the maze's size. If you would like to change the other options,
 * you may then set the individual parameters.
 * @author Jeffery Thompson
 */
public class MazeOptions {
	private final int sizeX, sizeY;
	private Algorithm algorithm;
	
	private Point start;
	private Point goal;
	
	/**
	 * Creates a new MazeOptions with the specified maze size. The maze size
	 * is required. The other options may be set with their specific methods.
	 * The start Point defaults to the top left of the maze (0,0). The goal
	 * Point defaults to the bottom right of the maze (x-1,y-1). The default
	 * maze generation algorithm is DFS.
	 * @param x
	 * @param y 
	 */
	public MazeOptions(int x, int y){
		sizeX = x;
		sizeY = y;
		algorithm = Algorithm.DFS;
		start = new Point(0,0);
		goal = new Point(x-1,y-1);
	}
	/**
	 * Gets the horizontal size of the maze.
	 * @return horizontal size
	 */
	public int getSizeX(){
		return sizeX;
	}
	
	/**
	 * Gets the vertical size of the maze
	 * @return vertical size
	 */
	public int getSizeY(){
		return sizeY;
	}
	
	/**
	 * Gets the Algorithm used to generate the maze.
	 * @return generation Algorithm
	 */
	public Algorithm getAlgorithm(){
		return algorithm;
	}
	/**
	 * Sets the Algorithm used to generate the maze.
	 * @param a Algorithm to generate maze
	 */
	public void setAlgorithm(Algorithm a){
		algorithm = a;
	}
	
	/**
	 * Gets the starting Point of the maze. This is the recommended starting
	 * point. Players may individually set their own starting positions.
	 * @return start Point of maze
	 */
	public Point getStart(){
		return start;
	}
	/**
	 * Sets the starting Point of the maze. This is the recommended starting
	 * point. Players may individually set their own starting positions.
	 * @param p start Point of the maze
	 */
	public void setStart(Point p){
		start = p;
		if(start.x >= sizeX) start.x = sizeX-1;
		if(start.x < 0) start.x = 0;
		if(start.y >= sizeY) start.y = sizeY-1;
		if(start.y < 0) start.y = 0;
	}
	
	/**
	 * Gets the goal Point of the maze. This is where players will finish the 
	 * maze.
	 * @return goal Point of the maze
	 */
	public Point getGoal(){
		return goal;
	}
	/**
	 * Sets the goal Point of the maze. This is where players will finish the
	 * Maze.
	 * @param p 
	 */
	public void setGoal(Point p){
		goal = p;
		if(goal.x >= sizeX) goal.x = sizeX-1;
		if(goal.x < 0) goal.x = 0;
		if(goal.y >= sizeY) goal.y = sizeY-1;
		if(goal.y < 0) goal.y = 0;
	}
	
	/**
	 * An enumeration for the different maze generation algorithms.
	 */
	public enum Algorithm {
		/**
		 * A generation algorithm with a high "river" factor. Long, winding
		 * paths with little branching. These mazes take a long time to solve
		 * and will often have the user backtracking a lot from dead ends.
		 */
		DFS,
		/**
		 * A generation algorithm with a low "river" factor. Lots of short
		 * branches makes for a relatively easy maze.
		 */
		PRIM,
		/**
		 * A generation algorithm that generates all possible mazes with equal
		 * probability. Since mazes with a lower "river" factor are more common,
		 * this algorithm tends to create lower "river" mazes.
		 */
		WILSON;
		
		@Override
		public String toString(){
			switch(this){
				case DFS:
					return "Depth-first Search";
				case PRIM:
					return "Prim's Algorithm";
				case WILSON:
					return "Wilson's Algorithm";
				default:
					return null;
			}
		}
	}
}
