package gest.view;

import gest.MainApp;
import gest.model.Article;
import gest.model.Client;
import gest.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
