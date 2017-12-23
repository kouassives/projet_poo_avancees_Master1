package gest.view;

import gest.MainApp;
import javafx.stage.Stage;

public class FenMenuPrincipalController {
	  private MainApp mainApp;

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


    public void handleclosing() {
    	mainApp.getPrimaryStage().hide();
    }
}
