package model;

import java.util.Random;

public class MapImageModel {
	int currentImageIndex;
	Random rand;
	private String [] imageNames;
	public MapImageModel( String [] imageNames ){
		currentImageIndex = 0;
		rand = new Random(1);
		this.imageNames = imageNames;
	}
	public void nextMapImage(){
		currentImageIndex=Math.abs((currentImageIndex+Math.abs(rand.nextInt()%(imageNames.length-2))+1)%imageNames.length);
	}
	public String getMapImage(){
		return imageNames[currentImageIndex];
	}

}
