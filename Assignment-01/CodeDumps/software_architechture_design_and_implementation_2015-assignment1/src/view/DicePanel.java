package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DicePanel extends JPanel {
	private static final long serialVersionUID = 5943364367155114789L;
	JLabel dice1Label;
	JLabel dice2Label;
	Font font;
	
	DicePanel() {
		font = new Font("SansSerif", Font.BOLD, 48);
		setLayout(new GridLayout(1, 2));
		setBackground(new Color(0, 153, 0));
		
		dice1Label = new JLabel("1");
		dice1Label.setFont(font);
		dice1Label.setForeground(Color.WHITE);
		dice1Label.setHorizontalAlignment(SwingConstants.CENTER);
		add(dice1Label);
		
		dice2Label = new JLabel("1");
		dice2Label.setFont(font);
		dice2Label.setForeground(Color.WHITE);
		dice2Label.setHorizontalAlignment(SwingConstants.CENTER);
		add(dice2Label, BorderLayout.EAST);
	}
	
	public void setDice1(String value) {
		dice1Label.setText(value);
	}
	
	public void setDice2(String value) {
		dice2Label.setText(value);
	}
}
