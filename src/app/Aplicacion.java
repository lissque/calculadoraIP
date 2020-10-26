package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Aplicacion extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../views/vistaCalculadora.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Calculadora IP");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();		
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}

}
