import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
	private final static int WIDTH = 500;
	private final static int HEIGHT = 500;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(new MainPanel());
		frame.pack();
		frame.setVisible(true);
	}
	
	static class MainPanel extends JPanel {
		BufferedImage bi = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bi.getGraphics();
		final int spacing = 1;
		int x = Main.WIDTH/2;
		int y = Main.HEIGHT/2;
		int currentNum = 1;
		int direction = 0;
		int stepsUntilTurn = 1;
		int counter = 1;
		
		public MainPanel() {
			this.setBounds(0,0,Main.WIDTH, Main.HEIGHT);
			int currentNum = 1;
			int columns = Main.WIDTH / spacing;
			int rows = Main.HEIGHT / spacing;
			int total = columns*rows;
			g2d.setColor(Color.white);
			g2d.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
			g2d.setColor(Color.black);
			while(currentNum <= total) {
				currentNum = calculateSpiral();
			}
			repaint();
			
			try {
				ImageIO.write(bi, "png", new File("Ulam Spiral 500x500.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bi, 0, 0, Color.white, this);
		}
		
		private int calculateSpiral() {
			if(isPrime(currentNum)) {
				g2d.fillRect(x,y,1,1);				
			}
			switch (direction % 4) {
				case 0:
					x += spacing;
					break;
				case 1:
					y -= spacing;
					break;
				case 2:
					x -= spacing;	
					break;
				case 3:
					y += spacing;		
					break;
			}
			
			
			if(currentNum % stepsUntilTurn == 0){
				direction++;
				counter++;
				if(counter % 2 == 0) {
					stepsUntilTurn++;
				}
			}
			currentNum++;
			return currentNum;
		}
		
		private boolean isPrime(int number) {
			if(number <= 1) return false;
			for(int i = 2; i < number; i++) {
				if(number % i == 0) return false;
			}
			return true;
		}
	}

}
