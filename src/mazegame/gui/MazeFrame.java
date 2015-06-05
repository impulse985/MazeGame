/*
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
package mazegame.gui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import mazegame.Direction;
import mazegame.Maze;
import mazegame.player.Player;
import mazegame.player.PlayerList;

/**
 *
 * @author Jeff
 */
public class MazeFrame extends JFrame {
	MazePanel panel;
	Maze maze;
	
	JCheckBoxMenuItem blockView;
	JMenuItem increaseView, decreaseView;
	
	public MazeFrame(Maze m) {
		maze = m;
		panel = new MazePanel(m);
		this.setContentPane(panel);
		
		this.setJMenuBar(createMenuBar());
		this.addKeyListener(new KeyboardInput());
		panel.addKeyListener(new KeyboardInput());
		//this.setMinimumSize(new Dimension(300,300));
		this.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.requestFocus();
	}
        
	private JMenuBar createMenuBar(){
		JMenuBar bar = new JMenuBar();

		JMenu maze = new JMenu("Maze");
		MenuListener listener = new MenuListener();
		
		JMenuItem newMaze = new JMenuItem("New...");
		newMaze.setMnemonic(KeyEvent.VK_N);
		newMaze.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newMaze.addActionListener(listener);
		maze.add(newMaze);
		
		JMenuItem restart = new JMenuItem("Restart");
		restart.setMnemonic(KeyEvent.VK_R);
		restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		restart.addActionListener(listener);
		maze.add(restart);
		
		JMenu view = new JMenu("View");
		JMenuItem zoomIn = new JMenuItem("Zoom in");
		zoomIn.addActionListener(listener);
		view.add(zoomIn);
		
		JMenuItem zoomOut = new JMenuItem("Zoom out");
		zoomOut.addActionListener(listener);
		view.add(zoomOut);
		view.add(new JSeparator());
		
		blockView = new JCheckBoxMenuItem("Block view");
		blockView.addActionListener(listener);
		view.add(blockView);
		
		increaseView = new JMenuItem("Increase view distance");
		increaseView.addActionListener(listener);
		increaseView.setEnabled(false);
		view.add(increaseView);
		
		decreaseView = new JMenuItem("Decrease view distance");
		decreaseView.addActionListener(listener);
		decreaseView.setEnabled(false);
		view.add(decreaseView);
		
		bar.add(maze);
		bar.add(view);
		return bar;
	}
	
	public void resize(){
		panel.resize();
		this.pack();
	}
	
	public void winMessage(Player player){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");
		Duration time = player.getTime();
		String minutes = (time.toMinutes() < 10 ? "0": "") + time.toMinutes();
		String seconds = (time.getSeconds()%60 < 10 ? "0": "") + time.getSeconds()%60;
		JOptionPane.showMessageDialog(null, "You won! Time: "
				+ minutes + ":" + seconds);
	}
	
	public class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			switch(ae.getActionCommand()){
				case "New...":
					NewMazeDialog dialog = new NewMazeDialog(MazeFrame.this,maze.getOptions());
					if(dialog.showDialog() != null){
						maze = new Maze(dialog.getOptions());
						panel.setMaze(maze);
						PlayerList.setMaze(maze);
						panel.resize();
						panel.repaint();
						MazeFrame.this.pack();
					}
					break;
				case "Restart":
					panel.restart();
					panel.repaint();
					break;
				case "Zoom in":
					Maze.CELL_WIDTH *= 1.25;
					Maze.CELL_HEIGHT *= 1.25;
					resize();
					break;
				case "Zoom out":
					Maze.CELL_WIDTH /= 1.25;
					Maze.CELL_HEIGHT /= 1.25;
					resize();
					break;
				case "Block view":
					increaseView.setEnabled(blockView.isSelected());
					decreaseView.setEnabled(blockView.isSelected());
					panel.setBlockView(blockView.isSelected());	
					panel.repaint();
					break;
				case "Increase view distance":
					panel.increaseViewSize();
					break;
				case "Decrease view distance":
					panel.decreaseViewSize();
					break;
			}	
			
		}
	}
	
	public class KeyboardInput implements KeyListener {
			@Override
			public void keyTyped(KeyEvent ke) {
				
			}

			@Override
			public void keyPressed(KeyEvent ke) {
				switch(ke.getKeyCode()) {
					case KeyEvent.VK_UP:
						if(PlayerList.get(1)!=null){
							PlayerList.get(1).move(Direction.NORTH);
							break;
						}
					case KeyEvent.VK_W:
						PlayerList.get(0).move(Direction.NORTH);
						break;
					case KeyEvent.VK_DOWN:
						if(PlayerList.get(1)!=null){
							PlayerList.get(1).move(Direction.SOUTH);
							break;
						}
					case KeyEvent.VK_S:
						PlayerList.get(0).move(Direction.SOUTH);
						break;
					case KeyEvent.VK_LEFT:
						if(PlayerList.get(1)!=null){
							PlayerList.get(1).move(Direction.WEST);
							break;
						}
					case KeyEvent.VK_A:
						PlayerList.get(0).move(Direction.WEST);
						break;
					case KeyEvent.VK_RIGHT:
						if(PlayerList.get(1)!=null){
							PlayerList.get(1).move(Direction.EAST);
							break;
						}
					case KeyEvent.VK_D:
						PlayerList.get(0).move(Direction.EAST);
						break;
					default:
						break;
				}
				
				panel.repaint();
				for(Player p : PlayerList.getPlayers())
					if(!p.hasFinished() && p.checkWin())
						winMessage(p);
			}
			@Override
			public void keyReleased(KeyEvent ke) {}
			
		}
}
