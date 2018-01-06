package gest.view;

import gest.MainApp;
import gest.model.Article;
import gest.model.Client;
import gest.model.Commande;
import gest.model.ModeReglements;
import gest.util.DateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class FenCommandesController {

private Client client;
private Article article;
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
private ComboBox<?> numeroDocumentComboBox;
@FXML
private TextField codeTextField;
@FXML
private TextField designationTextField;
@FXML
private TextField codeCategorieTextField;
@FXML
private ComboBox<Integer> quantiteComboBox;
@FXML
private TextField montantTextField;
@FXML
private ComboBox<?> modeReglementComboBox;
@FXML
private Button nomCLientButton;
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
    	/* 
    	 * On ajouter un listener pour reagir au
    	 * changement de valeur de la quantité
    	 * pour calculer le montant
    	 */
    	quantiteComboBox.valueProperty().addListener(new ChangeListener<Integer>() {
    		@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				// TODO Auto-generated method stub
    			if(quantiteComboBox.getValue()!=null)
    				handleRefreshMontant();
			}
    	});
    	
    	
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
    private void handleFichierDesCommandes() {
    	mainApp.showFenTableCommandes();
    }
    
    @FXML
    private void handleSelectionClient() {
    	client = new Client(null,null,null,2,null);
    	
    	boolean okRowDoubleClicked = mainApp.showFenChoixClient(client);
    	/*
    	 * Le client selectionné dans FenChoixClient est recuilli dans la variable client
    	 */
    	if (okRowDoubleClicked) {
    		nomCLientButton.setText(client.getNom()+" "+client.getPrenom());
    	}
    	
    }
    
    @FXML
    private void handleSelectionArticle() {
    	article = new Article(null,null,null,2,1,null);
    	
    	boolean okRowDoubleClicked = mainApp.showFenChoixArticle(article);
    	/*
    	 * Le client selectionné dans FenChoixClient est recuilli dans la variable client
    	 */
    	if (okRowDoubleClicked) {
    		codeTextField.setText(article.getCode());
    		designationTextField.setText(article.getDesignation());
    		codeCategorieTextField.setText(article.getCodeCategorie());
    		/*
    		 * On cree une liste d'entier quon va ajouer dans le ComboBox Quantité
    		 * La quantié possible de commande ne doit pas etre supperieur à celle
    		 * en stock. Donc on recupère la valeur de la quantité en stock pour
    		 * creer la liste des choix possible de quantité
    		 * 
    		 */
    		ArrayList<Integer> array = new ArrayList<Integer>();
    		ObservableList<Integer> quantiteList = FXCollections.observableArrayList(array);
    		for (int i=0;i<article.getQuantite();i++)
    		{
    			quantiteList.add(i+1);
    		}
    		//L'instruction qui sert à ajouter la liste au ComboBox quantité
    		quantiteComboBox.setItems(quantiteList);
    		quantiteComboBox.setValue(1);
    		montantTextField.setText(Double.toString(article.getPrix_unitaire()));
    	}
    	
    }
    
    @FXML
    private void handleRefreshMontant() {
    	double montant = article.getPrix_unitaire()*quantiteComboBox.getValue();
		montantTextField.setText(Double.toString(montant));
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
