package utilities;

import java.util.List;

import model.ICrosser;
import strategies.ICrossingStrategy;

public class GameState {
		public int rafts;
		public List<ICrosser> leftBank;
		public List<ICrosser> rightBank;
		
		public GameState(int rafts, List<ICrosser> leftBank, List<ICrosser> rightBank) {
			this.rafts = rafts;
			this.leftBank = leftBank;
			this.rightBank = rightBank;
	}
}
