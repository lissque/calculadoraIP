package observable;

import javafx.beans.property.SimpleStringProperty;

public class TablaObservable {
	
	SimpleStringProperty indice, subred, broadcast, rango;
	
	public TablaObservable(String index, String network, String broadcast, String range) {
		super();
		this.indice = new SimpleStringProperty(index);
		this.subred = new SimpleStringProperty(network);
		this.broadcast = new SimpleStringProperty(broadcast);
		this.rango = new SimpleStringProperty(range);
	}

	public SimpleStringProperty getIndice() {
		return indice;
	}

	public void setIndice(SimpleStringProperty indice) {
		this.indice = indice;
	}

	public SimpleStringProperty getSubred() {
		return subred;
	}

	public void setSubred(SimpleStringProperty subred) {
		this.subred = subred;
	}

	public SimpleStringProperty getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(SimpleStringProperty broadcast) {
		this.broadcast = broadcast;
	}

	public SimpleStringProperty getRango() {
		return rango;
	}

	public void setRango(SimpleStringProperty rango) {
		this.rango = rango;
	}

	@Override
	public String toString() {
		return "TablaSubredes [indice=" + indice.getValue() + ", subred=" + subred + ", broadcast=" + broadcast + ", rango=" + rango + "]";
	}
}
