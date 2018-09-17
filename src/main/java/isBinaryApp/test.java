package isBinaryApp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class test {

	public static boolean isBinary(String s) {
	      char b;
	      boolean result = true; 
	      for(int i = 0; i < s.length(); i++){
	    	 b = s.charAt(i);
	         if(b != '0' && b != '1'){
	        	 result = false;
	        	 break;
	         }
	      }
	      
	      if(result){   
	    	  System.out.println("That is binary");
	      }
	      else{
	    	  System.out.println("That is not binary.");
	      }
	      
	      return result;
	}

	public static void main(String[] args) {
	    //Scanner scanner = new Scanner( System.in );
	    //String s = scanner.nextLine();
	    
	    JFrame frame = new JFrame();;
		
		frame.setTitle("Binary Tester!");
		final JLabel title2 = new JLabel("Marvin: Please enter a binary number: ");
		JTextArea text = new JTextArea();
		final JLabel result = new JLabel("Result will appear here");
		JButton b = new JButton("Test!");
		
		b.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						//TODO //if(isBinary(text.getText())) result.setText("Binary indeed!");
						//else result.setText("Not Binary!");
					}
				});
		
		
		frame.setLayout(new BorderLayout());
		frame.add(title2, BorderLayout.NORTH);
		frame.add(text, BorderLayout.CENTER);
		frame.add(b, BorderLayout.EAST);
		frame.add(result, BorderLayout.SOUTH);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	    
	}

}
