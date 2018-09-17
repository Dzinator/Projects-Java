package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;


/**
 * A program that allows the users to move a car with the mouse
 * @author Yanis
 *
 */
public class CarMover {

	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menu = new JMenuBar();
		JLabel l =new JLabel("Magic");
		menu.setOpaque(true);
        menu.setPreferredSize(new Dimension(200, 20));
		
		frame.setJMenuBar(menu);
		frame.add(new CarComponent(), BorderLayout.CENTER);
		frame.add(l, BorderLayout.NORTH);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
		
	}
	
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 400;
	
	

}
