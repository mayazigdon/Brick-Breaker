package brickbreaker;
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

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer time;
	private int delay =8;
	
	private int playerX = 310;
	
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public GamePlay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		time = new Timer(delay,this);
		time.start();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1,692, 592);
		//bricks
		map.draw((Graphics2D)g);
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 1, 692, 4);
		g.fillRect(683, 0, 3, 592);
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		//the score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("score: "+score,585,30);
		//the ball
		g.setColor(Color.red);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if(totalBricks<=0) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("YOU Won! score: "+score, 230, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter To Restart", 230, 350);
		}
		
		if(ballPosY>570) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over.. score: "+score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter To Restart", 230, 350);

		}
		
		g.dispose();
		
	}

	

	public void actionPerformed(ActionEvent e) {
		time.start();
		if(play) {
			if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8)))
				ballYdir = -ballYdir;
			A: for(int i=0; i<map.map.length; i++) {
				for(int j=0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j* map.brickWidth+80;
						int brickY = i* map.brickHeight+50;
						
						Rectangle rect = new Rectangle(brickX,brickY,map.brickWidth,map.brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);
						
						if(ballRect.intersects(rect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=5;
							
							if(ballPosX + 19 <= rect.x || ballPosX +1 >= rect.x + rect.width)
								ballXdir = -ballXdir;
							else
								ballYdir = -ballYdir;
							break A;
						}
						
					}
				}
			}
			
			ballPosX+=ballXdir;
			ballPosY+=ballYdir;
			if(ballPosX<5 || ballPosX>665) {
				ballXdir = -ballXdir;
			}
			if(ballPosY<5)
				ballYdir=-ballYdir;
		}
		repaint();
	}

	

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 580) {
				playerX = 580;
			}
			else
				moveRight();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX<=5) {
				playerX = 5;
			}
			else
				moveLeft();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				ballPosX = 120;
				ballPosY = 350;
				ballXdir = -1;
				ballYdir = -2;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				repaint();
			}
		}
		
	}
	
	public void moveRight() {
		play = true;
		playerX+=20;
	}
	public void moveLeft() {
		play = true;
		playerX-=20;
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
