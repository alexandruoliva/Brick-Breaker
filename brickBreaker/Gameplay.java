	package brickBreaker;

	import java.awt.Color;
	import java.awt.Font;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.Rectangle;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import javax.swing.Timer;
	
	import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

	//key listener indicate when the user is typing at the keyboard

	public  class  Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;

	private Timer timer;
	private int delay = 6;
	private int playerX = 310; // starting position for the slider

	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	File Coin =new File("coin.WAV");
	File Win = new File ("win.WAV");
	File Lost=new File("lost.WAV");
	
	private MapGenerator map;
	public Gameplay() {
		
		map =new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);// it will be the field that receives the keyboard
							// input
		setFocusTraversalKeysEnabled(false);// Sets whether focus traversal keys
											// are enabled for this Component.
		 timer = new Timer(delay, this);
		 timer.start();
		 

	}
	//coin sound 
	static void PlaySound (File Sound){
		try{
			Clip clip =AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			
			Thread.sleep(clip.getMicrosecondLength()/1000);
		}catch(Exception e)
		{
			
		}
	}
	//lost sound and win 
	static void PlayLost (File Sound){
		try{
			Clip clip =AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			
			//Thread.sleep(clip.getMicrosecondLength()/1000);
		}catch(Exception e)
		{
			
		}
	}
	
	
	
	public void paint(Graphics g) {

		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		
		//drawing map 
		map.draw((Graphics2D)g);

		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		
		//scores
		g.setColor(Color.magenta);
		g.setFont(new Font("allegro",Font.BOLD,25));
		g.drawString(""+score ,590,30);
		
		
		//scores
				g.setColor(Color.magenta);
				g.setFont(new Font("allegro",Font.BOLD,25));
				g.drawString("Score:" ,510,30);
				

		// the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		// the ball
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		
		
		if(ballposY>570){
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.white);
			g.setFont(new Font("allegro",Font.BOLD,30));
			g.drawString("Game Over, Score:"+score, 190, 300);
			
			
			
			PlayLost(Lost);
			
			g.setFont(new Font("allegro",Font.ITALIC,30));
			g.drawString("Press ENTER to restart", 230 , 350);
			
			
			
			
		}
		
		if(totalBricks <= 0)
		{	play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.white);
			g.setFont(new Font("allegro",Font.BOLD,30));
			g.drawString("You won, score: "+score, 190, 300);
			PlayLost(Win);
			g.setFont(new Font("allegro",Font.ITALIC,30));
			g.drawString("Press ENTER to restart", 230 , 350);
			
		}
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();//started the timer 
		
		if(play){
			
			
			if(new Rectangle(ballposX, ballposY,20 ,20).intersects (new Rectangle(playerX,550,100,8))){
				ballYdir= -ballYdir;
			}
			
			A:	for(int i=0;i<map.map.length;i++){
			for(int j =0 ; j<map.map[0].length;j++){//the first map is the one from the top , and the second one is from the MapGenerator class
				if(map.map[i][j]>0){
					int brickX= j*map.brickWidth +80;
					int brickY= i*map.brickHeight +50;
					int brickWidth =map.brickWidth;
					int brickHeight=map.brickHeight;
					
					Rectangle rect= new Rectangle (brickX,brickY ,brickWidth, brickHeight);
					Rectangle ballRect= new Rectangle ( ballposX,ballposY,20,20);
					Rectangle brickRect= rect;
					
					if(ballRect.intersects(brickRect)){//if the ball intersects the brick
						map.setBrickValue(0, i, j);
						totalBricks--;
						score +=5;
						PlaySound(Coin);
						
						if(ballposX+ 19 <= brickRect.x || ballposX +1>=brickRect.x +brickRect.width){//condition for collsion betwwen left and right part of the brick 
							ballXdir =-ballXdir; 
							
						}
						else{
							ballYdir= -ballYdir;
						}
					
						break A;
				}
			}	
		}
	}
		
		
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX <0){
				ballXdir = - ballXdir;
			}
			
			if(ballposY<0){
				ballYdir = -ballYdir;
			}
			
			if(ballposX > 670 ){
				ballXdir= -ballXdir;
			}
		}
		
		
		repaint();//calling the repaint method because we want to re-draw the GUI because it will change
		//we need to repaint because when we are incrementing the player's X positons and redraw the paddle 
		
	
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {// if the right key is pressed
			if (playerX >= 600) { // checks if the player is going further the
				playerX = 600;				// panel
				
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {// if the left key is pressed
			if (playerX < 10) {// checks if the player is going further the
				playerX = 10;			// panel
				
			} 
			else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_UP){
			level1();
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER){ //condition to restart the game and reinitialize the variables 
			if(!play){
			play=true;
			ballposX=120;
			ballposY=350;
			ballXdir=-1;
			ballYdir=-2;
			playerX=310;
			score=0;
			totalBricks=21;
			map=new MapGenerator(3,7);
			repaint();
			
			}
			
		}

	}

	public void moveRight() {// the player moves 20 pixels to right
		play = true;
		playerX += 20;
	}

	public void moveLeft() {// the player moves 20 pixels to left
		play = true;
		playerX -= 20;
	}
	
	public void level1(){
		play=true;
		delay--;
	}
	
	
	

 
}
