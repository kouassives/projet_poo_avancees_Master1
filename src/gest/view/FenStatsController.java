package gest.view;

import gest.MainApp;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class FenStatsController {

private String annee;
//private  DefaultPieDataset data = new DefaultPiedDataset();
	public String getAnnee() {
	return annee;
}

public void setAnnee(String annee) {
	this.annee = annee;
}

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
