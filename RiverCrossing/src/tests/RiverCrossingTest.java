package tests;
/**
 * 
 * @author Paula B B Salib
 * 4th of May 2019, CS272 OOP spring 2018/2019
 * IRiverCrossing project JUnit test version 1.0.0
 */


import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import model.ICrosser;

import org.junit.Before;
import org.junit.Test;

import strategies.ICrossingStrategy;
import controller.GameEngine;
import controller.IRiverCrossingController;

public class RiverCrossingTest {

	private IRiverCrossingController instance;

	@Before
	public void initForTests() {
		instance = GameEngine.getInstance();
		instance.newGame(new AtLeastOneCanSailStartegy()); // to make sure it is fresh instance even if student used singleton 
	}

	@Test
	public void testNewGame() {
		assertEquals(0, instance.getNumberOfSails());
		assertEquals(false, instance.canRedo());
		assertEquals(false, instance.canUndo());
	}

	public void testResetGame() {
		instance.resetGame();
		assertEquals(0, instance.getNumberOfSails());
		assertEquals(false, instance.canRedo());
		assertEquals(false, instance.canUndo());
	}
	
	@Test
	public void testNewGameThenResetGame() {
		testNewGame();
		testResetGame();
	}

	@Test
	public void testCorrectNumberOfCrossersStartegy1Initially() {
		testCorrectNumberOfCrossers(3, 0);
	}

	public void testCorrectNumberOfCrossers(int leftNum, int rightNum) {
		assertEquals(leftNum, instance.getCrossersOnLeftBank().size());
		assertEquals(rightNum, instance.getCrossersOnRightBank().size());
	}

	@Test
	public void testInvalidMovementStartegy1() {
		List<ICrosser> crossers = new ArrayList<>();
		for (ICrosser x : instance.getCrossersOnLeftBank()) {
			if (!x.canSail())
				crossers.add(x);
		}
		assertEquals(false, instance.canMove(crossers, true));
		assertEquals(false, instance.canMove(crossers, false));
	}

	@Test
	public void testValidMovementStartegy1() {
		List<ICrosser> crossers = new ArrayList<>();
		for (ICrosser x : instance.getCrossersOnLeftBank()) {
			crossers.add(x);
			if (x.canSail())
				break;
		}
		assertEquals(true, instance.canMove(crossers, true));
		assertEquals(true, instance.canMove(crossers, false));
	}

	@Test
	public void testGameFlowStartegy1() {
		List<ICrosser> crossers = new ArrayList<>();
		for (ICrosser x : instance.getCrossersOnLeftBank()) {
			crossers.add(x);
			if (x.canSail())
				break;
		}
		// first move
		assertEquals(true, instance.canMove(crossers, true));
		instance.doMove(crossers, true);
		testCorrectNumberOfCrossers(2, 1);
		assertEquals(false, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(1, instance.getNumberOfSails());
		assertEquals(false, instance.isBoatOnTheLeftBank());
		// second move
		assertEquals(true, instance.canMove(crossers, false));
		instance.doMove(crossers, false);
		assertEquals(false, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(2, instance.getNumberOfSails());
		assertEquals(true, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(3, 0);
		// 3rd move
		crossers = new ArrayList<>(instance.getCrossersOnLeftBank());
		assertEquals(true, instance.canMove(crossers, true));
		instance.doMove(crossers, true);
		assertEquals(false, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(3, instance.getNumberOfSails());
		assertEquals(false, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(0, 3);
	}

	@Test
	public void testResetGameAfterGameFlowOfStrategy1() {
		testGameFlowStartegy1();
		instance.resetGame();
		assertEquals(0, instance.getNumberOfSails());
		assertEquals(false, instance.canRedo());
		assertEquals(false, instance.canUndo());
	}

	@Test
	public void testUndoThroughGameFlowOfStrategy1() {
		List<ICrosser> crossers = new ArrayList<>();
		for (ICrosser x : instance.getCrossersOnLeftBank()) {
			crossers.add(x);
			if (x.canSail())
				break;
		}
		// first move
		assertEquals(true, instance.canMove(crossers, true));
		instance.doMove(crossers, true);
		testCorrectNumberOfCrossers(2, 1);
		assertEquals(false, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(1, instance.getNumberOfSails());
		assertEquals(false, instance.isBoatOnTheLeftBank());
		// second move
		assertEquals(true, instance.canMove(crossers, false));
		instance.doMove(crossers, false);
		assertEquals(false, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(2, instance.getNumberOfSails());
		assertEquals(true, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(3, 0);
		// undo
		instance.undo();
		assertEquals(true, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(1, instance.getNumberOfSails());
		assertEquals(false, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(2, 1);
		// undo
		instance.undo();
		assertEquals(true, instance.canRedo());
		assertEquals(false, instance.canUndo());
		assertEquals(0, instance.getNumberOfSails());
		assertEquals(true, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(3, 0);
	}

	@Test
	public void testUndoRedoThroughGameFlowOfStrategy1() {
		List<ICrosser> crossers = new ArrayList<>();
		for (ICrosser x : instance.getCrossersOnLeftBank()) {
			crossers.add(x);
			if (x.canSail())
				break;
		}
		// first move
		assertEquals(true, instance.canMove(crossers, true));
		instance.doMove(crossers, true);
		testCorrectNumberOfCrossers(2, 1);
		assertEquals(false, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(1, instance.getNumberOfSails());
		assertEquals(false, instance.isBoatOnTheLeftBank());
		// second move
		assertEquals(true, instance.canMove(crossers, false));
		instance.doMove(crossers, false);
		assertEquals(false, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(2, instance.getNumberOfSails());
		assertEquals(true, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(3, 0);
		// undo
		instance.undo();
		assertEquals(true, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(1, instance.getNumberOfSails());
		assertEquals(false, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(2, 1);
		// undo
		instance.undo();
		assertEquals(true, instance.canRedo());
		assertEquals(false, instance.canUndo());
		assertEquals(0, instance.getNumberOfSails());
		assertEquals(true, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(3, 0);
		// redo
		instance.redo();
		assertEquals(true, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(1, instance.getNumberOfSails());
		assertEquals(false, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(2, 1);
		// redo
		instance.redo();
		assertEquals(false, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(2, instance.getNumberOfSails());
		assertEquals(true, instance.isBoatOnTheLeftBank());
		testCorrectNumberOfCrossers(3, 0);
	}

	@Test
	public void testSaveAndLoadVirtually() {
		List<ICrosser> crossers = new ArrayList<>();
		for (ICrosser x : instance.getCrossersOnLeftBank()) {
			crossers.add(x);
			if (x.canSail())
				break;
		}
		// first move
		assertEquals(true, instance.canMove(crossers, true));
		instance.doMove(crossers, true);
		testCorrectNumberOfCrossers(2, 1);
		assertEquals(false, instance.canRedo());
		assertEquals(true, instance.canUndo());
		assertEquals(1, instance.getNumberOfSails());
		assertEquals(false, instance.isBoatOnTheLeftBank());
		instance.saveGame();
		testResetGame();
		instance.loadGame();
		testCorrectNumberOfCrossers(2, 1);
		assertEquals(1, instance.getNumberOfSails());
		assertEquals(false, instance.isBoatOnTheLeftBank());

	}

	class AtLeastOneCanSailStartegy implements ICrossingStrategy {

		@Override
		public boolean isValid(List<ICrosser> rightBankCrossers,
				List<ICrosser> leftBankCrossers, List<ICrosser> boatRiders) {
			for (ICrosser crosser : boatRiders) {
				if (crosser.canSail())
					return true;
			}
			return false;
		}

		@Override
		public List<ICrosser> getInitialCrossers() {
			List<ICrosser> dummies = new ArrayList<ICrosser>();
			for (int i = 0; i < 3; i++) {
				dummies.add(new DummyCrosser(i == 0)); // first rider only can
				// sail
			}
			return dummies;
		}

		@Override
		public String[] getInstructions() {

			return null;
		}

	}

	class AllCanCross extends AtLeastOneCanSailStartegy {
		@Override
		public boolean isValid(List<ICrosser> rightBankCrossers,
				List<ICrosser> leftBankCrossers, List<ICrosser> boatRiders) {
			return !boatRiders.isEmpty();
		}
	}

	public static class DummyCrosser implements ICrosser {

		private Boolean canSail = false;

		public DummyCrosser() {
			// TODO Auto-generated constructor stub
		}

		public DummyCrosser(Boolean canSail) {
			this.canSail = canSail;
		}

		@Override
		public boolean canSail() {
			// TODO Auto-generated method stub
			return canSail;
		}

		@Override
		public double getWeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getEatingRank() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public BufferedImage[] getImages() {
			// TODO Auto-generated method stub
			return new BufferedImage[0];
		}

		@Override
		public ICrosser makeCopy() {
			// TODO Auto-generated method stub
			return new DummyCrosser(canSail);
		}

		@Override
		public void setLabelToBeShown(String label) {

		}

		@Override
		public String getLabelToBeShown() {
			// TODO Auto-generated method stub
			return "label";
		}

	}

}
