package model;

import java.awt.image.BufferedImage;

public abstract class Crosser implements ICrosser {

	protected int eatingRank;
	protected double weight;
	protected BufferedImage[] images;
	protected String label;
	
	public Crosser(BufferedImage[] images, double weight, int eatingRank) {
		this.images = images;
		this.weight = weight;
		this.eatingRank = eatingRank;
	}
	
	@Override
	public BufferedImage[] getImages() {
		return images;
	}
	
	@Override
	public double getWeight() {
		return weight;
	}
	
	@Override
	public int getEatingRank() {
		return eatingRank;
	}
	
	@Override
	public void setLabelToBeShown(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabelToBeShown() {
		return label;
	}
}
