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
	
	Maze maze;
	Cell pos;
	
	Color playerColor;
	/**
	 * Creates a Player in the Maze m 
	 * @param m 
	 */
	public Player(Maze m){
		maze = m;
		m.addPlayer(this);
		pos = m.getStartCell();
		playerColor = Color.blue;
	}
	
	public Player(Maze m, int x, int y){
		maze = m;
		m.addPlayer(this);
		pos = m.getCell(x, y);
		playerColor = Color.blue;
	}
	
	public Color getColor(){
		return playerColor;
	}
	public void setColor(Color c){
		playerColor = c;
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
		if(pos.hasNeighbor(dir) && !pos.wall.get(dir))
		{
			pos = pos.getNeighbor(dir);
			return true;
		}
		return false;
	}
	
	public void restart(){
		pos = maze.getStartCell();
	}
	
	public void paint(Graphics2D g){
		g.setColor(playerColor);
		g.fillRect(pos.posX*Maze.CELL_WIDTH + Maze.CELL_WIDTH/4 ,
				   pos.posY*Maze.CELL_HEIGHT + Maze.CELL_HEIGHT/4,
				   Maze.CELL_WIDTH/2, Maze.CELL_HEIGHT/2);
	}
}
