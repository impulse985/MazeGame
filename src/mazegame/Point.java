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
public class Point{
		int x, y;
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		
		public Point getNeighbor(Direction dir){
			switch(dir){
				case NORTH:
					return new Point(x,y-1);
				case SOUTH:
					return new Point(x,y+1);
				case EAST:
					return new Point(x+1,y);
				case WEST:
					return new Point(x-1,y);
				default:
					return null;
			}
		}
		
		@Override
		public int hashCode(){
			return (x <<16)+y;
		}
		
		@Override
		public boolean equals(Object o){
			if(o==this) return true;
			if(!o.getClass().equals(Point.class)) return false;
			Point p = (Point) o;
			return p.x == this.x && p.y == this.y;
		}
	}
