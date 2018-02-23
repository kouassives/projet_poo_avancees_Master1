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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import messervices.MonService;
import messervices.MonServiceService;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import com.web.service.TaxeProxy;


public class FenCommandesController {

private Client client;
private Article article;
private MainApp mainApp;

private Double tva=0.0;

ArrayList<String> selectedCodeCategorieList = new ArrayList<String>();
ArrayList<Integer> selectedQuantie= new ArrayList<Integer>();

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
private Label tvaValueLabel;
@FXML
private Label totalLabelTTC;
@FXML
private Label dateDuJour;
	

private static String codeArticle="";
private static String codeClient="";
private double totalTtc;
private double totalHt;
private static String codeCommande=randomCommandeNumber();
private ModeReglements mode = new ModeReglements();
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	dateDuJour.setText(DateUtil.format(LocalDate.now()));
    	getTva();
    	
    	code.setCellValueFactory(cellData -> cellData.getValue().codeCommandeProperty());
    	codeCategorie.setCellValueFactory(cellData -> cellData.getValue().codeArticleProperty());
    	designation.setCellValueFactory(cellData -> cellData.getValue().designationProperty());
    	quantite.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty());
    	prixUnitaire.setCellValueFactory(cellData -> cellData.getValue().prixUnitaireProperty());
    	total.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
    	
    	lignesCommandesTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) ->
	            {
	            	int index = lignesCommandesTable.getSelectionModel().getSelectedIndex();
	            	LignesCommandes selectedLignesCommandes = lignesCommandesTable.getSelectionModel().getSelectedItem();
	            	
	            	if(article != null && selectedLignesCommandes !=null) {
	            		article.setCode(selectedLignesCommandes.getcodeArticle());
		            	article.setDesignation(selectedLignesCommandes.getdesignation());
		            	article.setPrix_unitaire(selectedLignesCommandes.getprixUnitaire());
	            	}
	            	if(!selectedCodeCategorieList.isEmpty() && index>=0)
	            	codeCategorieTextField.setText(selectedCodeCategorieList.get(index));
	            	
	            	ArrayList<Integer> array = new ArrayList<Integer>();
	        		ObservableList<Integer> quantiteList = FXCollections.observableArrayList(array);
	        		
	        		if (!selectedQuantie.isEmpty() && index>=0)
	        		{
	        			for (int i=0;i<selectedQuantie.get(index);i++)
			        		{
			        			quantiteList.add(i+1);
			        		}
				        //L'instruction qui sert à ajouter la liste au ComboBox quantité
		        		quantiteComboBox.setItems(quantiteList);
	        		}
	            	if (newValue!=null)
	            	showLignesCommandesDetails(newValue);
	            	
	            });
    	
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

    	totalLabel.setText("0");
    	totalLabelTTC.setText("0");
    	
    		

    	/*
		 * On cree une liste de String quon va ajouer dans le ComboBox modeReglementComboBox
		 */
		ArrayList<String> modeReglementList =new ArrayList<String>();
		ObservableList<String> modeReglementListObservable = FXCollections.observableArrayList(modeReglementList);
		for (int i=0;i<mode.getlesEnreg().size();i++)
		{
			modeReglementListObservable.add(mode.getlesEnreg().get(i).toString());
		}
		//L'instruction qui sert à ajouter la liste au modeReglementComboBox
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
    	this.dialogStage.hide();
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
    		codeClient=client.getCode();
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
        			 * une ligne de commande d'un meme article à été ajouté
        			 * à  la table ( d'où à la liste mainApp.getLignesCommandeData()
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
		    				 * commande en cours, ainsi on procede à l'ajout dans la table
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
		    				 * faudrait que le calcul se fasse après l'ajout d'une nouvelle commadne
		    				 */
		    				totalLabel.setText(calculTotal());
		    				totalLabelTTC.setText(calculTTC());
		    				
		    				//pour la gestion de showLignesCommandesDetails()
		    				selectedCodeCategorieList.add(codeCategorieTextField.getText());
		    				selectedQuantie.add(article.getQuantite());
		    				
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
    		        alert.setHeaderText("Mauvaise quantité");
    		        alert.setContentText("La quantité doit être d'au moins 1");
    		    	alert.showAndWait();
    			}
    		}
    }
    
    
    @FXML
    public void handleEditOneRow(){
    	if (lignesCommandesTable.getSelectionModel().getSelectedItem()!=null && !codeTextField.getText().equals("") && codeTextField.getText().length() != 0) {
            String selectedCodeArticle = lignesCommandesTable.getSelectionModel().getSelectedItem().getcodeArticle();
    		if(codeTextField.getText().equals(selectedCodeArticle)) {
	    		int quantite = quantiteComboBox.getValue();
	            lignesCommandesTable.getSelectionModel().getSelectedItem().setQuantite(quantite);
	            lignesCommandesTable.getSelectionModel().getSelectedItem().setTotal(quantiteComboBox.getValue()*(int)article.getPrix_unitaire());
	            
	            totalLabel.setText(calculTotal());
	            totalLabelTTC.setText(calculTTC());
	            
            }else {
            	Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Probleme correspondance");
                alert.setHeaderText("L'article à modifier n'est pas celui selectionné dans la table");
                alert.setContentText(codeTextField.getText()+" different de "+selectedCodeArticle);

                alert.showAndWait();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Selection vide");
            alert.setHeaderText("Aucune ligne selectionnée");
            alert.setContentText("Veillez selectionner une ligne dans la table.");

            alert.showAndWait();
        }
    	
    }
    
    @FXML
    private void handleValiderCommande() {
    	if(!lignesCommandesTable.getItems().isEmpty()) {
    		//Cas où la tableView est non vide
    		if(!codeClient.equals("")) {
    			int codeReglement = mode.getlesEnreg().get(modeReglementComboBox.getSelectionModel().getSelectedIndex()).getCode();
    			String typeReglement = mode.getlesEnreg().get(modeReglementComboBox.getSelectionModel().getSelectedIndex()).getType();
    			Commande commande = new Commande(codeCommande,codeClient,client.getNom()+" "+client.getPrenom(),totalTtc,typeReglement,LocalDate.now());
    			boolean okCommande = commande.creerCRUD(codeCommande, codeClient, totalTtc, codeReglement);
    			//LignesCommandes ligneCommandes = new LignesCommandes(codeCommande,codeArticle,article.getDesignation());
    			
    			if (okCommande)
    			{
    				// Ajout de la commande dans la liste des commandes mainApp.getLignesCommandeData()
    				mainApp.getCommandeData().add(commande);
    				
    				for(LignesCommandes lignesCommandes : mainApp.getLignesCommandeData()) {
	    				lignesCommandes = new LignesCommandes(codeCommande,lignesCommandes.getcodeArticle(),lignesCommandes.getdesignation(),lignesCommandes.getquantite(),lignesCommandes.getprixUnitaire(),lignesCommandes.gettotal());
	        			lignesCommandes.creerCRUD(codeCommande, lignesCommandes.getcodeArticle(), lignesCommandes.getquantite(), lignesCommandes.getprixUnitaire(),lignesCommandes.gettotal());	
	    			}
    				
    				//pour la gestion de showLignesCommandesDetails()
    				selectedCodeCategorieList.clear();;
    				selectedQuantie.clear();
    				
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
	    			totalLabelTTC.setText("0");
	    			Alert alert = new Alert(AlertType.INFORMATION);
	    	        alert.setTitle("INFORMATION");
	    	        alert.setHeaderText("Commande ajoutée !");
	    	        alert.setContentText("");
	    	    	alert.showAndWait();
    			}
    		}
    		else {
    			Alert alert = new Alert(AlertType.INFORMATION);
    	        alert.setTitle("INFORMATION");
    	        alert.setHeaderText("Aucun client selectionné");
    	        alert.setContentText("Vous devez selectionner un client pour continuer");
    	    	alert.showAndWait();
    		}
    	}
    	else {
    		Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("INFORMATION");
	        alert.setHeaderText("Aucun article ajouté à la commande");
	        alert.setContentText("Une commande doit avoir moins un article pour validée");
	    	alert.showAndWait();
    	}
    }
    
    
    @FXML
    public void handleDeleteOneRow() {
    	int selectedIndex = lignesCommandesTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	LignesCommandes selectedLignesCommandes = lignesCommandesTable.getItems().get(selectedIndex);
        	
        	//Affichage d'une fenetre de confirmation pour la suppression de la ligne
        	Alert alert = new Alert(AlertType.CONFIRMATION, "Supprimer la ligne dont le code est:  "+ selectedLignesCommandes.getcodeArticle()+ "?",ButtonType.YES,ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult()==ButtonType.YES) {
            	//Suppression seulement dans la tableView
            	lignesCommandesTable.getItems().remove(selectedIndex);
            	/*
				 * Le calcul de total parcours la tableView donc
				 * faudrait que le calcul se fasse après le retrait de la ligne
				 */
				
            	totalLabel.setText(calculTotal());
            	totalLabelTTC.setText(calculTTC());
            	
            	//pour la gestion de showLignesCommandesDetails()
				selectedCodeCategorieList.remove(selectedIndex);
				selectedQuantie.remove(selectedIndex);
				
    	    }
        }
        else{
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Selection vide");
            alert.setHeaderText("Aucun ligne selection");
            alert.setContentText("Veillez selectionner une ligne de commande dans la table.");

            alert.showAndWait();
        }
    }
    
    @FXML
    public void handleDeleteAllRow() {
    	//Affichage d'une fenetre de confirmation pour la suppression de toutes les lignes
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Supprimer toutes les lignes de commandes ?  ",ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult()==ButtonType.YES) {
        	//Suppression seulement dans la tableView
        	mainApp.getLignesCommandeData().clear();
        	/*
			 * Le calcul de total parcours la tableView donc
			 * faudrait que le calcul se fasse après le retrait de toute les lignes
			 */
			
        	totalLabel.setText(calculTotal());
        	totalLabelTTC.setText(calculTTC());
        	
        	//pour la gestion de showLignesCommandesDetails()
			selectedCodeCategorieList.clear();;
			selectedQuantie.clear();
			
	    }
    	
    }
    
    private String calculTotal() {
    	String total="";
    	try {
    		DecimalFormat format = new DecimalFormat("#,##0");
    		totalHt=0.0;
    		for(int i =0;i<lignesCommandesTable.getItems().size();i++) {
    			totalHt += Double.valueOf(lignesCommandesTable.getItems().get(i).gettotal());
    		}
    		total= format.format(totalHt)+ "€";
    		
    	}catch(Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("ERREUR");
	        alert.setHeaderText("Mauvais format de nombre");
	        alert.setContentText("");
	    	alert.showAndWait();
    	}
    	return total;
    }
    
    private String calculTTC() {
    	String total="";
    	try {
    		DecimalFormat format = new DecimalFormat("#,##0");
    		totalTtc=totalHt;
    		if(tva>=0)
    			totalTtc+=totalHt*tva; 
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
    
    private static String randomCommandeNumber() {
    	String num = "";
    	Random rand = new Random();
    	num+="FAC" + rand.nextInt(999);
    	return num;
    }
    
    private void showLignesCommandesDetails(LignesCommandes selectedLignesCommandes) {
    	if(article != null ) {
    		codeTextField.setText(selectedLignesCommandes.getcodeArticle());
	    	designationTextField.setText(selectedLignesCommandes.getdesignation());
	    	
	    	montantTextField.setText(Double.toString(selectedLignesCommandes.gettotal()));
	    	//quantiteComboBox.setItems(value);
	    	quantiteComboBox.setValue(selectedLignesCommandes.getquantite());
	    	
    	}
    	
    }
    
    
    /*
     *AVEC LE SERVEUR TOMCAT 
     * 
    public double getTva() {
    	TaxeProxy tvaWeb = new TaxeProxy();
		try {
			Double tva = tvaWeb.getTVA();
			return tva;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return -1;
    }
     */
    
    public void getTva() {
    	try {
	    	MonService port = new MonServiceService().getMonServicePort();
			this.tva = port.getTva();
	    	tvaValueLabel.setText((new DecimalFormat("#,##0")).format(tva*100)+"%");
	    	
    	}catch(Exception e) {
    		System.out.println("rien");
    		this.tva = 0.0;
    		tvaValueLabel.setText("Indisponible");
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
	this.dialogStage.close();
	mainApp.showFenMenuPrincipal();
}

}
