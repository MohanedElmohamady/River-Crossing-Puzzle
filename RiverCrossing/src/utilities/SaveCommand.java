package utilities;

import controller.GameEngine;

public class SaveCommand implements Command {

	@Override
	public void execute() {
		GameEngine gameEngine = GameEngine.getInstance();
		SaveManager.execute(gameEngine.getGameState());
	}

}
