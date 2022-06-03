import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

//Sinan Hacisoyu
//119200060

public class Game extends JPanel implements ActionListener, MouseListener{
	public static JMenuBar menuBar;
	private JMenu gameMenu, specificationsMenu;
	private JLabel lifeText;
	private JMenuItem Start, Pause, Exit, ChangeBallSpeed, IncreaseDropletSpeed, DecreaseDropletSpeed;
	private Timer timer;
	private ArrayList<Droplet> circles;
	private int numberOfdroplets=3;
	private int lifeValue;
	private Thread addDropletThread;
	private boolean isStarted = false;
	private JButton button1;
	private int sumScore;
	Font font = new Font("TimesRoman", Font.BOLD, 20);
	Ball ball = new Ball(50,300,5,5);

	public void Game() {
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 40, 600, 600);

		// adding menubar
		menuBar = new JMenuBar();
	
		// adding menu items
		gameMenu = new JMenu("Game");
		specificationsMenu = new JMenu("Specifications");
		
		
		Start = new JMenuItem("Start");
		Pause = new JMenuItem("Pause");
		Exit = new JMenuItem("Exit");
		ChangeBallSpeed = new JMenuItem("Change Ball Speed");
		IncreaseDropletSpeed = new JMenuItem("Increase Droplet Speed");
		DecreaseDropletSpeed = new JMenuItem("Decrease Droplet Speed");

		lifeValue = 10;
		lifeText = new JLabel();
		lifeText.setText("Life: " + lifeValue);
		lifeText.setBounds(20,500,100,50);
		lifeText.setFont(font);
		
		menuBar.add(gameMenu);
		menuBar.add(specificationsMenu);
		gameMenu.add(Start);
		gameMenu.add(Pause);
		gameMenu.add(Exit);
		gameMenu.add(lifeText);
		add(lifeText);
		specificationsMenu.add(ChangeBallSpeed);
		specificationsMenu.add(IncreaseDropletSpeed);
		specificationsMenu.add(DecreaseDropletSpeed);
		
		//mouse listener
		addMouseListener(this);

		//actionlistener
		Start.addActionListener(this);
		Pause.addActionListener(this);
		Exit.addActionListener(this);
		ChangeBallSpeed.addActionListener(this);
		IncreaseDropletSpeed.addActionListener(this);
		DecreaseDropletSpeed.addActionListener(this);
		
		circles = new ArrayList<Droplet>(); // Creating arraylist for droplets of Droplet class
		for(int i=0; i<numberOfdroplets ;i++) {
			circles.add(new Droplet());
		}

		addDropletThread = new Thread(new Runnable() {//adding new droplet in each 2 seconds
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(2000);
						circles.add(new Droplet());
					}
				} catch (IllegalThreadStateException | InterruptedException e) {
					System.out.println("Error: " + e.getLocalizedMessage() + " " + e.getMessage() + " " + e.getCause());
				}
			}
		});

		timer= new Timer(50, this);
		panel.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(timer)) {
			if (lifeValue <= 0) {//Game is over
				timer.stop(); // asking reset and exit options when the game over
				JOptionPane.showMessageDialog(null, "Game Over...!!!   Your Score:"+ sumScore);
				int k = JOptionPane.showConfirmDialog(this, "Do you want to play again", " ", JOptionPane.YES_NO_OPTION);
				if(k == JOptionPane.NO_OPTION)
					System.exit(1);
				else if(k==JOptionPane.YES_OPTION)
				reset();
				timer.start();
			}
			ball();
			moveAndRemoveDroplets();
			detectCollision();
			lifeText.setText("Life: " + lifeValue);
		}
		else if(e.getSource().equals(Start))
			timer.start();
		else if(e.getSource().equals(Pause))
			timer.stop();
		else if(e.getSource().equals(Exit))
			exit();
		else if(e.getSource().equals(ChangeBallSpeed)) {
			timer.stop();
			ballVelocity();
			timer.start();
			}
		else if(e.getSource().equals(IncreaseDropletSpeed))
			DropletVelocityIncrease();
		else if(e.getSource().equals(DecreaseDropletSpeed))
			DropletVelocityDecrease();
		repaint();
	}

	// drawing all the droplets and the ball
	public void paint(Graphics g) { 
		super.paint(g);
		for(int i=0; i<circles.size() ;i++) {
			g.setColor(Color.blue);
			g.fillOval(circles.get(i).getX(), circles.get(i).getY(),30,70);
		}
		g.setColor(Color.red);
		g.fillOval(ball.getxBall(), ball.getyBall(), 50, 50);
	}

	// ball movement according to panel
	public void ball() {
		ball.setxBall(ball.getxBall() + ball.getVelocityBallX());
		ball.setyBall(ball.getyBall() + ball.getVelocityBallY());
		//making a coordination of ball (x and y) and prevent leaving the ball from border
		if(ball.getxBall()<0 || ball.getxBall() + 60 >= 600) // ...+60>= ... to not cross the border
			ball.setVelocityBallX(ball.getVelocityBallX() * -1); //bounce to the width
		if(ball.getyBall()<0 || ball.getyBall() + 60 >= 600) //...+60>= ... to not cross the border
			ball.setVelocityBallY(ball.getVelocityBallY() * -1); //bounce to the height
	}
	
	// giving new velocity to the ball
	private String veloBallx;
	private String veloBally;
	public void ballVelocity() {
		veloBallx=JOptionPane.showInputDialog(null,"Enter the x value");
		veloBally=JOptionPane.showInputDialog(null,"Enter the y value");
		int number1 = Integer.parseInt(veloBallx);
		int number2 = Integer.parseInt(veloBally);
		ball.setVelocityBallX(number1);
		ball.setVelocityBallY(number2);
		
	}

	// droplet movement and removement
	public void moveAndRemoveDroplets() { //If the thread to add the droplet has not started, starting it
		if (!isStarted) {
			isStarted = true;
			addDropletThread.start();
		}
		for (int i = 0; i< circles.size(); i++) {
			//removing the droplets that come out of the screen border from the list so that they are not drawn
			if (circles.get(i).getY() >= 520) {
				circles.remove(i);
				lifeValue -= 3; // decreasing lifevalue when droplet come out of the panel
			} else {
				circles.get(i).setY(circles.get(i).getY() + circles.get(i).getSpeed());
			}
		}
	}
	
	// Droplet velocity methods
	public void DropletVelocityIncrease() {
		Droplet.speedChangeQuantity+=1; // to make the velocity current of the new droplets which are came each two seconds
		for (int i = 0; i< circles.size(); i++) {
			circles.get(i).setSpeed(circles.get(i).getSpeed() + 1);
		}
	}
	public void DropletVelocityDecrease() {
		Droplet.speedChangeQuantity-=1; // to make the velocity current of the new droplets which are came each two seconds
		for (int i = 0; i< circles.size(); i++) {
			if(circles.get(i).getSpeed()>1) // avoid the droplets to go opposite direction
				circles.get(i).setSpeed(circles.get(i).getSpeed() - 1);
		}
	}

	// collision between ball and droplet
	public void detectCollision() { // Assign droplet and ball as rectangle according to size to use defined intersects method in java
		Rectangle circleRect = new Rectangle(ball.getxBall(), ball.getyBall(), 40, 50);
		for (int i=0; i<circles.size(); i++) {
			Droplet currentDroplet = circles.get(i);
			Rectangle currentRect = new Rectangle(currentDroplet.getX(), currentDroplet.getY(), 30, 70);
			if (circleRect.intersects(currentRect)) {  //removing the rectangles when they hit each other (just between ball and droplet rectangle)
				circles.remove(i);
				lifeValue -= 5;
			}
		}
	}

	public void exit() { //exit
		System.exit(1);
	}
	
	public void reset() { // reseting game with assign values
		ball = new Ball(50,300,5,5);
		lifeValue=10;
		lifeText.setText("Life: "+ lifeValue);
		circles = new ArrayList<Droplet>();
		for(int i=0; i<numberOfdroplets ;i++) {
			circles.add(new Droplet());
			sumScore=0;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//calculating the area of ball and droplet when user clicked and adding lifevalues
		if (e.getX() > ball.getxBall()&& e.getX() < ball.getxBall() + 50 && e.getY() > ball.getyBall() && e.getY() < ball.getyBall() + 50) {
			System.out.println("Touching the ball"); // to see in the console when you click the ball 
			lifeValue += 1;
			sumScore+=100; // when the user click the ball, get 100 score point
		} else {
			for (int i=0; i<circles.size(); i++) {
				int x = circles.get(i).getX();
				int y = circles.get(i).getY();
				if (e.getX() > x && e.getX() < x + 30 && e.getY() > y && e.getY() < y + 70) {
					lifeValue += 3;
					circles.remove(i); //remove droplet when user click the droplet
					sumScore+=200; // when the user click the droplet, get 200 score point
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
