import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements KeyListener {
	int ballSize = 7;
	int ballMove = 1;
	
	int paddleSpeed = 20;
	int playerLeftScore = 0;
	int playerRightScore = 0;

	boolean ballUp = true;
	boolean ballRight = true;

	JLabel middleLine;
	JLabel paddleLeft;
	JLabel paddleRight;
	JLabel ball;
	JLabel scoreLeft;
	JLabel scoreRight;

	JLabel winner;

	Timer timer;

	Dimension gameSize = new Dimension();

	Panel() {
//setting dimensions
		gameSize.height = Frame.windowHeight;
		gameSize.width = Frame.windowWidth;
//adding labels
		middleLine = new JLabel();
		middleLine.setBackground(Color.white);
		middleLine.setBounds(Frame.windowWidth / 2, 0, 3, Frame.windowHeight);
		middleLine.setOpaque(true);

		winner = new JLabel();
		winner.setBackground(Color.white);
		winner.setOpaque(false);

		paddleLeft = new JLabel();
		paddleLeft.setBounds(10 + ballMove, 300, 10, 100);
		paddleLeft.setOpaque(true);
		paddleLeft.setBackground(Color.white);

		paddleRight = new JLabel();
		paddleRight.setBounds(Frame.windowWidth - 20 - ballMove, 200, 10, 100);
		paddleRight.setOpaque(true);
		paddleRight.setBackground(Color.white);

		ball = new JLabel();

		scoreLeft = new JLabel("0");
		scoreLeft.setBounds(Frame.windowWidth / 2 - 50, 10, 50, 50);
		scoreLeft.setForeground(Color.white);
		scoreLeft.setFont(new Font("Georgia", Font.BOLD, 25));

		scoreRight = new JLabel("0");
		scoreRight.setBounds(Frame.windowWidth / 2 + 37, 10, 50, 50);
		scoreRight.setForeground(Color.white);
		scoreRight.setFont(new Font("Georgia", Font.BOLD, 25));

//panel variables
		this.setFocusable(true);
		this.setPreferredSize(gameSize);
		this.setBackground(Color.black);
		this.setLayout(null);
		this.addKeyListener(this);
		this.add(middleLine);
		this.add(paddleLeft);
		this.add(paddleRight);
		this.add(scoreLeft);
		this.add(scoreRight);

//adds ball
		newBall(this);
//adding timer
		timer = new Timer((int) Frame.gameTicSpeed, e -> timer(this));
		timer.setRepeats(true);
		timer.start();
	}

	public void timer(JPanel panel) {
		updateBall();
		checkCollision(panel);
	}

	public void checkCollision(JPanel panel) {
		Rectangle ballBounds = ball.getBounds();

		if (ballBounds.intersects(paddleRight.getBounds()) || ballBounds.intersects(paddleLeft.getBounds())) {
			ballRight = !ballRight;
			System.out.println("hit paddle");
		} else if (ball.getX() < paddleSpeed) {
			panel.remove(ball);
			playerRightScore++;
			timer.stop();
			newBall(panel);
			timer.start();
			System.out.println("out left");

		} else if (ball.getX() > Frame.windowWidth - 10) {
			panel.remove(ball);
			playerLeftScore++;
			timer.stop();
			newBall(panel);
			timer.start();
			System.out.println("out right");
		}

		if (ball.getY() > Frame.windowHeight - 10) {
			ballUp = !ballUp;
		} else if (ball.getY() < 0) {
			ballUp = !ballUp;
		}
		scoreLeft.setText(playerLeftScore + "");
		scoreRight.setText(playerRightScore + "");

	}

	public void updateBall() {

		if (playerLeftScore > 5 || playerRightScore > 5) {
			if (playerLeftScore > playerRightScore) {
				System.out.println("Player Left WINS");
				timer.stop();
			} else {
				System.out.println("Player Right WINS");
				timer.stop();
			}
		} else {
			if (ballRight) {
				ball.setLocation(ball.getX() + ballMove, ball.getY());
			} else {
				ball.setLocation(ball.getX() - ballMove, ball.getY());
			}

			if (ballUp) {
				ball.setLocation(ball.getX(), ball.getY() - ballMove);
			} else {
				ball.setLocation(ball.getX(), ball.getY() + ballMove);
			}
		}
	}

	public void newBall(JPanel panel) {
		ball = new JLabel();
		ball.setBounds(Frame.windowWidth / 2 - ballSize, Frame.windowHeight / 2 - ballSize, ballSize, ballSize);
		ball.setBackground(Color.white);
		ball.setOpaque(true);
		panel.add(ball);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (paddleLeft.getY() < 0) {
			paddleLeft.setLocation(paddleLeft.getX(), 1);
			;
		}
		if (paddleLeft.getY() > Frame.windowHeight - 110) {
			paddleLeft.setLocation(paddleLeft.getX(), Frame.windowHeight - 111);
			;
		}
		if (paddleRight.getY() < 0) {
			paddleRight.setLocation(paddleRight.getX(), 1);
			;
		}
		if (paddleRight.getY() > Frame.windowHeight - 110) {
			paddleRight.setLocation(paddleRight.getX(), Frame.windowHeight - 111);
			;
		}

		if (e.getKeyChar() == 'w') {
			paddleLeft.setLocation(paddleLeft.getX(), paddleLeft.getY() - paddleSpeed);
		} else if (e.getKeyChar() == 's') {
			paddleLeft.setLocation(paddleLeft.getX(), paddleLeft.getY() + paddleSpeed);
		} else if (e.getKeyCode() == 38) {
			paddleRight.setLocation(paddleRight.getX(), paddleRight.getY() - paddleSpeed);
		} else if (e.getKeyCode() == 40) {
			paddleRight.setLocation(paddleRight.getX(), paddleRight.getY() + paddleSpeed);
		}
		System.out.println(e.getKeyCode());

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
