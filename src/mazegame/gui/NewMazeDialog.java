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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mazegame.MazeOptions;
import mazegame.MazeOptions.Algorithm;
import mazegame.Point;

/**
 * A dialog box shown when a user wants to generate a new maze. This dialog
 * allows the user to select the maze size, starting positions of player(s),
 * the goal position, and the maze generation algorithm. The dialog returns
 * a MazeOptions for use in the main MazeFrame to create the new maze.
 * @author Jeffery Thompson
 */
public class NewMazeDialog extends JDialog {
	int sizeX, sizeY;
	Point start;
	Point goal;
	Algorithm algorithm;
	private final JSpinner spinnerX = new JSpinner();
	private final JSpinner spinnerY = new JSpinner();
	private final JSpinner startX = new JSpinner();
	private final JSpinner startY = new JSpinner();
	private final JSpinner goalX = new JSpinner();
	private final JSpinner goalY = new JSpinner();
	private final JComboBox algComboBox = new JComboBox(Algorithm.values());
	
	MazeOptions options;
	boolean cancelled;
	
	public NewMazeDialog(JFrame f){
		super(f, "New Maze", true);
		options = new MazeOptions(10,10);
		options.setAlgorithm(Algorithm.DFS);
		options.setStart(new Point(0,0));
		options.setGoal(new Point(options.getSizeX()-1,options.getSizeY()-1));
		
		createUserInterface();
	}
	
	public NewMazeDialog(JFrame f, MazeOptions o){
		super(f, "New Maze", true);
		options = o;
		
		createUserInterface();
	}
	
	private void createUserInterface(){
		
		DialogListener listener = new DialogListener();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		
		c.gridx = 0; c.gridy = 0;
		JPanel sizePanel = new JPanel();
		sizePanel.setBorder(new TitledBorder("Size"));
		sizePanel.add(new JLabel("X:"));
		spinnerX.setModel(new SpinnerNumberModel(options.getSizeX(),2,100,1));
		spinnerX.addChangeListener(listener);
		sizePanel.add(spinnerX);
		sizePanel.add(new JLabel("Y:"));
		spinnerY.setModel(new SpinnerNumberModel(options.getSizeY(),2,100,1));
		spinnerY.addChangeListener(listener);
		sizePanel.add(spinnerY);
		this.add(sizePanel,c);
		
		c.gridy = 1;
		JPanel startPanel = new JPanel();
		startPanel.setBorder(new TitledBorder("Start Position"));
		startPanel.add(new JLabel("X:"));
		System.out.println(options.getStart().getX());
		startX.setModel(new SpinnerNumberModel(options.getStart().getX(),0,options.getSizeX()-1,1));
		startX.addChangeListener(listener);
		startPanel.add(startX);
		startPanel.add(new JLabel("Y:"));
		startY.setModel(new SpinnerNumberModel(options.getStart().getY(),0,options.getSizeY()-1,1));
		startY.addChangeListener(listener);
		startPanel.add(startY);
		this.add(startPanel,c);
		
		c.gridy = 2;
		JPanel goalPanel = new JPanel();
		goalPanel.setBorder(new TitledBorder("Goal Position"));
		goalPanel.add(new JLabel("X:"));
		goalX.setModel(new SpinnerNumberModel(options.getGoal().getX(),0,options.getSizeX()-1,1));
		goalX.addChangeListener(listener);
		goalPanel.add(goalX);
		goalPanel.add(new JLabel("Y:"));
		goalY.setModel(new SpinnerNumberModel(options.getGoal().getY(),0,options.getSizeY()-1,1));
		goalY.addChangeListener(listener);
		goalPanel.add(goalY);
		this.add(goalPanel,c);
		
		c.gridy = 3;
		JPanel algPanel = new JPanel();
		algPanel.setBorder(new TitledBorder("Algorithm"));
		algPanel.add(new JLabel("Algorithm:"));
		algComboBox.setSelectedItem(options.getAlgorithm());
		algPanel.add(algComboBox);
		algComboBox.setActionCommand("Algorithm");
		algComboBox.addActionListener(listener);
		this.add(algPanel,c);
		
		c.gridy = 4;
		JPanel buttonPanel = new JPanel();
		JButton ok = new JButton("OK");
		ok.addActionListener(listener);
		buttonPanel.add(ok);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(listener);
		buttonPanel.add(cancel);
		this.add(buttonPanel,c);
		
		this.pack();
	}
	
	private void createOptionsFromDialog(){
		options = new MazeOptions((int)spinnerX.getValue(), (int)spinnerY.getValue());
		options.setAlgorithm((Algorithm)algComboBox.getSelectedItem());
		options.setStart(new Point((int)startX.getValue(),(int)startY.getValue()));
		options.setGoal(new Point((int)goalX.getValue(),(int)goalY.getValue()));
		
		updateSpinners();
	}
	
	private void updateSpinners(){
		int sx = (int)startX.getValue();
		int sy = (int)startY.getValue();
		
		if(sx >= options.getSizeX()) sx = options.getSizeX()-1;
		startX.setModel(new SpinnerNumberModel(sx,0,options.getSizeX()-1,1));
		if(sy >= options.getSizeY()) sy = options.getSizeY()-1;
		startY.setModel(new SpinnerNumberModel(sy,0,options.getSizeY()-1,1));
		
		int gx = (int)goalX.getValue();
		int gy = (int)goalY.getValue();
		
		if(gx >= options.getSizeX()) gx = options.getSizeX()-1;
		goalX.setModel(new SpinnerNumberModel(gx,0,options.getSizeX()-1,1));
		if(gy >= options.getSizeY()) gy = options.getSizeY()-1;
		goalY.setModel(new SpinnerNumberModel(gy,0,options.getSizeY()-1,1));
	}
	
	public MazeOptions showDialog(){
		this.setVisible(true);
		return options;
	}
	
	public MazeOptions getOptions(){
		return options;
	}
	
	private class DialogListener implements ActionListener, ChangeListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			switch(ae.getActionCommand()){
				case "Cancel":
					options = null;
					dispose();
					break;
				case "OK":
					createOptionsFromDialog();
					dispose();
					break;
				case "Algorithm":
					createOptionsFromDialog();
					break;
			}
		}

		@Override
		public void stateChanged(ChangeEvent ce) {
			createOptionsFromDialog();
			if(ce.getSource().equals(spinnerX)||ce.getSource().equals(spinnerY))
				updateSpinners();
			
		}
		
	}
	
}
