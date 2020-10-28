package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import co.edu.uniquindio.redes.model.Network;
import co.edu.uniquindio.redes.model.NetworkTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.OperacionesRed;
import observable.TablaObservable;

public class ControladorPrincipal implements Initializable{
	
	private OperacionesRed operaciones = new OperacionesRed();
	
    @FXML
    private TextField txtDireccionIP,txtMascara;

    @FXML
    private Label lblDireccionRed, lblMascara, lblBroadcast, lblBitsRed, lblHost, lblRango, lblCantidadHost;
    
    @FXML
    private Button btnCalcularRed;
    
    @FXML
    private TextField txtIPHost, txtMascaraHost;
    
    @FXML
    private Label lblDireccionRedHost, lblDireccionBroadcastHost, lblCantidadHostHost, lblRangoHost;

    @FXML
    private Button btnCalcularHost;
    
    @FXML
    private TextField txtDireccionSN, txtMascaraSN;
    
    @FXML
    private TableView<?> tvSubRedes;

    @FXML
    private TableColumn<?, ?> tcNumero, tcIPSbubred, tcBroadcast, tcRango;

    @FXML
    private ComboBox<?> cmbCantidad;

    @FXML
    private Label lblDireccionRedSN, lblDireccionBroadcastSN, lblCantidadHostSN;
    
    @FXML
    private Button btnCalcularSN;
    
    /**
	 * Metodo que verifica si los cambios de valor en una entrada de informacion corresponde a una IP Y/O mascara valida.
	 * Si son direcciones de red y mascara valida, mostrara y establecera los valores de informacion de la Red. 
	 */
    @FXML
	private void operarNetid(ActionEvent e) {
		if(validar(""+txtDireccionIP.getText().toString(), ""+txtMascara.getText().toString())) {
			String[] temp = hacerOperacionesRed(""+txtDireccionIP.getText().toString(), ""+txtMascara.getText().toString());
			lblDireccionRed.setText(txtDireccionIP.getText());
			lblMascara.setText(temp[0]);
			lblBroadcast.setText(temp[1]);
			lblRango.setText(temp[2]);
			lblCantidadHost.setText(temp[3]);
			lblHost.setText(temp[5]);
			lblBitsRed.setText(temp[6]);
		}
		else {
			lblDireccionRed.setText("");
			lblMascara.setText("");
			lblBroadcast.setText("");
			lblRango.setText("");
			lblCantidadHost.setText("");
			lblHost.setText("");
			lblBitsRed.setText("");
		}
	}
	
	/**
	 * Metodo que valida si las entradas de texto de ip y mascara de subred son validas.
	 * @param ip Direccion IP en formato decimal. 
	 * @param mask Direccion de la mascara de subred en formato simplificado.
	 * @return Valor de verdad de el metodo.
	 */
	public boolean validar( String ip, String mask ) {
		String[] text = ip.split("\\.");
		if( mask.length() != 0 && text.length == 4) {
			int[] address = new int[4];
			try {
				for( int i = 0; i < 4; i++ ) {
					address[i] = Integer.parseInt(text[i]);	
					if( address[i] < 0 || address[i] > 255 ) 
						return false;
				}
				int subnetmask = Integer.parseInt(mask);
				if( subnetmask < 1 || subnetmask > 31)
					return false;
				return true;
			}catch(Exception exception) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Metodo que realiza los calculos asociados a una red basados en la direccion de red
	 * y la mascara de subred en formato simplificado.
	 * @param ip Direccion de red
	 * @param mask Mascara de subred
	 * @return Arreglo que contiene (Mascara de subred en formato decimal, Direccion de Broadcast, Rango 
	 * de direcciones disponibles para los hosts, Numero de host Asignables en la Red, Numero de bits del NetID 
	 * y Numero de bits del HostID)
	 */
	public String[] hacerOperacionesRed(String ip , String mask) {
		String[] text = ip.split("\\.");
		String[] values = new String[7];
		int[] address = new int[4];
		for( int i = 0; i < 4; i++ ) 
			address[i] = Integer.parseInt(text[i]);	
		int subnetmask = Integer.parseInt(mask);
		operaciones.setSubred(subnetmask);
		if(operaciones.esDireccionRed(address)) {
			int tempnet[] = operaciones.getConfig();
			values[0] = tempnet[0]+"."+tempnet[1]+"."+tempnet[2]+"."+tempnet[3];
			operaciones.calcularBroadcast();
			int tempbroadcast[] = operaciones.getRed().getBroadcast();
			values[1] = tempbroadcast[0]+"."+tempbroadcast[1]+"."+tempbroadcast[2]+"."+tempbroadcast[3];
			values[2] = operaciones.calcularRangoHost();
			values[3] = "" +operaciones.contarHosts();
			values[4] = "" + subnetmask;
			values[5] = "" + (32 - subnetmask);
			values[6] = "" + (32 - (32 - subnetmask));
		}
		else {
			for(int j = 0; j< 7; j++)
				values[j] = "";
			values[2] = "No es una direccion de Red";
		}
		return values;
	}
	
	/**
	 * Metodo que verifica si los cambios de valor en una entrada de informacion corresponde a una IP Y/O mascara valida.
	 * Si son direcciones y mascara valida, mostrara y establecera los valores de informacion de la Red. 
	 */
	@FXML
	private void operarHostId(ActionEvent e) {
		if(validar(""+txtIPHost.getText().toString(), ""+txtMascaraHost.getText().toString())) {
			String[] temp = hacerOperacionesHost(""+txtIPHost.getText().toString(), ""+txtMascaraHost.getText().toString());
			lblDireccionRedHost.setText(temp[0]);
			lblDireccionBroadcastHost.setText(temp[1]);
			lblRangoHost.setText(temp[2]);
			lblCantidadHostHost.setText(temp[3]);
		}
		else {
			lblDireccionRedHost.setText("");
			lblDireccionBroadcastHost.setText("");
			lblRangoHost.setText("");
			lblCantidadHostHost.setText("");
		}
	}
	
	/**
	 * Metodo que realiza los calculos de direccion una red basado en una direccion IP de la misma (host o direccion de red)
	 * y la mascara de subred en formato simplificado.
	 * @param ip direccion IP del Host o direccion de red.
	 * @param mask Mascara de subred en formato simplificado.
	 * @return Arreglo que contiene (Direccion de Red, Direccion de Broadcast, Rango de direcciones disponibles para los hosts
	 * y Numero de host Asignables en la Red.
	 */
	public String[] hacerOperacionesHost(String ip , String mask ) {
		String[] text = ip.split("\\.");
		String[] values = new String[4];
		int[] address = new int[4];
		for( int i = 0; i < 4; i++ ) 
			address[i] = Integer.parseInt(text[i]);	
		int subnetmask = Integer.parseInt(mask);
		operaciones.setHost(address);
		operaciones.setSubred(subnetmask);
		operaciones.configNetwork();
		operaciones.calcularDireccionRed();	
		int tempnet[] = operaciones.getRed().getRed();
		values[0] = tempnet[0]+"."+tempnet[1]+"."+tempnet[2]+"."+tempnet[3];
		operaciones.calcularBroadcast();
		int tempbroadcast[] = operaciones.getRed().getBroadcast();
		values[1] = tempbroadcast[0]+"."+tempbroadcast[1]+"."+tempbroadcast[2]+"."+tempbroadcast[3];
		values[2] = operaciones.calcularRangoHost();
		values[3] = "" + operaciones.contarHosts();

		return values;
	}
	
	/**
	 * Metodo que realiza los calculos de Subred asociados a una Red basado en una direccion IP de la misma (host o direccion de red)
	 * y la mascara de subred en formato simplificado.
	 * @param ip Direccion de la IP perteneciente a esa Red.
	 * @param mask Mascara de subred en formato simplificado.
	 * @param bits Porcion de HostID que se destina para realizar Subnetting.
	 * @return Lista que contiene la informacion de Red de cada una de las Subredes.
	 */
	public ArrayList<TablaObservable> hacerOperacionesSubred(String ip , String mask, String bits) {
		ArrayList<TablaObservable> subnets = new ArrayList<>();
		String[] text = ip.split("\\.");
		int[] address = new int[4];
		for( int i = 0; i < 4; i++ ) 
			address[i] = Integer.parseInt(text[i]);	
		int subnetmask = Integer.parseInt(mask);
		int bit = Integer.parseInt(bits);
		System.out.println(bits);
		if(bit < 0)
			return null;
		
		operaciones.setHost(address);
		operaciones.setSubred(subnetmask);
		operaciones.configNetwork();
		operaciones.calcularDireccionRed();
		operaciones.calcularBroadcast();
		//operaciones.dosubnetting
		ArrayList<Network> aux = operations.getSubnets();
		for( Network n: aux ) {
			String index = subnets.size()+"";
			String network = "";
			String broadcast = "";
			for( int j = 0; j < 3; j++ ) {
				network += n.getNetwork()[j] + ".";
				broadcast += n.getBroadcast()[j] + ".";
			}
			int octectnetwork = n.getNetwork()[3]+1;
			int octectbroadcast = n.getBroadcast()[3]-1;
			String range = network + octectnetwork + " - " + broadcast + octectbroadcast;
			network += n.getNetwork()[3];
			broadcast += n.getBroadcast()[3];
			String category = n.getCategory();
			subnets.add(new NetworkTable(index, network, broadcast, range, category));
		}
		return subnets;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
