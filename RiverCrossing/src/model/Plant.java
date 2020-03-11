package model;

import java.awt.image.BufferedImage;

public abstract class Plant extends Crosser {

	public Plant(BufferedImage[] images, double weight) {
		super(images, weight, 0);
	}

	
	@Override
	public boolean canSail() {
		return false;
	}	
	
}
