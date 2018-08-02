package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.MainFrame;
import model.interfaces.GameEngine;

public class RollPlayerButtonListener implements ActionListener, KeyListener {
	private GameEngine gameEngine;
	private MainFrame mainFrame;
	
	public RollPlayerButtonListener(GameEngine gameEngine, MainFrame mainFrame) {
		this.gameEngine = gameEngine;
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// player roll
				gameEngine.rollPlayer(null, 1, 500, 20);
			}
		});
		
		thread.start();
		
		// change view immediately
		mainFrame.getTableAndToolbarContainerPanel().getToolBar().getRollPlayerButton().setEnabled(false);
		mainFrame.getMenu().getRollPlayerMenuItem().setEnabled(false);
		// disable quit and exit
		mainFrame.getTableAndToolbarContainerPanel().getToolBar().getQuitButton().setEnabled(false);
		mainFrame.getMenu().getQuitMenuItem().setEnabled(false);
		mainFrame.getTableAndToolbarContainerPanel().getToolBar().getExitButton().setEnabled(false);
		mainFrame.getMenu().getExitMenuItem().setEnabled(false);
		// disable add points
		mainFrame.getPlayerPanel().getAddPointsButton().setEnabled(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// nothing to do here
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			mainFrame.getTableAndToolbarContainerPanel().getToolBar().clickRollPlayer();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// nothing to do here
	}
}
