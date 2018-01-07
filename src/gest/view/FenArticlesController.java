package gest.view;

import java.time.LocalDate;
import java.util.ArrayList;

import gest.MainApp;
import gest.model.Article;
import gest.model.Client;
import gest.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FenArticlesController {
	private MainApp mainApp;
	
	

	@FXML
    private TableView<Article> articleTable;
	@FXML
    private TableColumn<Article, String> code;
	@FXML
    private TableColumn<Article, String> designation;
    @FXML
    private TableColumn<Article, Number> quantite;
    @FXML
    private TableColumn<Article, String> codeCategorie;
    @FXML
    private TableColumn<Article, Number> prixUnitaire;


    @FXML
    private TextField codeTextField;
    @FXML
    private TextField designationTextField;
    @FXML
    private TextField quantiteTextField;
    @FXML
    private TextField codeCategorieTextField;
    @FXML
    private TextField prixUnitaireTextField;
    @FXML
    private TextField rechercherTextField;

	
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	code.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
    	designation.setCellValueFactory(cellData -> cellData.getValue().designationProperty());
    	quantite.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty());
    	codeCategorie.setCellValueFactory(cellData -> cellData.getValue().codeCategorieProperty());
    	prixUnitaire.setCellValueFactory(cellData -> cellData.getValue().prixUnitaireProperty());

    	articleTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> showArticleDetails(newValue));
    }
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        articleTable.setItems(mainApp.getArticleData());
    }
    
    private void showArticleDetails(Article article) {
    	if(article != null) {
    		codeTextField.setText(article.getCode());
    		designationTextField.setText(article.getDesignation());
    		quantiteTextField.setText(String.valueOf(article.getQuantite()));
    		codeCategorieTextField.setText(article.getCodeCategorie());
    		prixUnitaireTextField.setText(String.valueOf(article.getPrix_unitaire()));
    	}
    	else {
    		codeTextField.setText("");
    		designationTextField.setText("");
    		quantiteTextField.setText("");
    		codeCategorieTextField.setText("");
    		prixUnitaireTextField.setText("");
    	}
    }

@FXML
private void handleAddArticle() {
	String vCode = codeTextField.getText();
	if(!vCode.equals("")) {
		String vCodeCategorie = codeCategorieTextField.getText();
		String vDesignation = designationTextField.getText();
		int vQuantite = Integer.valueOf(quantiteTextField.getText());
		double vPrixUnitaire= Double.valueOf(prixUnitaireTextField.getText());
		LocalDate vDate = LocalDate.now();
		
		Article unArticle = new Article(vCode,vCodeCategorie,vDesignation,vQuantite,vPrixUnitaire,vDate);
		boolean bCreation = unArticle.creerCRUD(vCode, vCodeCategorie, vDesignation, vQuantite, vPrixUnitaire, vDate);
		
		if(bCreation) {
			mainApp.getArticleData().add(unArticle);
			
			codeTextField.setText("");
    		designationTextField.setText("");
    		quantiteTextField.setText("");
    		codeCategorieTextField.setText("");
    		prixUnitaireTextField.setText("");
    		codeTextField.requestFocus();
		}
	}
	else {
		// Show the error message.
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Champs Vide");
        alert.setHeaderText("Les champs suivants sont obligatoire");
        alert.setContentText("Code");

        alert.showAndWait();
	}
}

    
@FXML
private void handleCleanFields() {
	codeTextField.setText("");
	designationTextField.setText("");
	quantiteTextField.setText("");
	codeCategorieTextField.setText("");
	prixUnitaireTextField.setText("");
}


@FXML
private void handleEditArticle() {
	String vCode = codeTextField.getText();
	if (!(vCode.equals("")|| vCode.length()==0)) {
		String vCodeCategorie = codeCategorieTextField.getText();
		String vDesignation = designationTextField.getText();
		int vQuantite = Integer.valueOf(quantiteTextField.getText());
		double vPrixUnitaire= Double.valueOf(prixUnitaireTextField.getText());
		LocalDate vDate = LocalDate.now();
		
		Article unArticle = new Article(vCode,vCodeCategorie,vDesignation,vQuantite,vPrixUnitaire,vDate);
		boolean bCreation = unArticle.modifierCRUD(vCode, vCodeCategorie, vDesignation, vQuantite, vPrixUnitaire);
		if(bCreation) {
			Article selectedArticle = articleTable.getSelectionModel().getSelectedItem();
		    selectedArticle.setCode(unArticle.getCode());
		    selectedArticle.setCodeCategorie(unArticle.getCodeCategorie());
		    selectedArticle.setDesignation(unArticle.getDesignation());
		    selectedArticle.setQuantite(unArticle.getQuantite());
		    selectedArticle.setPrix_unitaire(unArticle.getPrix_unitaire());
		    selectedArticle.setDate(unArticle.getDate());
		}
		
	}
	else {
		// Show the error message.
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Champs Vide");
        alert.setHeaderText("Les champs suivants sont obligatoire");
        alert.setContentText("Code");
        
        alert.showAndWait();
	}
}


/**
 * Called when the user clicks on the delete button.
 */
@FXML
private void handleDeleteArticle() {
    int selectedIndex = articleTable.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
    	Article selectedArticle = articleTable.getItems().get(selectedIndex);
    	
    	//Affichage d'une fenetre de confirmation pour la suppression du client
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Supprimer l'article dont le code est:  "+ selectedArticle.getCode() + "?",ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult()==ButtonType.YES) {
	    	boolean okdatabase = selectedArticle.supprimerCRUD(selectedArticle.getCode());
	        if (okdatabase)
	    	articleTable.getItems().remove(selectedIndex);
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


/**
 * Appelé lorsqu'on clique sur le bouton recherhcer
 */
@FXML
private void handleRechercherClient() {
	if(!(rechercherTextField.getText().equals("") || rechercherTextField.getText().length()==0))
	{
		Article unArticle= new Article();
		/*
		 * On va effectuer un clear() pour effacer
		 * les données initiale qui sont les recupperation
		 * fait dans la base de données avec lireRecup de la classe Article
		 * 
		 */
		
		unArticle.getLesEnreg().clear();
		ArrayList<Article> nouvelleListe = unArticle.chercherCRUD(rechercherTextField.getText());
		ObservableList<Article> nouvelleListeDataObservale = FXCollections.observableArrayList(nouvelleListe);
		
		/*
		 * On met les données de la recherche dans la TableView articleTable
		 */
		
		articleTable.setItems(nouvelleListeDataObservale);

	}
	else {
		articleTable.getItems().clear();
		articleTable.setItems(mainApp.getArticleData());
	}
}
    

@FXML
private void handleAnnuler() {
	System.out.println(articleTable.getItems().size());
	System.out.println(mainApp.getArticleData().size());
	for (int i = 0; i<articleTable.getItems().size();i++) {
		articleTable.getItems().remove(i);
	}
	System.out.println(articleTable.getItems().size());
	System.out.println(mainApp.getArticleData().size());
	
	articleTable.setItems(mainApp.getArticleData());
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
