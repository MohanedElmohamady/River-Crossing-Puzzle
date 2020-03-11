package model;

import java.awt.image.BufferedImage;

public class Wolf extends Animal {

	public Wolf() {
		super(loadImages(), 30.0, 10);
	}

	private static BufferedImage[] loadImages() {
		// TODO load images of wolf
		return null;
	}

	@Override
	public ICrosser makeCopy() {
		return new Wolf();
	}

	
}
