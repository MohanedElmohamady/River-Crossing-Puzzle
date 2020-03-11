package model;

import java.awt.image.BufferedImage;

public abstract class Animal extends Crosser {

	public Animal(BufferedImage[] images, double weight, int eatingRank) {
		super(images, weight, eatingRank);
	}

	protected int eatingRank;
	
	@Override
	public boolean canSail() {
		return false;
	}	
	
}
