package gest.view;

import java.util.ArrayList;

import gest.MainApp;
import gest.model.Client;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class FenTableClientController {
	private MainApp mainApp;


	    /**
	     * Returns the data as an observable list of Persons. 
	     * @return
	     */
	    
	
	@FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, String> code;
    @FXML
    private TableColumn<Client, String> nom;
    @FXML
    private TableColumn<Client, String> prenom;
    @FXML
    private TableColumn<Client, String> carte_Fidele;
    @FXML
    private TableColumn<Client, String> date_Creation;


	


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	code.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
    	nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
    	prenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
    	//carte_Fidele.setCellValueFactory(cellData -> cellData.getValue().carte_FideleProperty().toString());
    	//date_Creation.setCellValueFactory(cellData -> cellData.getValue().date_creationProperty());
    }
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        clientTable.setItems(mainApp.getClientData());
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



public void handleMenuPrincipal() {
	this.dialogStage.hide();
	mainApp.showFenMenuPrincipal();
}


}
