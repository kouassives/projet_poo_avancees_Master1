package gest.view;

import connection.ControleConnexion;
import gest.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class FenConnexionController {
	
	 // Reference to the main application
    private MainApp mainApp;

    
    @FXML
    private TextField userNom;
    
    @FXML
    private TextField userMDP;
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public FenConnexionController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
    @FXML
    private void handleValider() {
    	controleConnexion_Appel();
    }
    
    @FXML
    private void handleQuitter() {
    	System.exit(0);
    }
    
    private void controleConnexion_Appel() {
    	//controle de la saisie
    	if(ControleConnexion.controle(userNom.getText(), userMDP.getText()))
    	if(ControleConnexion .getControleConnexion()){
    			mainApp.getPrimaryStage().hide();
    	    	mainApp.showFenMenuPrincipal();
    		}
    	else
	    {
	    	Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Connexion Base de Données");
	        alert.setContentText("Impossible de se connecter" + " à la base de données"+ "\n"+ "Vos nom et mot de passe sont corrects"+"\n"+"Mais les paramètres"+"\n"+"Pour le pilote"+"\n"+"Et la base de données"+"\n"+"doivent être vérifié"+"\n \n"+"Contactez le responsable informatique");
	    	alert.showAndWait();
	    }
    }
}