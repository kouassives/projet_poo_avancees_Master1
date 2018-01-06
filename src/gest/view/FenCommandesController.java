package gest.view;

import gest.MainApp;
import gest.model.Article;
import gest.model.Client;
import gest.model.Commande;
import gest.model.ModeReglements;
import gest.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Random;

public class FenCommandesController {

private Client client;
private MainApp mainApp;

@FXML
private TableView<Commande> commandeTable;
@FXML
private TableColumn<Commande, String> code;
@FXML
private TableColumn<Commande, String> designation;
@FXML
private TableColumn<Commande, String> codeCategorie;
@FXML
private TableColumn<Commande, Number> quantite;
@FXML
private TableColumn<Commande, Number> Montant;



@FXML
private ComboBox<?> numeroDocumentComboBox;
@FXML
private TextField codeTextField;
@FXML
private TextField designationTextField;
@FXML
private TextField codeCategorieTextField;
@FXML
private ComboBox<?> quantiteComboBox;
@FXML
private ComboBox<?> modeReglementComboBox;
@FXML
private Label totalLabel;
@FXML
private Label dateDuJour;
	

private static String codeArticle="";
private static String codeClient="";
//private static String codeCommande=randomCommandeNumber();
private ModeReglements mode = new ModeReglements();
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	dateDuJour.setText(DateUtil.format(LocalDate.now()));
    	
    	code.setCellValueFactory(cellData -> cellData.getValue().CodeProperty());
    	
    	
    	
    }
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        commandeTable.setItems(mainApp.getCommandeData());
    }
    
    @FXML
    private void handleSelectionClient() {
    	client = new Client(null,null,null,2,null);
    	
    	mainApp.showFenChoixClient(client);
    	/*
    	 * Le client selectionné dans FenChoixClient est recuilli dans la variable client
    	 * 
    	 */
		
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
