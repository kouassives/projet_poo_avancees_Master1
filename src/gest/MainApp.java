package gest;

import java.io.IOException;

import gest.view.FenConnexionController;
import gest.view.FenMenuPrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	
	public MainApp() {
    }

	@Override
	public void start(Stage primaryStage) {
		this.setPrimaryStage(primaryStage);
        this.getPrimaryStage().setTitle("Gestion");

        // Set the application icon.
       this.getPrimaryStage().getIcons().add(new Image("file:resources/images/icone_eclipse.png"));

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
            getPrimaryStage().setScene(scene);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().setResizable(false);
            // Give the controller access to the main app.
            FenConnexionController controller = loader.getController();
            controller.setMainApp(this);
            
            getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showFenMenuPrincipal() {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenMenuPrincipal.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Menu Principal");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneMenuPrincipal = new Scene(page);
            dialogStage.setScene(sceneMenuPrincipal);
            dialogStage.getIcons().add(new Image("file:resources/images/icone_eclipse.png"));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenMenuPrincipalController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            
            // Show the dialog
            dialogStage.show();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }
	    }

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}	
}
	


