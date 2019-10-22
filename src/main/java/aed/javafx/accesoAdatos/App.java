package aed.javafx.accesoAdatos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	private Controller controller;

	
	public void start(Stage primaryStage) throws Exception {
		
		controller = new Controller();
		
		Scene escena = new Scene(controller.getRoot(), 600, 800);
		
		primaryStage.setScene(escena);
		primaryStage.setTitle("Acceso a datos");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
