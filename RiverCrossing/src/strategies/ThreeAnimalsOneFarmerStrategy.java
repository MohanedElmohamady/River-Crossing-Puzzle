package strategies;

import java.util.ArrayList;
import java.util.List;

import model.Cabbage;
import model.Farmer;
import model.Goat;
import model.ICrosser;
import model.Wolf;

public class ThreeAnimalsOneFarmerStrategy implements ICrossingStrategy {
	@Override
	public List<ICrosser> getInitialCrossers() {
		List<ICrosser> crossers = new ArrayList<>();
		crossers.add(new Farmer());
		crossers.add(new Wolf());
		crossers.add(new Goat());
		crossers.add(new Cabbage());
		setLabelForCrossers(crossers);
		return crossers;
	}

	private void setLabelForCrossers(List<ICrosser> crossers) {
		for(ICrosser crosser: crossers) {
			crosser.setLabelToBeShown(String.valueOf(crosser.getEatingRank()));
		}
	}

	@Override
	public String[] getInstructions() {
		String[] instructions = {
				"1)You have 3 animals & one farmer",
				"2)No animal can eat the farmer",
				"3)Any animal can eat other animals which have eating rank less than its eating rank by 5 or less",
				"4)Try to deliver all the crossers from the left to the right bank" };
		return instructions;
	}

	@Override
	public boolean isValid(List<ICrosser> rightBankCrossers,
			List<ICrosser> leftBankCrossers, List<ICrosser> boatRiders) {
		boolean canAnyOneRaft = false;
		boolean oneCanEatAnother = false;
		for (ICrosser crosser : boatRiders) {
			if (crosser.canSail()) {
				canAnyOneRaft = true;
			}
		}
		if (!canAnyOneRaft)
			return false;
		oneCanEatAnother = checkRisk(leftBankCrossers)||checkRisk(rightBankCrossers);

		return !oneCanEatAnother;
	}

	private boolean checkRisk(List<ICrosser> stayers) {
		boolean oneCanEatAnother = false;
		for (ICrosser stayer : stayers) {
			for (ICrosser anotherstayer : stayers) {
				if (stayer == anotherstayer)
					continue;
				if (Math.abs(stayer.getEatingRank()
						- anotherstayer.getEatingRank()) <= 5)
					oneCanEatAnother = true;
			}
		}
		return oneCanEatAnother;
	}

}
