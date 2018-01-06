package gest.view;

import java.util.ArrayList;

import gest.MainApp;
import gest.model.Article;
import gest.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FenChoixArticleController {

	@FXML
    private TableView<Article> articleTable;
	@FXML
    private TableColumn<Article, String> code;
	@FXML
    private TableColumn<Article, String> codeCategorie;
    @FXML
    private TableColumn<Article, String> designation;
    @FXML
    private TableColumn<Article, Number> quantite;
    @FXML
    private TableColumn<Article, Number> prixUnitaire;


    @FXML
    private TextField rechercherTextField;
    
    
    
    private Article article;
	private MainApp mainApp;
	private boolean rowDoubleClicked = false;


	  /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    	
	    	code.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
	    	codeCategorie.setCellValueFactory(cellData -> cellData.getValue().codeCategorieProperty());
	    	designation.setCellValueFactory(cellData -> cellData.getValue().designationProperty());
	    	quantite.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty());
	    	prixUnitaire.setCellValueFactory(cellData -> cellData.getValue().prixUnitaireProperty());
		    
	    	/*
    		 * Gestion du doubleclic sur une ligne de la table
    		 * On procède à l'ajout d'un ecouteur d'evemenet 
    		 * sur les ligne de la table
    		 */
    		articleTable.setRowFactory( tv ->{
    			TableRow<Article> row = new TableRow<>();
    			row.setOnMouseClicked(event -> {
    				if(event.getClickCount()==2 && (! row.isEmpty())){
    				Article rowData = row.getItem();
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
            articleTable.setItems(FXCollections.observableArrayList(new Article().getLesEnreg()));
            
    
	    }
	    
	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }

	    public void setArticle(Article article) {
	    	this.article = article;
	    }
	    
	    @FXML
	    private void handleRechercherClient() {
	    	Article unArticle = new Article();
	        unArticle.getLesEnreg().clear();
	        
	        ArrayList<Article> nouvelleListe = unArticle.chercherCRUD(rechercherTextField.getText());
	        ObservableList<Article> nouvelleListeObservale = FXCollections.observableArrayList(nouvelleListe);
	        
	        articleTable.setItems(nouvelleListeObservale);
	    }

	    
	    private void handleTableViewRowsDoubleCliked(Article varticle)
	    {
	    	article.setCode(varticle.getCode());
	    	article.setCodeCategorie(varticle.getCodeCategorie());
	    	article.setDesignation(varticle.getDesignation());
	    	article.setQuantite(varticle.getQuantite());
	    	article.setPrix_unitaire(varticle.getPrix_unitaire());
	    	
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
