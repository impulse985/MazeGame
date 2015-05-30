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

import java.awt.Color;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;
import mazegame.Maze.Cell;

/**
 * Represents a user who moves around in the maze. This class provides a way for
 * users to move around and solve the maze. This class includes a KeyListener
 * for moving around with the arrows or WASD keys.
 * 
 * Eventually, the goal is to make the mazes multi-player, with different
 * key bindings for each player. Thus, users can race each other to the goal.
 * @author Jeffery Thompson
 */
public class Player {
	
	private Maze maze;
	private Cell pos;
	private Path path;
	
	private Instant startTime, finishTime;
	
	private Color playerColor;
	
	private boolean finished = false;
	/**
	 * Creates a Player in the Maze m 
	 * @param m 
	 */
	public Player(Maze m){
		maze = m;
		pos = m.getStartCell();
		playerColor = Color.blue;
		path = new Path(playerColor, Color.yellow);
		startTime = Instant.now();
	}
	
	public Player(Maze m, int x, int y){
		maze = m;
		pos = m.getCell(x, y);
		playerColor = Color.blue;
	}
	
	/**
	 * Gets the Player's display color.
	 * @return Color used to draw this Player
	 */
	public Color getColor(){
		return playerColor;
	}
	public void setColor(Color c){
		playerColor = c;
	}
	
	/**
	 * Gets the Player's Duration of time in the maze. If the player has
	 * finished the maze, this returns the completion time. Otherwise, returns
	 * how long the Player has been in the maze so far.
	 * @return Duration of time in the maze
	 */
	public Duration getTime(){
		if(finished) return Duration.between(startTime, finishTime);
		else return Duration.between(startTime, Instant.now());
	}
	
	/**
	 * Attempts to move the player in the given Direction. If the move was
	 * successful,the player's position should now be in the Cell adjacent to 
	 * the current Cell in the given Direction, and the method returns true.
	 * Otherwise, if the neighboring cell has a wall or does not exist, the 
	 * position will remain the same, and the method returns false.
	 * 
	 * @param dir Direction to move
	 * @return true if the player moved successfully, false otherwise
	 */
	public boolean move(Direction dir){
		if(pos.hasNeighbor(dir) && !pos.wall.get(dir) && !finished)
		{
			path.add(pos,dir);
			pos = pos.getNeighbor(dir);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if the player has reached the goal cell of the maze.
	 * If the player has won, this method sets the player as finished, so they
	 * can no longer move and spam win messages.
	 * @return true if the Player is at the goal, false otherwise
	 */
	public boolean checkWin(){
		if(pos.equals(maze.getGoalCell()))
		{
			finishTime = Instant.now();
			finished = true;
			return true;
		}
		else return false;
	}
	
	public boolean hasFinished(){
		return finished;
	}
	
	/**
	 * Returns the player to the starting Cell of the maze.
	 */
	public void restart(){
		pos = maze.getStartCell();
		startTime = Instant.now();
	}
	
	public void paint(Graphics2D g){
		path.paint(g);
		g.setColor(playerColor);
		g.fillRect(pos.posX*Maze.CELL_WIDTH + 3,
				   pos.posY*Maze.CELL_HEIGHT + 3,
				   Maze.CELL_WIDTH -5, Maze.CELL_HEIGHT-5);
	}
}
