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

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import mazegame.Maze.Cell;
import mazegame.MazeOptions.Algorithm;
import mazegame.player.Path;
import mazegame.player.Path.PathPoint;

/**
 * A static utility class to generate a maze on a given Maze object. The current
 * algorithm used to create the maze is a randomized depth-first search (DFS),
 * which guarantees only one path between any two points. Thus, there is only
 * one possible path from start to finish. I plan on implementing more maze
 * generating algorithms, such as Prim's algorithm.
 * @author Jeffery Thompson
 */
public class MazeGenerator {
	
	public static void generateMaze(Maze m, Algorithm a){
		switch(a){
			case DFS:
				generateDFSMaze(m);
				break;
			case PRIM:
				generatePrimMaze(m);
				break;
			case WILSON:
				generateWilsonMaze(m);
				break;
			default:
				break;
		}
	}
	
	private static void generateDFSMaze(Maze m){
		Random rand = new Random();
		Stack<Cell> s = new Stack();
		
		Cell currCell = m.getCell(rand.nextInt(m.getOptions().getSizeX()),
				rand.nextInt(m.getOptions().getSizeY()));
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
	
	private static void generatePrimMaze(Maze m){
		System.out.println("Generating prim maze");
	}
	
	private static void generateWilsonMaze(Maze m){
		System.out.println("Generating wilson maze");
		Random rand = new Random();
		Path path = new Path();
		
		m.getCell(m.getOptions().getGoal()).visited = true;
		do{
			List<Cell> unvisited = m.getUnvisitedCells();
			System.out.println(unvisited.size());
			Cell currCell = unvisited.get(rand.nextInt(unvisited.size()));
			do{
				Direction dir = Direction.values()[rand.nextInt(4)];
				if(currCell.hasNeighbor(dir)) {
					path.push(currCell.getPos(),dir);
					currCell = currCell.getNeighbor(dir);
				}
			} while(!currCell.visited);
			do{
				PathPoint pp = path.pop();
				Cell c = m.getCell(pp.getPoint());
				if(!c.visited) c.breakWall(pp.getDirection());
				c.visited = true;
			} while(!path.isEmpty());
		} while(!m.getUnvisitedCells().isEmpty());
		
	}
}
