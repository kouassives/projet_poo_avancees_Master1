package gest.view;

import gest.MainApp;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class FenTableauBordController {

	private MainApp mainApp;

	  /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    }
	    
	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	        
	    }
	    
@FXML
private void handleNombre() {
	
}

@FXML
private void handleCA() {
	this.dialogStage.hide();
	String annee="";
	mainApp.showFenStats(annee);
}

@FXML
private void handleImpaye() {
	
}

@FXML
public void handleMenuPrincipal() {
	this.dialogStage.close();
	mainApp.showFenMenuPrincipal();
}

	private Stage dialogStage;

	/**
 * Sets the stage of this dialog.
 *
 * @param dialogStage
 */
public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
}


}
