import javax.swing.JFrame;

//Sinan Hacisoyu

public class Test {
	
	public static void main(String[] args) {
		Game g = new Game();
		JFrame myFrame = new JFrame();
		g.Game();
		//new Game();
		myFrame.setJMenuBar(Game.menuBar);
		myFrame.setVisible(true);//make this visible
		myFrame.setSize(600,640);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit out of application
		myFrame.add(g);
		myFrame.setResizable(false);
	}
}
