package gest.view;

import gest.MainApp;
import gest.model.Article;
import gest.model.Client;
import gest.model.Commande;
import gest.model.LignesCommandes;
import gest.model.ModeReglements;
import gest.util.DateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class FenCommandesController {

private Client client;
private Article article;
private MainApp mainApp;

@FXML
private TableView<LignesCommandes> lignesCommandesTable;
@FXML
private TableColumn<LignesCommandes, String> code;
@FXML
private TableColumn<LignesCommandes, String> codeCategorie;
@FXML
private TableColumn<LignesCommandes, String> designation;
@FXML
private TableColumn<LignesCommandes, Number> quantite;
@FXML
private TableColumn<LignesCommandes, Number> prixUnitaire;
@FXML
private TableColumn<LignesCommandes, Number> total;



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
private ComboBox<String> modeReglementComboBox;
@FXML
private Button nomCLientButton;
@FXML
private Label totalLabel;
@FXML
private Label dateDuJour;
	

private static String codeArticle="";
private static String codeClient="";
private double totalTtc;
private static String codeCommande=randomCommandeNumber();
private ModeReglements mode = new ModeReglements();
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	dateDuJour.setText(DateUtil.format(LocalDate.now()));
    	
    	code.setCellValueFactory(cellData -> cellData.getValue().codeCommandeProperty());
    	codeCategorie.setCellValueFactory(cellData -> cellData.getValue().codeArticleProperty());
    	designation.setCellValueFactory(cellData -> cellData.getValue().designationProperty());
    	quantite.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty());
    	prixUnitaire.setCellValueFactory(cellData -> cellData.getValue().prixUnitaireProperty());
    	total.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
    	
    	/* 
    	 * On ajouter un listener pour reagir au
    	 * changement de valeur de la quantit�
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

    	totalLabel.setText("0");

    	/*
		 * On cree une liste de String quon va ajouer dans le ComboBox modeReglementComboBox
		 */
		ArrayList<String> modeReglementList =new ArrayList<String>();
		ObservableList<String> modeReglementListObservable = FXCollections.observableArrayList(modeReglementList);
		for (int i=0;i<mode.getlesEnreg().size();i++)
		{
			modeReglementListObservable.add(mode.getlesEnreg().get(i).toString());
		}
		//L'instruction qui sert � ajouter la liste au modeReglementComboBox
		modeReglementComboBox.setItems(modeReglementListObservable);
		modeReglementComboBox.setValue(modeReglementComboBox.getItems().get(0));
    	
    }
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        lignesCommandesTable.setItems(mainApp.getLignesCommandeData());
    	mainApp.getLignesCommandeData().clear();
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
    	 * Le client selectionn� dans FenChoixClient est recuilli dans la variable client
    	 */
    	if (okRowDoubleClicked) {
    		nomCLientButton.setText(client.getNom()+" "+client.getPrenom());
    		codeClient=client.getCode();
    	}
    	
    }
    
    @FXML
    private void handleSelectionArticle() {
    	article = new Article(null,null,null,2,1,null);
    	
    	boolean okRowDoubleClicked = mainApp.showFenChoixArticle(article);
    	/*
    	 * Le client selectionn� dans FenChoixClient est recuilli dans la variable client
    	 */
    	if (okRowDoubleClicked) {
    		codeTextField.setText(article.getCode());
    		designationTextField.setText(article.getDesignation());
    		codeCategorieTextField.setText(article.getCodeCategorie());
    		/*
    		 * On cree une liste d'entier quon va ajouer dans le ComboBox Quantit�
    		 * La quanti� possible de commande ne doit pas etre supperieur � celle
    		 * en stock. Donc on recup�re la valeur de la quantit� en stock pour
    		 * creer la liste des choix possible de quantit�
    		 * 
    		 */
    		ArrayList<Integer> array = new ArrayList<Integer>();
    		ObservableList<Integer> quantiteList = FXCollections.observableArrayList(array);
    		for (int i=0;i<article.getQuantite();i++)
    		{
    			quantiteList.add(i+1);
    		}
    		//L'instruction qui sert � ajouter la liste au ComboBox quantit�
    		quantiteComboBox.setItems(quantiteList);
    		if(article.getQuantite()>0)
    			quantiteComboBox.setValue(1);
    		montantTextField.setText(Double.toString(article.getPrix_unitaire()));
    	}
    	
    }
    
    @FXML
    private void handleRefreshMontant() {
    	double montant = article.getPrix_unitaire()*quantiteComboBox.getValue();
		montantTextField.setText(Double.toString(montant));
    }
    
    @FXML
    private void handleAddLignesCommande() {
    	
    	if(!(codeTextField.getText().isEmpty() || codeTextField.getText().equals("")))
    		{
    			int vquantite = quantiteComboBox.getValue();
    			if (vquantite > 0) {
    				
    				/*
        			 * Effectuons un test pour verifier si
        			 * une ligne de commande d'un meme article � �t� ajout�
        			 * �  la table ( d'o� � la liste mainApp.getLignesCommandeData()
        			 */
	        		boolean unique =true;
	        		for (LignesCommandes lignesCommande : mainApp.getLignesCommandeData()) {
	        			if(lignesCommande.getcodeArticle().equals(codeTextField.getText()))
	        				{
	        					unique=false;
	        					break;
	        				}
	        		}
	        			if(unique) {
		    				/*
		    				 * Creation d'une ligne de commande pour l'ajouter dans la listeObservable des
		    				 * commande en cours, ainsi on procede � l'ajout dans la table
		    				 * 
		    				 */
		    				LignesCommandes uneLigne = new LignesCommandes(null,null,null,1,0,0);
		    				uneLigne.setCodeCommande(codeCommande);
		    				uneLigne.setCodeArticle(codeTextField.getText());
		    				uneLigne.setDesignation(designationTextField.getText());
		    				uneLigne.setQuantite(quantiteComboBox.getValue());
		    				uneLigne.setPrixUnitaire((int)article.getPrix_unitaire());
		    				uneLigne.setTotal(quantiteComboBox.getValue()*(int)article.getPrix_unitaire());
		    				
		    				mainApp.getLignesCommandeData().add(uneLigne);
		    				/*
		    				 * Le calcul de total parcours la tableView donc
		    				 * faudrait que le calcul se fasse apr�s l'ajout d'une nouvelle commadne
		    				 */
		    				totalLabel.setText(calculTotal());
	        			}else {
	        				Alert alert = new Alert(AlertType.ERROR);
	        		        alert.setTitle("ERREUR");
	        		        alert.setHeaderText("Vous tentez d'ajouter plusieurs fois");
	        		        alert.setContentText("l'article: " + article.getDesignation());
	        		    	alert.showAndWait();
	        			}
    				
    			}
    			else {
    				Alert alert = new Alert(AlertType.INFORMATION);
    		        alert.setTitle("INFORMATION");
    		        alert.setHeaderText("Mauvaise quantit�");
    		        alert.setContentText("La quantit� doit �tre d'au moins 1");
    		    	alert.showAndWait();
    			}
    		}
    }
    
    @FXML
    private void handleValiderCommande() {
    	if(!lignesCommandesTable.getItems().isEmpty()) {
    		//Cas o� la tableView est non vide
    		if(!codeClient.equals("")) {
    			int codeReglement = mode.getlesEnreg().get(modeReglementComboBox.getSelectionModel().getSelectedIndex()).getCode();
    			String typeReglement = mode.getlesEnreg().get(modeReglementComboBox.getSelectionModel().getSelectedIndex()).getType();
    			Commande commande = new Commande(codeCommande,codeClient,client.getNom()+""+client.getPrenom(),totalTtc,typeReglement,LocalDate.now());
    			boolean okCommande = commande.creerCRUD(codeCommande, codeClient, totalTtc, codeReglement);
    			//LignesCommandes ligneCommandes = new LignesCommandes(codeCommande,codeArticle,article.getDesignation());
    			
    			if (okCommande)
    			{
    				for(LignesCommandes lignesCommandes : mainApp.getLignesCommandeData()) {
	    				lignesCommandes = new LignesCommandes(codeCommande,lignesCommandes.getcodeArticle(),lignesCommandes.getdesignation(),lignesCommandes.getquantite(),lignesCommandes.getprixUnitaire(),lignesCommandes.gettotal());
	        			lignesCommandes.creerCRUD(codeCommande, lignesCommandes.getcodeArticle(), lignesCommandes.getquantite(), lignesCommandes.getprixUnitaire(),lignesCommandes.gettotal());	
	    			}
	    			
	    			nomCLientButton.setText("[ Cliquez ici pour selectionner un client");
	    			codeTextField.setText("");
	    			designationTextField.setText("");
	    			codeCategorieTextField.setText("");
	    			quantiteComboBox.getItems().clear();
	    			montantTextField.setText("");
	    			lignesCommandesTable.getItems().clear();
	    			codeClient="";
	    			codeCommande = randomCommandeNumber();
	    			totalLabel.setText("0");
	    			Alert alert = new Alert(AlertType.INFORMATION);
	    	        alert.setTitle("INFORMATION");
	    	        alert.setHeaderText("Commande ajout�e !");
	    	        alert.setContentText("");
	    	    	alert.showAndWait();
    			}
    		}
    		else {
    			Alert alert = new Alert(AlertType.INFORMATION);
    	        alert.setTitle("INFORMATION");
    	        alert.setHeaderText("Aucun client selectionn�");
    	        alert.setContentText("Vous devez selectionner un client pour continuer");
    	    	alert.showAndWait();
    		}
    	}
    	else {
    		Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("INFORMATION");
	        alert.setHeaderText("Aucun article ajout� � la commande");
	        alert.setContentText("Une commande doit avoir moins un article pour valid�e");
	    	alert.showAndWait();
    	}
    }
    
    private String calculTotal() {
    	String total="";
    	try {
    		DecimalFormat format = new DecimalFormat("#,##0");
    		totalTtc=0.0;
    		for(int i =0;i<lignesCommandesTable.getItems().size();i++) {
    			totalTtc += Double.valueOf(lignesCommandesTable.getItems().get(i).gettotal());
    		}
    		total= format.format(totalTtc)+ "�";
    		
    	}catch(Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("ERREUR");
	        alert.setHeaderText("Mauvais format de nombre");
	        alert.setContentText("");
	    	alert.showAndWait();
    	}
    	return total;
    }
    
    private static String randomCommandeNumber() {
    	String num = "";
    	Random rand = new Random();
    	num+="FAC" + rand.nextInt(999);
    	return num;
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
