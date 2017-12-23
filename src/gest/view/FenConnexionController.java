package gest.view;

import gest.MainApp;
import javafx.fxml.FXML;

public class FenConnexionController {
	
	 // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public FenConnexionController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
    @FXML
    private void handleValider() {
    	mainApp.getPrimaryStage().hide();
    	mainApp.showFenMenuPrincipal();
    }
    
    @FXML
    private void handleQuitter() {
    	System.exit(0);
    }


}
