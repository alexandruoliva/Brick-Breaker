	package brickBreaker;

	

import javax.swing.JFrame;

	public class Main {

	public static void main(String[] args) {
		JFrame obj = new JFrame(); // creating the frame
		obj.setBounds(10, 10, 700, 600);// setting the bounds
		obj.setTitle("Brick Breaker");// title
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(new Gameplay());
		//adding sound
		
		
	}

	}
