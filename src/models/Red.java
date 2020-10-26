package models;

import java.util.Arrays;

public class Red {
	
	private int[] red, broadcast, mascara;
	private int subred;
	
	public Red(){
		super();
		red = new int[4];
		broadcast = new int[4];
		mascara = new int[4];
	}
	
	public Red(int[] red, int[] broadcast, int[] mascara) {
		this.red = red;
		this.broadcast = broadcast;
		this.mascara = mascara;
	}

	public int[] getRed() {
		return red;
	}

	public void setRed(int[] red) {
		this.red = red;
	}

	public int[] getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(int[] broadcast) {
		this.broadcast = broadcast;
	}

	public int[] getMascara() {
		return mascara;
	}

	public void setMascara(int[] mascara) {
		this.mascara = mascara;
	}

	public int getSubred() {
		return subred;
	}

	public void setSubred(int subred) {
		this.subred = subred;
	}
	
	@Override
	public String toString() {
		return "Red [red=" + Arrays.toString(red) + ", broadcast=" + Arrays.toString(broadcast)
		+ ", mascara=" + Arrays.toString(mascara) + "]";
	}
}
