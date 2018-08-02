package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.GameEngineClientStub;
import model.interfaces.GameEngine;
import view.MainFrame;

public class ExitButtonListener implements ActionListener, KeyListener, WindowListener {
	private GameEngine gameEngine;
	private MainFrame mainFrame;
	
	public ExitButtonListener (GameEngine gameEngine, MainFrame mainFrame) {
		this.gameEngine = gameEngine;
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame exit = new JFrame();
		int n = JOptionPane.showConfirmDialog(exit, "Exit the application?", "Exit application confirmation", JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			((GameEngineClientStub)gameEngine).exitGame();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// nothing to do here
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			mainFrame.getTableAndToolbarContainerPanel().getToolBar().clickExit();;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// nothing to do here
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// nothing to do here
	}

	@Override
	public void windowClosing(WindowEvent e) {
		mainFrame.getTableAndToolbarContainerPanel().getToolBar().clickExit();;
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// nothing to do here
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// nothing to do here
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// nothing to do here
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// nothing to do here
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// nothing to do here
	}
}
