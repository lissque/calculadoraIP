package models;

import java.util.ArrayList;


public class OperacionesRed {
	private Red red;
	private int[] config, host;
	private int subred;
	private ArrayList<Red> subredes;
	
	public OperacionesRed() {
		red = new Red();
		config = new int[4];
		host = new int[4];
		subredes = new ArrayList<Red>();
	}
	
	/**
	 * Expresa la mascara de subred en formato decimal.
	 */
	public void expresarMascaraEnDecimal() {
		red.setMascara(config);
	}
	
	/**
	 * Metodo que verifica si la direccion IP es una direccion de Red valida de acuerdo a la mascara
	 * @param ipnet Direccion IP;
	 * @return Valor de verdad de la funcion.
	 */
	public boolean esDireccionRed(int[] direccion) {
		boolean ans = true;
		setHost(direccion);
		configNetwork();
		calcularDireccionRed();
		int[] aux = red.getRed();
		for(int i = 0; i < 4; i++) 
			ans = (aux[i] != direccion[i])?false:ans;
		return ans;
	}
	
	/**
	 * Establece la configuración inicial de la red (NetId, HostId ) y la guarda
	 * en el array config.
	 */
	public void configNetwork() {
		for(int j = 0; j < 4; j++ ) 
			config[j] = 0;	
		red.setSubred(getSubred());
		int mask = red.getSubred();
		for( int i = 31; i > 31 - mask; i--) 
			config[3 - i/8 ] += (int) Math.pow(2, i%8);	
	}
	
	/**
	 * Metodo que calcula la direccion de red, dada la direccion IP de un Host y la mascara de subred.
	 */
	public void calcularDireccionRed() {
		int[] red1 = new int[4];
		for( int i = 0; i < 4; i++) 
			red1[i] = host[i] & config[i];
		red.setRed(red1);
	}
	
	/**
	 * Metodo que calcula la direccion de Broadcast de una red.
	 */
	public void calcularBroadcast() {
		int[] temp = red.getRed();
		int[] broadcast = new int[4];
		for( int i = 0; i < 4; i++) 
			broadcast[i] = temp[i] | ( 255 - config[i]);
		red.setBroadcast(broadcast);	
	}
	
	/**
	 * Metodo que realiza un conteo de los Hosts que estan o podrian estar en la Red.
	 */
	public int contarHosts() {
		return (int) Math.pow(2, 32-getSubred())-2;	
	}
	
	/**
	 * Metodo que calcula el rango de direcciones IP asignables a los host en la Red.
	 * @return String que contiene el rango de los host sin incluir la direccion de red ni de broadcast.
	 */
	public String calcularRangoHost() {
		if(contarHosts()==0)
			return "Not avalaible";
		String begin = "", end = "", ans = "";
		int[] aux = red.getRed();
		int[] temp = red.getBroadcast();
		for(int j = 0; j < 3; j++) {
			begin += aux[j]+".";
			end += temp[j]+".";
		}
		begin += (aux[3]+1);
		end += (temp[3]-1);
		ans += begin + " - " + end;
		System.out.println(ans);
		return ans;
	}

	public Red getRed() {
		return red;
	}

	public void setRed(Red net) {
		this.red = net;
	}

	public int[] getConfig() {
		return config;
	}

	public void setConfig(int[] config) {
		this.config = config;
	}

	public int[] getHost() {
		return host;
	}

	public void setHost(int[] host) {
		this.host = host;
	}


	public ArrayList<Red> getSubredes() {
		return subredes;
	}

	public void setSubredes(ArrayList<Red> subredes) {
		this.subredes = subredes;
	}

	public int getSubred() {
		return subred;
	}

	public void setSubred(int subred) {
		this.subred = subred;
	}
	
	
}
