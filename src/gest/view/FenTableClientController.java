package gest.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import gest.MainApp;
import gest.model.Client;
import gest.util.DateUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
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
    	carte_Fidele.setCellValueFactory(cellData -> {
    		Integer carte = cellData.getValue().carte_FideleProperty().intValue();
    		
    		String carteAsString;
    		if(carte==1)
            {
                carteAsString = "Oui";
            }
            else
            {
            	carteAsString = "Non";
            }
			return new ReadOnlyStringWrapper(carteAsString);
        });
    		date_Creation.setCellValueFactory(cellData -> {
    		System.out.println(cellData.getValue().date_creationProperty().getValue());
    		String datechaine = DateUtil.format(cellData.getValue().date_creationProperty().getValue());
    		return new ReadOnlyStringWrapper(datechaine);
    	});
    	
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
    
    /*
    @FXML
    private void handleNewPerson() {
        Client tempPerson = new Client("Yves","DUMAS");
        
           mainApp.getClientData().add(tempPerson);
    }
    */
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
