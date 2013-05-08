package be.fomp.carcassonne.view.panels;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import be.fomp.carcassonne.controller.GameController;
import be.fomp.carcassonne.exceptions.ActionNotAllowedException;
import be.fomp.carcassonne.exceptions.TileFactoryException;
import be.fomp.carcassonne.game.objects.AreaType;
import be.fomp.carcassonne.model.Player;
import be.fomp.carcassonne.model.beans.GameBean;
import be.fomp.carcassonne.model.beans.PlayerBean;
import be.fomp.carcassonne.model.beans.TileBean;
import be.fomp.carcassonne.utils.Color;
import be.fomp.carcassonne.utils.InnsCathedralsRuleset;
import be.fomp.carcassonne.utils.Ruleset;
import be.fomp.carcassonne.utils.TileUtils;
import be.fomp.carcassonne.utils.TradersBuildersRuleset;
import be.fomp.carcassonne.view.GameView;

@SuppressWarnings("serial")
public class GameViewControlPanel extends JPanel implements GameView {

	private GameBean model;
	private GameController controller;

	// GameState init
	private JPanel initControlPanel;
	// private JLabel gameNameLabel;
	// private JTextField gameNameField;

	private JButton editPlayersButton;
	private JComboBox rulesetSelector;
	private JButton startGameButton;

	// GameState running
	private JPanel runningControlPanel;
	private GameViewTilePanel tilePanel;
	private JButton rotateTileButton;
	protected JButton placeTileButton;
	private JButton placeFollowerButton;
	private JButton skipButton;

	// GameState ended
	private JPanel endedControlPanel;

	// keeps track of game status chenges in order to change layout
	private String currentGameStatus;

	public GameViewControlPanel(GameBean model, GameController controller) {
		this.model = model;
		this.controller = controller;
	}

	@Override
	public void createView() {
		this.setLayout(null);
		this.setSize(CONTROL_AREA_W, CONTROL_AREA_H);
		this.setLocation(CONTROL_AREA_X, CONTROL_AREA_Y);
		this.setBorder(BorderFactory.createTitledBorder("Controls"));
this.
		initControlPanel = new JPanel();
		initControlPanel.setLayout(null);
		initControlPanel.setLocation(0, 0);
		initControlPanel.setSize(this.getSize());
		initControlPanel.setBorder(this.getBorder());
		initControlPanel.setBackground(this.getBackground());

		// gameNameLabel = new JLabel();
		// gameNameLabel.setText("Game name: ");
		// gameNameLabel.setBounds(20,20,100,20);
		// initControlPanel.add(gameNameLabel);

		// gameNameField = new JTextField(model.getName());
		// gameNameField.setBounds(120,20,100,20);
		// initControlPanel.add(gameNameField);
		int buttonWidth = 120;
		int controlPanelButtonCenteringOffset = (CONTROL_AREA_W - buttonWidth) / 2;

		editPlayersButton = new GButton("Edit Players");
		editPlayersButton.setBounds(controlPanelButtonCenteringOffset, 20, buttonWidth, 30);
		initControlPanel.add(editPlayersButton);
		
		rulesetSelector = new JComboBox();
		rulesetSelector.addItem(new Ruleset());
		rulesetSelector.addItem(new InnsCathedralsRuleset());
		rulesetSelector.addItem(new TradersBuildersRuleset());
		rulesetSelector.addActionListener(this);
		
		rulesetSelector.setBounds(controlPanelButtonCenteringOffset-15, 60, buttonWidth+30, 30);
		initControlPanel.add(rulesetSelector);
		
		startGameButton = new GButton("Start Game");
		startGameButton.setBounds(controlPanelButtonCenteringOffset, 100, buttonWidth, 30);
		initControlPanel.add(startGameButton);

		this.add(initControlPanel);

		runningControlPanel = new JPanel();
		runningControlPanel.setLayout(null);
		runningControlPanel.setLocation(0, 0);
		runningControlPanel.setSize(this.getSize());
		runningControlPanel.setBorder(this.getBorder());
		runningControlPanel.setBackground(this.getBackground());

		tilePanel = new GameViewTilePanel(model.getActiveTile(), controller);
		tilePanel.setScale(2);
		tilePanel.setLocation((CONTROL_AREA_W - TILE_W * 2) / 2, 30);
		tilePanel.createView();
		tilePanel.addMouseListener(mouseListener);
		runningControlPanel.add(tilePanel);

		rotateTileButton = new GButton("rotate");
		rotateTileButton.setLocation(controlPanelButtonCenteringOffset,CONTROL_AREA_H - 80);
		runningControlPanel.add(rotateTileButton);

		placeTileButton = new GButton("place tile");
		placeTileButton.setLocation(controlPanelButtonCenteringOffset,CONTROL_AREA_H - 40);
		//placeTileButton.setEnabled(false);
		runningControlPanel.add(placeTileButton);

		placeFollowerButton = new GButton("place follower");
		placeFollowerButton.setLocation(controlPanelButtonCenteringOffset,CONTROL_AREA_H - 80);
		placeFollowerButton.setEnabled(false);
		runningControlPanel.add(placeFollowerButton);

		skipButton = new GButton("End Turn");
		skipButton.setLocation(controlPanelButtonCenteringOffset,CONTROL_AREA_H - 40);
		runningControlPanel.add(skipButton);

		this.add(runningControlPanel);

		endedControlPanel = new JPanel();
		endedControlPanel.setLayout(null);
		endedControlPanel.setLocation(0, 0);
		endedControlPanel.setSize(this.getSize());
		endedControlPanel.setBorder(this.getBorder());
		endedControlPanel.setBackground(this.getBackground());

		this.add(endedControlPanel);

		this.refresh();
	}

	@Override
	public void update(Observable o, Object arg) {
		GameBean updatedObject = (GameBean) o;

		System.out.println(o + "  ---  "+ arg);
		if (o != model) {
			model.deleteObserver(this);
			model = updatedObject;
			model.addObserver(this);
		}
		if (model.getActiveTile() != null) {
			TileBean activeTile = model.getActiveTile();
			activeTile.setFollowerLocation(model.getMap()
					.getActiveFollowerLocation());
			activeTile.setFollowerOwner(model.getPlayers()[model.getRound()
					% model.getPlayers().length].getColor());
			this.tilePanel.update((Observable) model.getActiveTile(), arg);
		}
		refresh();
	}

	@Override
	public void refresh() {
		String currentState = model.getState();

		System.out.println("Updating view to state= " + currentState);
		this.endedControlPanel.setVisible("ended".equalsIgnoreCase(currentState));
		placeTileButton.setEnabled(controller.tryPlaceTile());		
		
		this.initControlPanel.setVisible("init".equalsIgnoreCase(currentState));
		this.runningControlPanel.setVisible("running".equalsIgnoreCase(currentState));
		if (this.runningControlPanel.isVisible()) {
			PlayerBean playerBean = model.getPlayers()[model.getRound() % model.getPlayers().length];

			this.runningControlPanel.setBackground(Color.getColor(playerBean.getColor()));
			
			String playerState = playerBean.getState();
			this.placeTileButton.setVisible("placing_card".equalsIgnoreCase(playerState));
			//this.placeTileButton.setVisible(model.isTileValid());
			this.rotateTileButton.setVisible(placeTileButton.isVisible());
			this.placeFollowerButton.setVisible("placing_follower".equalsIgnoreCase(playerState));
			this.skipButton.setVisible(this.placeFollowerButton.isVisible());
		}		
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		try {
			if ("init".equalsIgnoreCase(model.getState())) {
				if (startGameButton == source) {
					System.out.println("StartGame");
					controller.doStartGame("Game");// gameNameField.getText());
				} else if (editPlayersButton == source) {
					System.out.println("EditPlayers");
					controller.doEditPlayers();
				} else if (rulesetSelector == source) {
					System.out.println("Select Rules");
					controller.setGameRuleset((Ruleset)rulesetSelector.getSelectedItem());
				}
			} else if ("running".equalsIgnoreCase(model.getState())) {
				if (rotateTileButton == source) {
					System.out.println("RotateTile");
					controller.doRotateTile();
				} else if (placeTileButton == source) {
					// TODO move validation to view layer
					controller.doPlaceTile();
				} else if (placeFollowerButton == source) {
					// TODO move validation to view layer
					controller.doPlaceFollower();
				} else if (skipButton == source) {
					// TODO move validation to view layer
					controller.doNextRound();
				}
			} else if ("ended".equalsIgnoreCase(model.getState())) {

			}
		} catch (ActionNotAllowedException ano) {
			JOptionPane.showMessageDialog(this, ano.getMessage(), null,	JOptionPane.INFORMATION_MESSAGE);
		} catch (TileFactoryException tfe) {
			JOptionPane.showMessageDialog(this, tfe.getMessage(), null,	JOptionPane.ERROR_MESSAGE);
		}
	}

	private class GButton extends JButton {
		GButton(String text) {
			super(text);
			int buttonWidth = 120;
			int buttonHeight = 30;
			setSize(buttonWidth, buttonHeight);
			addActionListener(GameViewControlPanel.this);
		}
	}

	private MouseListener mouseListener = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if ("placing_follower".equalsIgnoreCase(model.getPlayers()[model
					.getRound() % model.getPlayers().length].getState())) {
				Object source = e.getSource();
				GameViewTilePanel tilePanel;
				if (source instanceof GameViewTilePanel) {
					try {
						tilePanel = (GameViewTilePanel) source;
						int width = tilePanel.getWidth();
						int height = tilePanel.getHeight();
						int xPos = e.getX();
						int yPos = e.getY();

						int location = TileUtils.getCoordinateLocation(xPos,
								yPos, width, height);

						AreaType type = controller
								.doClickFollowerLocation(location);
						placeFollowerButton.setText("Claim " + type);
						placeFollowerButton.setEnabled(true);

					} catch (ActionNotAllowedException ana) {
						placeFollowerButton.setEnabled(false);
						JOptionPane.showMessageDialog(null, ana.getMessage(),
								null, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	};
}
