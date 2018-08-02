/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.MainFrame;
import model.interfaces.GameEngine;

/**
 * @author "Michael Vescovo - s3459317"
 *
 */
public class RollHouseButtonListener implements ActionListener, KeyListener {
	private GameEngine gameEngine;
	private MainFrame mainFrame;
	
	RollHouseButtonListener(GameEngine gameEngine, MainFrame mainFrame) {
		this.gameEngine = gameEngine;
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// roll house and calculate results
				gameEngine.calculateResult();
			}
		});
		
		thread.start();
		
		// change view immediately
		mainFrame.getTableAndToolbarContainerPanel().getToolBar().getRollHouseButton().setEnabled(false);
		mainFrame.getMenu().getRollHouseMenuItem().setEnabled(false);
		mainFrame.getTableAndToolbarContainerPanel().getToolBar().getQuitButton().setEnabled(false);
		mainFrame.getMenu().getQuitMenuItem().setEnabled(false);
		mainFrame.getTableAndToolbarContainerPanel().getToolBar().getExitButton().setEnabled(false);
		mainFrame.getMenu().getExitMenuItem().setEnabled(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// nothing to do here
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			mainFrame.getTableAndToolbarContainerPanel().getToolBar().getRollHouseButton().doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// nothing to do here
		
	}

}
