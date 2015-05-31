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
package mazegame.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import mazegame.Maze;

/**
 * A class to maintain a list of all Players in the current maze.
 * @author Jeffery Thompson
 */
public class PlayerList {
	private static final Map<Integer, Player> player = new HashMap();
	private static int numPlayers = 0;
	public static void add(Player p){
		player.put(numPlayers++,p);
	}
	
	/**
	 * Gets the Player with the given player number.
	 * @param i player number
	 * @return player number i, or null if that player does not exist
	 */
	public static Player get(int i){
		if(!player.containsKey(i)) return null;
		return player.get(i);
	}
	
	public static Collection<Player> getPlayers(){
		return player.values();
	}
	public static void setMaze(Maze m){
		for(Integer k : player.keySet()){
			player.get(k).setMaze(m);
		}
	}
}
