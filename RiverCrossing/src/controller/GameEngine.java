package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.ICrosser;
import strategies.ICrossingStrategy;
import utilities.GameState;
import utilities.LoadCommand;
import utilities.SaveCommand;

public class GameEngine implements IRiverCrossingController{

	private int rafts;
	private ICrossingStrategy strategy;
	private List<ICrosser> leftBank;
	private List<ICrosser> rightBank;
	private Stack<List<ICrosser>[]> undoStack;
	private Stack<List<ICrosser>[]> redoStack;
	private static GameEngine instance;
	private SaveCommand saveCommand = new SaveCommand();
	private LoadCommand loadCommand = new LoadCommand();
	
	public static GameEngine getInstance() {
		if (instance == null) {
			instance = new GameEngine();
		}
		return instance;
	}
	
	private GameEngine() {
		init();
	}
	
	private void init() {
		rafts = 0;
		undoStack = new Stack<List<ICrosser>[]>();
		redoStack = new Stack<List<ICrosser>[]>();
		rightBank = new ArrayList<>();
	}

	@Override
	public void doMove(List<ICrosser> crossers, boolean fromLeftToRightBank) {
		rafts++;
		takeUndoSnapShot();
		resetRedoStack();
		if (fromLeftToRightBank) {
			for(ICrosser crosser: crossers) {
				leftBank.remove(crosser);
				rightBank.add(crosser);
			}
		} else {
			for(ICrosser crosser: crossers) {
				rightBank.remove(crosser);
				leftBank.add(crosser);
			}
		}
	}

	private void resetRedoStack() {
		redoStack.clear();
	}

	@SuppressWarnings("unchecked")
	private void takeUndoSnapShot() {
		List<ICrosser> leftCopy = cloneFrom(leftBank);
		List<ICrosser> rightCopy = cloneFrom(rightBank);
		List<ICrosser>[] undoPackage = ((List<ICrosser>[]) new List[2]);
		undoPackage[0] = leftCopy;
		undoPackage[1] = rightCopy;
		undoStack.push(undoPackage);
	}

	private List<ICrosser> cloneFrom(List<ICrosser> list) {
		List<ICrosser> copy = new ArrayList<>();
		
		for(ICrosser crosser: list){
			copy.add(crosser.makeCopy());
		}
		return copy;
	}

	@Override
	public void undo() {
		takeRedoSnapShot();
		List<ICrosser>[] undoPackage = undoStack.pop();
		leftBank = undoPackage[0];
		rightBank = undoPackage[1];
		rafts--;
	}

	@SuppressWarnings("unchecked")
	private void takeRedoSnapShot() {
		List<ICrosser> leftCopy = cloneFrom(leftBank);
		List<ICrosser> rightCopy = cloneFrom(rightBank);
		List<ICrosser>[] redoPackage = ((List<ICrosser>[]) new List[2]);
		redoPackage[0] = leftCopy;
		redoPackage[1] = rightCopy;
		redoStack.push(redoPackage);
	}

	@Override
	public void redo() {
		takeUndoSnapShot();
		List<ICrosser>[] undoPackage = redoStack.pop();
		leftBank = undoPackage[0];
		rightBank = undoPackage[1];
		rafts++;
	}

	@Override
	public boolean canUndo() {
		return !undoStack.isEmpty();
	}

	@Override
	public boolean canRedo() {
		return !redoStack.isEmpty();
	}

	@Override
	public String[] getInstructions() {
		return strategy.getInstructions();
	}

	@Override
	public void newGame(ICrossingStrategy gameStrategy) {
		strategy = gameStrategy;
		leftBank = strategy.getInitialCrossers();
		init();
	}

	@Override
	public List<ICrosser> getCrossersOnRightBank() {
		return rightBank;
	}

	@Override
	public List<ICrosser> getCrossersOnLeftBank() {
		return leftBank;
	}

	@Override
	public void resetGame() {
		newGame(strategy);
	}

	@Override
	public boolean isBoatOnTheLeftBank() {
		return rafts%2==0;
	}

	@Override
	public int getNumberOfSails() {
		return rafts;
	}

	@Override
	public boolean canMove(List<ICrosser> crossers, boolean fromLeftToRightBank) {
		if (fromLeftToRightBank) {
			ArrayList<ICrosser> leftCopy = new ArrayList<>();
			for (ICrosser x: leftBank){
				if (!crossers.contains(x)){
					leftCopy.add(x);
				}
			}
			return strategy.isValid(rightBank, leftCopy, crossers);
		} else {
			ArrayList<ICrosser> rightCopy = new ArrayList<>();
			for (ICrosser x: rightBank){
				if (!crossers.contains(x)){
					rightCopy.add(x);
				}
			}
			return strategy.isValid(rightCopy, leftBank, crossers);	
		}
		
	}
	
	@Override
	public void saveGame() {
		saveCommand.execute();
	}

	@Override
	public void loadGame() {
		loadCommand.execute();
	}

	public GameState getGameState() {
		return new GameState(rafts, leftBank, rightBank);
	}
	
	public void setGameState(GameState savedInfo) {
		rafts = savedInfo.rafts;
		leftBank = savedInfo.leftBank;
		rightBank = savedInfo.rightBank;
	}
	

}
