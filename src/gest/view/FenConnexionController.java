package gest.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ControleConnexion;
import gest.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

public class FenConnexionController {
	// Propriete pour etablir la connexion avec la BD
	//----------------------------------
	private MainApp mainApp;

    
    @FXML
    private TextField userNom;
    
    @FXML
    private PasswordField userMDP;
    
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
    	/*
    	 * Pour lancer le constructeur static de ControleConnexion. ce qui
    	 * chargera statiquement dans la memoire les donnees de Parametres
    	 */
    	new ControleConnexion();
    }
    
    @FXML
    private void handleValider() {
    	controleConnexion_Appel();
    }
    
    @FXML
    private void handleDataBase() {
    	mainApp.showFenUtilisateurDB();
    }
    
    
    @FXML
    private void handleQuitter() {
    	System.exit(0);
    }
    
    private void controleConnexion_Appel() {
    	try {
    		String nom = userNom.getText();
    		String mot = userMDP.getText();
    		if(nom.equals("") || nom.length()==0)
    			{
    			Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Le nom est vide");
		    	alert.showAndWait();
    		}else if(mot.equals("") || mot.length()==0 ){
    			Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Le mot de passe est vide");
		    	alert.showAndWait();
    		}else
    		{
    			Statement state = ControleConnexion.getConnexion().createStatement();
    			ResultSet rs = state.executeQuery("SELECT mdp,count(*) AS nombre "+"FROM utilisateurdb WHERE nomutilisateurdb = '"+ nom+"'");
    			rs.next();
    			int nombre = rs.getInt("nombre");
    			if( nombre == 1 )
    			{
	    			String mdp = rs.getString("mdp");
	    			if (mdp.equals(userMDP.getText())) {
	    				//controle de la saisie
	    		    	if(ControleConnexion .getControleConnexion()){
	    		    			mainApp.getPrimaryStage().close();
	    		    			/*
	    		    			 * l'instruction suivante est primordiale pour que
	    		    			 * les donnees (les listes)des clients, commandes et produits soient chargé dans l'application
	    		    			 * 
	    		    			 */
	    		    			mainApp.chargerLesDonnees();
	    		    			
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
	    			else {
	    				Alert alert = new Alert(AlertType.ERROR);
				        alert.setTitle("Error de connexion");
				        alert.setHeaderText("Le mot de passe est incorrect");
				        alert.showAndWait();
	    			}
    			}else {
    				Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("Error de connexion");
			        alert.setHeaderText("Information incorrectes");
			        alert.showAndWait();
    				
    			}
    		}
    		
		}
		catch (SQLException e) {
    			Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("");
		        alert.setHeaderText("");
		        alert.setContentText( e.getMessage());
		    	alert.showAndWait();
		}
    }
}