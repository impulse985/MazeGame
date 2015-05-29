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

import java.util.Map;
import java.util.Random;
import java.util.Stack;
import mazegame.Maze.Cell;

/**
 *
 * @author Jeffery Thompson
 */
public class MazeGenerator {
	
	public static void generateMaze(Maze m){
		Random rand = new Random();
		Stack<Cell> s = new Stack();
		
		Cell currCell = m.getCell(rand.nextInt(m.getRows()),rand.nextInt(m.getCols()));
		do {
			Map<Direction, Cell> neighbors = currCell.getNeighbors();
			Direction dir = Direction.values()[rand.nextInt(4)];
			while((neighbors.get(dir) == null || neighbors.get(dir).visited) && !neighbors.isEmpty()) {
				neighbors.remove(dir);
				dir = Direction.values()[rand.nextInt(4)];
			}
			if(neighbors.isEmpty()){
				currCell = s.pop();
			}
			else {
				currCell.breakWall(dir);
				s.push(currCell);
				currCell = currCell.getNeighbor(dir);
			}
		} while(!s.empty() && currCell != null);
		
	}
}
