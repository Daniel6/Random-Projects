package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class main {
	public static void main(String[] args) {
		JFrame gameFrame;
		KeyListener keyListener;
		MouseListener mListener;
//		JPanel gamePanel;
		Grid game;
		game = new Grid();
		gameFrame = new JFrame();
//		gamePanel = new JPanel();
		mListener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				Point mouse = MouseInfo.getPointerInfo().getLocation();
				if (mouse.x > gameFrame.getLocation().x + 370 && mouse.x < gameFrame.getLocation().x + 430 && mouse.y > gameFrame.getLocation().y + 630 && mouse.y < gameFrame.getLocation().y + 660) {
					game.setState("Pregame");
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (game.getState().equals("Pregame")) {
					game.setState("Running");
				}
				if (e.getKeyChar() == 'w') {
					game.changeDir("UP");
				}
				if (e.getKeyChar() == 's') {
					game.changeDir("DOWN");
				}
				if (e.getKeyChar() == 'a') {
					game.changeDir("LEFT");
				}
				if (e.getKeyChar() == 'd') {
					game.changeDir("RIGHT");
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		};
		gameFrame.setSize(816, 838);
//		gamePanel.setSize(800, 800);
//		gamePanel.setBackground(Color.WHITE);
//		gameFrame.add(gamePanel, BorderLayout.EAST);
		gameFrame.add(game);
		gameFrame.setVisible(true);
		game.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.addKeyListener(keyListener);
		gameFrame.addMouseListener(mListener);
		
		Runnable gameProcess = new Runnable() {
			public void run() {
				while(true) {
					game.repaint();
					while (game.getState().equals("Pregame")) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// / Do Nothing
					}
					while (game.getState().equals("Running")) { // Game Loop
						game.tick();
						try {
							Thread.sleep(1000 / 30); // Run at 30 fps
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (game.getState().equals("Lose")) {
						game.repaint();
					}
				}
			}
		};
		
		Thread gameThread = new Thread(gameProcess);
		gameThread.start();
	}
}
