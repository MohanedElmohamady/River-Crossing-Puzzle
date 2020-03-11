package model;

import java.awt.image.BufferedImage;

public class Goat extends Animal{

	public Goat() {
		super(loadImages(), 10.0, 5);
	}

	private static BufferedImage[] loadImages() {
		return null;
	}

	@Override
	public ICrosser makeCopy() {
		// TODO load image of framer
		return new Goat();
	}
}
