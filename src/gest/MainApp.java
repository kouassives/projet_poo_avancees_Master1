package gest;

import java.io.IOException;

import gest.view.FenConnexionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	
	public MainApp() {
    }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestion");

        // Set the application icon.
       //this.primaryStage.getIcons().add(new Image("resources/images/icone_eclipse.ico"));

        initPrimaryLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initPrimaryLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenConnexion.fxml"));
            AnchorPane FenConnexion = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(FenConnexion);
            primaryStage.setScene(scene);
            
            // Give the controller access to the main app.
            FenConnexionController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}

