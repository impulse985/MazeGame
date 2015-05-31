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
package mazegame.gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import mazegame.MazeOptions;

/**
 * A dialog box shown when a user wants to generate a new maze. This dialog
 * allows the user to select the maze size, starting positions of player(s),
 * the goal position, and the maze generation algorithm. The dialog returns
 * a MazeOptions for use in the main MazeFrame to create the new maze.
 * @author Jeffery Thompson
 */
public class NewMazeDialog extends JDialog {
	MazeOptions options;
	public NewMazeDialog(JFrame f){
		super(f,"New Maze",true);
	}
	
	public MazeOptions showDialog(){
		this.setVisible(true);
		return options;
	}
	
}
