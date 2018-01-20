package gest.view;

import java.text.DecimalFormat;
import java.util.ArrayList;

import gest.MainApp;
import gest.etat.JasperMySQL_Parametres;
import gest.model.Commande;
import gest.util.DateUtil;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

public class FenTableCommandesController {

	private MainApp mainApp;

	private double totalTtc;

	
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

	
	@FXML
	private TextField rechercherTextField;
	@FXML
	private Label totalTtcLabel;
	
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
	        if(!commandeTable.getItems().isEmpty())
		    	totalTtcLabel.setText(calculTotal());
	    }
	    
	    
	    @FXML
	    private void handleDeleteCommande() {
	    	int selectedIndex = commandeTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	        	Commande selectedCommande = commandeTable.getItems().get(selectedIndex);
	        	
	        	//Affichage d'une fenetre de confirmation pour la suppression du client
	        	Alert alert = new Alert(AlertType.CONFIRMATION, "Supprimer la commande dont le code est:  "+ selectedCommande.getCode() + "?",ButtonType.YES,ButtonType.NO);
	            alert.showAndWait();
	            if(alert.getResult()==ButtonType.YES) {
	    	    	boolean okdatabase = selectedCommande.supprimerCRUD(selectedCommande.getCode());
	    	        if (okdatabase)
	    	    	commandeTable.getItems().remove(selectedIndex);
	    	        if(!commandeTable.getItems().isEmpty())
	    		    	totalTtcLabel.setText(calculTotal());
	    	    }
	        }
	        else{
	            // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Selection vide");
	            alert.setHeaderText("Aucun article selectionné");
	            alert.setContentText("Veillez selectionner un article dans la table.");

	            alert.showAndWait();
	        }
	    }
	    
	    
	    @FXML
	    private void handleViewCommande() throws JRException{
	    	if (commandeTable.getSelectionModel().getSelectedItem() != null)
	    	{
	    		JasperMySQL_Parametres.setCodeCommande(commandeTable.getSelectionModel().getSelectedItem().getCode());
	    		JasperMySQL_Parametres.apercu("commande.jrxml");
	    		
	    	}
	    	else
	    	{
	    		Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("Aucune commande selectionnée");
		        alert.setContentText("Vous devez selectionner la commande à afficher");
		    	alert.showAndWait();
	    	}
	    }
	    
	    @FXML
	    private void handlePrintCommande() throws JRException{
	    	if (commandeTable.getSelectionModel().getSelectedItem() != null)
	    	{
	    		JasperMySQL_Parametres.setCodeCommande(commandeTable.getSelectionModel().getSelectedItem().getCode());
	    		JasperMySQL_Parametres.printCommande("commande.jrxml");
	    		
	    	}
	    	else
	    	{
	    		Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("Aucune commande selectionnée");
		        alert.setContentText("Vous devez selectionner la commande à afficher");
		    	alert.showAndWait();
	    	}
	    }
	    
	    @FXML
	    private void handleExportCommande() throws JRException{
	    	if (commandeTable.getSelectionModel().getSelectedItem() != null)
	    	{
	    		mainApp.showFenExport(commandeTable.getSelectionModel().getSelectedItem().getCode(),"commande");
	    		
	    	}
	    	else
	    	{
	    		Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("Aucune commande selectionnée");
		        alert.setContentText("Vous devez selectionner la commande à afficher");
		    	alert.showAndWait();
	    	}
	    }
	    
	    @FXML
	    private void handleRechercherCommandes() {
	    	Commande uneCommande = new Commande();
	    	uneCommande.getLesEnreg().clear();
	        
	        ArrayList<Commande> nouvelleListe = uneCommande.chercherCRUD(rechercherTextField.getText());
	        ObservableList<Commande> nouvelleListeObservale = FXCollections.observableArrayList(nouvelleListe);
	        
	        commandeTable.setItems(nouvelleListeObservale);
	    }
	    
	    
	    private String calculTotal() {
	    	String total="";
	    	try {
	    		DecimalFormat format = new DecimalFormat("#,##0");
	    		totalTtc=0.0;
	    		for(int i =0;i<commandeTable.getItems().size();i++) {
	    			totalTtc += Double.valueOf(commandeTable.getItems().get(i).getTotal_ttc());
	    		}
	    		total= format.format(totalTtc)+ "€";
	    		
	    	}catch(Exception e) {
	    		Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("ERREUR");
		        alert.setHeaderText("Mauvais format de nombre");
		        alert.setContentText("");
		    	alert.showAndWait();
	    	}
	    	return total;
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
private void handleBack() {
	this.dialogStage.close();
	mainApp.showFenCommandes();
}

}
