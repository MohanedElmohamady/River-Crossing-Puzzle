package model;

import java.awt.image.BufferedImage;

public abstract class Person extends Crosser {
	
	public Person(BufferedImage[] images, double weight) {
		super(images, weight,Integer.MAX_VALUE);
	}
	
	@Override
	public boolean canSail() {
		return true;
	}

}
