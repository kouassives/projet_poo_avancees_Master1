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
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FenTableClientController {
	private MainApp mainApp;


	    
	
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
    
    
    private void showClientDetails(Client client) {
    	if(client != null) {
    		nomTextField.setText(client.getNom());
    		prenomTextField.setText(client.getPrenom());
    		codeTextField.setText(client.getCode());
    		if (client.isCarte_Fidele()==1)
    			carte_FideleTextField.setSelected(true);
    		else 
    			carte_FideleTextField.setSelected(false);
    		date_CreationTextField.setText(DateUtil.format(client.date_creationProperty().getValue()));
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

public void handleAddClient() {
	String demande="Ajouter";
	Client tempClient = new Client(null,null,null,2,null);
	
    boolean okClicked = mainApp.showFenFicheClient(tempClient,demande);
    if (okClicked) {
        mainApp.getClientData().add(tempClient);
        tempClient.creerCRUD(tempClient.getCode(), tempClient.getNom(), tempClient.getPrenom(), tempClient.isCarte_Fidele(), tempClient.getDate_creation().toString());
    }
}

@FXML
private void handleEditClient() {
	String demande = "modifier";
    Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
    if (selectedClient != null) {
        boolean okClicked = mainApp.showFenFicheClient(selectedClient,demande);
        if (okClicked) {
            showClientDetails(selectedClient);
        }

    } else {
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Person Selected");
        alert.setContentText("Please select a person in the table.");

        alert.showAndWait();
    }
}


/**
 * Appelé lorsque que l'utilisateur appuie sue le bouton rechercher
 */
@FXML
private void handlesearchClient() {
	
}



/**
 * Called when the user clicks on the delete button.
 */
@FXML
private void handleDeleteClient() {
    int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
        clientTable.getItems().remove(selectedIndex);
    }
    else{
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Person Selected");
        alert.setContentText("Please select a person in the table.");

        alert.showAndWait();
    }
}

}
