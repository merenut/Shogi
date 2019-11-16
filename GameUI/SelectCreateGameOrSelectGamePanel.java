package GameUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectCreateGameOrSelectGamePanel extends JPanel{
	private JLabel title; 
	private JButton selectGameButton; 
	private JButton createGameButton; 
	public SelectCreateGameOrSelectGamePanel(GameGUI gameGUI) {
		// TODO Auto-generated constructor stub
		this.title = new JLabel("Welcome to Shogi! Choose an option");
		this.selectGameButton = new JButton("Select Game");
		this.createGameButton = new JButton("Create Game");
		this.selectGameButton.setName("Select Game");
		this.createGameButton.setName("Create Game");
		this.add(this.title); 
		this.add(this.selectGameButton); 
		this.add(this.createGameButton); 
		
		
	}
}
