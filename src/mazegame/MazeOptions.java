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
 *
 * @author Jeffery Thompson
 */
public class MazeOptions {
	private int sizeX, sizeY;
	private Algorithm algorithm;
	
	private Point start;
	private Point goal;
	
	public MazeOptions(int x, int y){
		sizeX = x;
		sizeY = y;
		algorithm = Algorithm.DFS;
		start = new Point(0,0);
		goal = new Point(x-1,y-1);
	}
	public int getSizeX(){
		return sizeX;
	}
	public int getSizeY(){
		return sizeY;
	}
	
	public Algorithm getAlgorithm(){
		return algorithm;
	}
	public void setAlgorithm(Algorithm a){
		algorithm = a;
	}
	
	public Point getStart(){
		return start;
	}
	public void setStart(Point p){
		start = p;
		if(start.x >= sizeX) start.x = sizeX-1;
		if(start.x < 0) start.x = 0;
		if(start.y >= sizeY) start.y = sizeY-1;
		if(start.y < 0) start.y = 0;
	}
	
	public Point getGoal(){
		return goal;
	}
	public void setGoal(Point p){
		goal = p;
		if(goal.x >= sizeX) goal.x = sizeX-1;
		if(goal.x < 0) goal.x = 0;
		if(goal.y >= sizeY) goal.y = sizeY-1;
		if(goal.y < 0) goal.y = 0;
	}
	
	public enum Algorithm {
		DFS,PRIM;
		
		@Override
		public String toString(){
			switch(this){
				case DFS:
					return "Depth-first Search";
				case PRIM:
					return "Prim's Algorithm";
				default:
					return null;
			}
		}
	}
}
