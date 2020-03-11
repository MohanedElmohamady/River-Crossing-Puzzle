package model;

import java.awt.image.BufferedImage;

public class Cabbage extends Plant{

	public Cabbage() {
		super(loadImages(), 2.0);
	}

	private static BufferedImage[] loadImages() {
		// TODO load images of cabbage
		return null;
	}


	@Override
	public ICrosser makeCopy() {
		return new Cabbage();
	}

}
