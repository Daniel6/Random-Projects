package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Grid extends JPanel{
	
	private int[][] grid;
	private Point head;
	private int snake_length = 3;
	private String dir = "UP";
	private String state = "Pregame";
	private int randx;
	private int randy;
	private int age;
	Random rand = new Random();
	private int score = 0;
	
	public Grid() {
		grid = new int[80][80];
		head = new Point();	
		head.x = rand.nextInt((70-10)+1)+10;
		head.y = rand.nextInt((70-10)+1)+10;
		grid[head.x][head.y] = 3;
		grid[head.x][head.y+1] = 2;
		grid[head.x][head.y+2] = 1;
		grid[rand.nextInt((77-2)+1)+2][rand.nextInt((77-2)+1)+2] = -1;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (state.equals("Running")) {
			paintGame(g);
		} else if (state.equals("Lose")) {
			paintLoseScreen(g);
		} else if (state.equals("Pregame")) {
			paintStartScreen(g);
		}
	}
	
	public void fillCell(int x, int y) {
		if (grid[x][y] == -1) {
			snake_length++;
			score += rand.nextInt(1200);
			while(true) {
				randx = rand.nextInt((77-2)+1)+2;
				randy = rand.nextInt((77-2)+1)+2;
				if (grid[randx][randy] < 1) {
					grid[randx][randy]= -1; 
					break;
				}
			}
		} else if (grid[x][y]> 0 ) {
			state = "Lose";
		}
		grid[x][y] = snake_length;
		head.x = x;
		head.y = y;
	}
	
	public void delCell(int x, int y) {
		grid[x][y]= 0; 
	}
	
	public void tick() {
		ageSnake();
		super.repaint(); //Repaint game canvas
		moveSnake();
	}
	
	public void ageSnake() {
		for (int x = 0; x < 80; x++) {
			for (int y = 0; y < 80; y++) {
				if (grid[x][y] > 0) {
					grid[x][y]-=1; 
				}
			}
		}
	}
	
	public void changeDir(String newDir) {
		switch(newDir) {
		case "UP":
			if (!dir.equals("DOWN") && !dir.equals("UP")) {
				dir = newDir;
			}
			break;
		case "DOWN":
			if (!dir.equals("UP") && !dir.equals("DOWN")) {
				dir = newDir;
			}
			break;
		case "LEFT":
			if (!dir.equals("RIGHT") && !dir.equals("LEFT")) {
				dir = newDir;
			}
			break;
		case "RIGHT":
			if (!dir.equals("LEFT") && !dir.equals("RIGHT")) {
				dir = newDir;
			}
			break;
		}
	}
	
	private void moveSnake() {
		try {
			switch (dir) {
			case "UP":
				fillCell(head.x, head.y-1);
				break;
			case "DOWN":
				fillCell(head.x, head.y+1);
				break;
			case "LEFT":
				fillCell(head.x-1, head.y);
				break;
			case "RIGHT":
				fillCell(head.x+1, head.y);
				break;
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			state = "Lose";
		}
	}
	
	private void paintGame(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 800, 800);
		
		for (int x = 0; x < 80; x++) {
			for (int y= 0; y < 80; y++) {
				if (grid[x][y] > 0) {
					g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
					g.fillRect(x*10, y*10, 10, 10);
				} else if (grid[x][y] == -1 ) {
					g.setColor(Color.RED);
					g.fillRect(x*10, y*10, 10, 10);
				}
			}
		}
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 800, 800);
		
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Font scoreFont = new Font("Serif",Font.ITALIC,32);
		graphics2d.setFont(scoreFont);
		graphics2d.setColor(Color.WHITE);
		String scoreString = "" + score;
		graphics2d.drawString(scoreString, 10, 30);
		
//		for (int i = 1; i <= 79; i++) { //Draw Vertical Lines
//			g.drawLine(i*10, 0, i*10, 800);
//		}
		
//		for (int i = 1; i <= 79; i++) { //Draw Horizontal Lines
//			g.drawLine(0, i*10, 800, i*10);
//		}
	}
	
	private void paintStartScreen(Graphics g) {
		paintGame(g);
		int stringWidth;
		FontMetrics fMetrics;
		g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(0, 0, 800, 800);
		
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Font titleFont = new Font("Serif",Font.ITALIC,60);
		Font byFont = new Font("Serif",Font.PLAIN,34);
		graphics2d.setFont(titleFont);
		graphics2d.setColor(Color.GRAY);
		String title = "Snake!";
		String by = "-Daniel";
		fMetrics = g.getFontMetrics(titleFont);
		stringWidth = fMetrics.stringWidth(title);
		graphics2d.drawString(title, 400 - (stringWidth / 2), 300);
		
		graphics2d.setFont(byFont);
		fMetrics = g.getFontMetrics(byFont);
		stringWidth = fMetrics.stringWidth(by);
		graphics2d.drawString(by, 400 - (stringWidth / 2), 330);
	}
	
	private void paintLoseScreen(Graphics g) {
		int stringWidth;
		FontMetrics fMetrics;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 800);
		Font loseTitleFont = new Font("Serif",Font.PLAIN,50);
		Font highScoreFont = new Font("Serif",Font.PLAIN,32);
		String loseMessage = "You Lose";
		String hsMessageString = "New High Score! " + score;
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		fMetrics = g.getFontMetrics(loseTitleFont);
		graphics2d.setFont(loseTitleFont);
		graphics2d.setColor(Color.WHITE);
		stringWidth = fMetrics.stringWidth(loseMessage);
		graphics2d.drawString(loseMessage, 400 - (stringWidth / 2), 400);
		
		fMetrics = g.getFontMetrics(highScoreFont);
		graphics2d.setFont(highScoreFont);
		graphics2d.setColor(Color.LIGHT_GRAY);
		stringWidth = fMetrics.stringWidth(hsMessageString);
		graphics2d.drawString(hsMessageString, 400 - (stringWidth / 2), 430);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(370, 610, 60, 30, 5, 5);
		
		fMetrics = g.getFontMetrics(new Font("Serif",Font.PLAIN,18));
		graphics2d.setFont(new Font("Serif",Font.PLAIN,18));
		graphics2d.setColor(Color.WHITE);
		stringWidth = fMetrics.stringWidth("Retry?");
		graphics2d.drawString("Retry?", 400 - (stringWidth / 2), 630);
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		if (this.state.equals("Lose") && state.equals("Pregame")) {
			reset();
		}
		this.state = state;
		super.repaint();
	}
	
	private void reset() {
		grid = new int[80][80];
		head = new Point();	
		head.x = rand.nextInt((70-10)+1)+10;
		head.y = rand.nextInt((70-10)+1)+10;
		grid[head.x][head.y] = 3;
		grid[head.x][head.y+1] = 2;
		grid[head.x][head.y+2] = 1;
		grid[rand.nextInt((77-2)+1)+2][rand.nextInt((77-2)+1)+2] = -1;
		score = 0;
	}
}
