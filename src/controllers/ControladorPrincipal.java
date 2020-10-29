package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import models.OperacionesRed;
import models.Red;
import observable.TablaObservable;

public class ControladorPrincipal implements Initializable{
	
	private OperacionesRed operaciones = new OperacionesRed();
	private ObservableList<TablaObservable> listaSubredes = FXCollections.observableArrayList();
	
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
    private TableView<TablaObservable> tvSubRedes;

    @FXML
    private TableColumn<TablaObservable, String> tcNumero, tcIPSbubred, tcBroadcast, tcRango;

    @FXML
    private ComboBox<Integer> cmbCantidad;

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
	 * Metodo que verifica si los cambios de valor en una entrada de informacion corresponde a una IP Y/O mascara valida.
	 * Si son direcciones de red y mascara valida, ejecutara los calculos de subnetting.
	 */
	@FXML
	public void operarSubredes(ActionEvent e) {
		if(validar(""+txtDireccionSN.getText().toString(), ""+txtMascaraSN.getText().toString()) &&
				cmbCantidad.getSelectionModel().getSelectedIndex() > 0 ) {
			String [] tmp = operAux(txtDireccionSN.getText().toString(), txtMascaraSN.getText().toString(), Integer.parseInt(""+cmbCantidad.getSelectionModel().getSelectedIndex()));
			lblDireccionRedSN.setText(tmp[0]);
			lblDireccionBroadcastSN.setText(tmp[1]);
			lblCantidadHostSN.setText(tmp[2]);
			ArrayList<TablaObservable> subnets = hacerOperacionesSubred(""+txtDireccionSN.getText().toString(),
					""+txtMascaraSN.getText().toString(), ""+cmbCantidad.getSelectionModel().getSelectedIndex());
			listaSubredes.clear();
			if (!subnets.isEmpty()){
				for(TablaObservable n: subnets)
					listaSubredes.add(n);
				llenarTabla();
				}
		}
	}
	
	/**
	 * Metodo que dados los valores de Red de un conjunto de subredes los mostrara en una tabla
	 */
	private void llenarTabla() {
		tvSubRedes.refresh();
		tcNumero.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TablaObservable,String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<TablaObservable, String> param) {
				// TODO Auto-generated method stub
				return param.getValue().getIndice();
			}	
		});
		tcIPSbubred.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TablaObservable, String>, ObservableValue<String>>() {			
			@Override
			public ObservableValue<String> call(CellDataFeatures<TablaObservable, String> param) {
				// TODO Auto-generated method stub
				return param.getValue().getSubred();
			}
		});
		tcBroadcast.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TablaObservable, String>, ObservableValue<String>>() {			
			@Override
			public ObservableValue<String> call(CellDataFeatures<TablaObservable, String> param) {
				// TODO Auto-generated method stub
				return param.getValue().getBroadcast();
			}
		});
		tcRango.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TablaObservable, String>, ObservableValue<String>>() {			
			@Override
			public ObservableValue<String> call(CellDataFeatures<TablaObservable, String> param) {
				// TODO Auto-generated method stub
				return param.getValue().getRango();
			}
		});			
		tvSubRedes.setItems(listaSubredes);
		tvSubRedes.setTableMenuButtonVisible(false);
		tvSubRedes.setPlaceholder(new Label("Table is void"));
	}
	
	public String[] operAux(String ip, String mask,int bit) {
		String[] text = ip.split("\\.");
		String[] values = new String[3];
		int[] address = new int[4];
		for( int i = 0; i < 3; i++ ) 
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
		values[2] = "" + Math.pow(2.0, (32-(bit+subnetmask)));

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
		operaciones.hacerSubredes(bit);
		ArrayList<Red> aux = operaciones.getSubredes();
		for( Red n: aux ) {
			String index = subnets.size()+"";
			String network = "";
			String broadcast = "";
			for( int j = 0; j < 3; j++ ) {
				network += n.getRed()[j] + ".";
				broadcast += n.getBroadcast()[j] + ".";
			}
			int octectnetwork = n.getRed()[3]+1;
			int octectbroadcast = n.getBroadcast()[3]-1;
			String range = network + octectnetwork + " - " + broadcast + octectbroadcast;
			network += n.getRed()[3];
			broadcast += n.getBroadcast()[3];
			subnets.add(new TablaObservable(index, network, broadcast, range));
		}
		return subnets;
	}
	
	/**
	 * Metodo que establece los valores del combobox de seleccion de numero de subredes.
	 * @return Lista que contiene el numero de subredes.
	 */
	@FXML
	public ObservableList<Integer> llenarCombobox(){
		ObservableList<Integer> subnets = FXCollections.observableArrayList();
		if( txtMascaraSN.getText().toString().length() != 0 ) {
			try {
				int nets = Integer.parseInt(txtMascaraSN.getText().toString());
				if( nets == 1)
					subnets.add(0);
				else if( nets > 1 && nets < 30) {
					for( int j = 0; j < ( 31- nets); j++ )
						subnets.add((int) Math.pow(2, j));
				}
			}catch(Exception exception) {
				System.out.println("No llenó combobox");
			}
		}
		else
			subnets.add(0);
		return subnets;
	}
	
	@FXML
	public void controlarCamposSubredes() {
		txtMascaraSN.textProperty().addListener((observable, oldValue, newValue) -> {
			cmbCantidad.setItems(llenarCombobox());
			cmbCantidad.getSelectionModel().select(0);
		});
		txtDireccionSN.textProperty().addListener((observable, oldValue, newValue) -> {
			cmbCantidad.setItems(llenarCombobox());
			cmbCantidad.getSelectionModel().select(0);
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ObservableList<Integer> subredes = FXCollections.observableArrayList();
		subredes.add(0);
		cmbCantidad.setItems(subredes);
		cmbCantidad.getSelectionModel().select(0);
		cmbCantidad.setVisibleRowCount(5);
		controlarCamposSubredes();
	}
}
