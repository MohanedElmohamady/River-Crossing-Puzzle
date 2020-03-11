package strategies;

import java.util.List;

import model.ICrosser;

public interface ICrossingStrategy {
	
	/**
	 * @param crossers which the user had selected to be moved to the other bank
	 * @param fromOtherCrossers is list denoting the crosses on the bank 
	 * where the boat currently stays 
	 * @return whether this move is valid 
	 * or not according to the rules
	 */
	public boolean isValid(List<ICrosser> rightBankCrossers,
			List<ICrosser> leftBankCrossers, List<ICrosser> boatRiders);
	
	
	public List<ICrosser> getInitialCrossers();
	public String[] getInstructions();
}
