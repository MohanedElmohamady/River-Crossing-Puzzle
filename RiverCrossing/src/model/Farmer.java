package model;

import java.awt.image.BufferedImage;

public class Farmer extends Person {

	public Farmer() {
		super(loadImages(), 80);
	}

	private static BufferedImage[] loadImages() {
		// TODO load images of farmer
		return null;
	}
	
	@Override
	public ICrosser makeCopy() {
		return new Farmer();
	}

}
