package gest.view;

import gest.MainApp;
import gest.model.Commande;
import gest.util.DateUtil;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class FenTableCommandesController {

	private MainApp mainApp;

	
	@FXML
	private TableView<Commande> commandeTable;
	@FXML
	private TableColumn<Commande, String> code;
	@FXML
	private TableColumn<Commande, String> client;
	@FXML
	private TableColumn<Commande, String> modeReglement;
	@FXML
	private TableColumn<Commande, Number> totalTcc;
	@FXML
	private TableColumn<Commande, String> date;


	
	  /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    	code.setCellValueFactory(cellData -> cellData.getValue().CodeProperty());
	    	client.setCellValueFactory(cellData -> cellData.getValue().NomPrenom_clientProperty());
	    	modeReglement.setCellValueFactory(cellData -> cellData.getValue().Mode_reglementProperty());
	    	totalTcc.setCellValueFactory(cellData -> cellData.getValue().Total_ttcProperty());
	    	date.setCellValueFactory(cellData -> {
	    		String datechaine = DateUtil.format(cellData.getValue().DateProperty().getValue());
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
	        /* Pour Recuperer les donner et les mettre dans une tableview
	         * On peut recuperer seulement la liste des clients avec mainApp.getClientData() 
	         * Pour inserer les donner dans la table de la fenetre associer a ce controlleur
	         * dataTable.setItems(mainApp.getClientData());
	         */
	        commandeTable.setItems(mainApp.getCommandeData());
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
