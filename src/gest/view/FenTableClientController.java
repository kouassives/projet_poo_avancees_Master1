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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField codeTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private CheckBox carte_FideleTextField;
    @FXML
    private TextField date_CreationTextField;

	


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
    		String datechaine = DateUtil.format(cellData.getValue().date_creationProperty().getValue());
    		return new ReadOnlyStringWrapper(datechaine);
    	});
    	
    
    	clientTable.getSelectionModel().selectedItemProperty().addListener(
    		            (observable, oldValue, newValue) -> showClientDetails(newValue));
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
    
    private void showClientDetails(Client client) {
    	if(client != null) {
    		nomTextField.setText(client.getNom());
    		prenomTextField.setText(client.getPrenom());
    		codeTextField.setText(client.getCode());
    		if (client.isCarte_Fidele()==1)
    			carte_FideleTextField.setSelected(true);
    		else 
    			carte_FideleTextField.setSelected(false);
    		date_CreationTextField.setText(client.date_creationProperty().getValue().toString());
    	}
    	else {
    		nomTextField.setText("");
    		prenomTextField.setText("");
    		codeTextField.setText("");
    		date_CreationTextField.setText("");
    	}
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
