package gest.view;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import gest.MainApp;
import gest.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
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
	//this.dialogStage.hide();
	TextInputDialog dialog = new TextInputDialog(Integer.toString(LocalDate.now().getYear()));
	dialog.setTitle("Statistique annuelle");
	dialog.setHeaderText("l'année de des statisque à produire");
	dialog.setContentText("Veuillez entrer l'année");
	Optional<String> result = dialog.showAndWait();
	result.ifPresent(annee-> mainApp.showFenStats(annee));
		
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
