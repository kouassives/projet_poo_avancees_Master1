package gest.view;

import gest.MainApp;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class FenMenuPrincipalController {
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

    @FXML
	public void handleGestionClient() {
    	this.dialogStage.hide();
    	mainApp.showFenTableClient();
    }
    
    @FXML
	public void handleGestionArticles() {
    	this.dialogStage.hide();
    	mainApp.showFenArticle();
    }
    
    @FXML
	public void handleGestionCommandes() {
    	this.dialogStage.hide();
    	mainApp.showFenCommandes();
    }
    
    @FXML
	public void handleTableauBord() {
    	this.dialogStage.hide();
    	mainApp.showFenTableauBord();
    }
    
    @FXML
	public void handleclosing() {
    	this.dialogStage.hide();
    	mainApp.initPrimaryLayout();
    }
}
