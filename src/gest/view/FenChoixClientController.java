package gest.view;

import java.util.ArrayList;

import gest.MainApp;
import gest.model.Client;
import gest.util.DateUtil;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FenChoixClientController {

	@FXML
    private TableView<Client> clientTable;
	@FXML
    private TableColumn<Client, String> code;
	@FXML
    private TableColumn<Client, String> nom;
    @FXML
    private TableColumn<Client, String> prenom;
    @FXML
    private TableColumn<Client, String> carteFidelite;
    @FXML
    private TableColumn<Client, String> dateCreation;


    @FXML
    private TextField rechercherTextField;
    
    
    
    private Client client;
	private MainApp mainApp;
	private boolean rowDoubleClicked = false;

	  /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    	
	    	code.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
	    	nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
	    	prenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
	    	carteFidelite.setCellValueFactory(cellData -> {
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
	    		dateCreation.setCellValueFactory(cellData -> {
	    		String datechaine = DateUtil.format(cellData.getValue().date_creationProperty().getValue());
	    		return new ReadOnlyStringWrapper(datechaine);
	    	});
	    
	    		clientTable.setRowFactory( tv ->{
	    			TableRow<Client> row = new TableRow<>();
	    			row.setOnMouseClicked(event -> {
	    				if(event.getClickCount()==2 && (! row.isEmpty())){
	    				Client rowData = row.getItem();
	    				handleTableViewRowsDoubleCliked(rowData);
	    				//this.client = rowData;
	    				rowDoubleClicked =true;
	    				dialogStage.close();
	    			}
	    			});
	    			return row;
	    		});
	    		
	    		/* Pour Recuperer les donner et les mettre dans une tableview
	             * On peut recuperer seulement la liste des clients avec mainApp.getClientData() 
	             * Pour inserer les donner dans la table de la fenetre associer a ce controlleur
	             */
	            clientTable.setItems(FXCollections.observableArrayList(new Client().getlesEnreg()));
	            
	    }
	    
	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }
	    
	    public void setClient(Client client) {
	    	this.client = client;
	    }
	    
	    @FXML
	    private void handleRechercherClient() {
	    	Client unClient = new Client();
	        unClient.getlesEnreg().clear();
	        
	        ArrayList<Client> nouvelleListe = unClient.chercherCRUD(rechercherTextField.getText());
	        ObservableList<Client> nouvelleListeObservale = FXCollections.observableArrayList(nouvelleListe);
	        
	        clientTable.setItems(nouvelleListeObservale);
	    }
	    
	    private void handleTableViewRowsDoubleCliked(Client vclient)
	    {
	    	client.setCode(vclient.getCode());
	    	client.setNom(vclient.getNom());
	    	client.setPrenom(vclient.getPrenom());
	    	client.setCarte_fidele(vclient.isCarte_Fidele());
	    	client.setDate_creation(vclient.getDate_creation());
	    	
	    }
	    
	    public boolean isrowDoubleClicked() {
	    	return rowDoubleClicked;
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
