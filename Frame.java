import javax.swing.JFrame;

public class Frame extends JFrame {
	static int windowWidth = 1000;
	static int windowHeight = 500;

	static double gameTicSpeed = 10;

	Panel panel = new Panel();

	Frame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.add(panel);
		this.pack();
	}
}
