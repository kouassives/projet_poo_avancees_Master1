package gest.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import connection.ControleConnexion;
import gest.MainApp;
import  javax.xml.bind.Marshaller;

import gest.model.Parametres;
import gest.model.UtilisateurDB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FenUtilisateurDBController {

	private MainApp mainApp;


    
    @FXML
    private TextField userNom;
    
    @FXML
    private PasswordField userMDP;
    
    @FXML
    private TextField serverAdress;
    
    private String serveurBD;
    
	  /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    	userNom.setText(ControleConnexion.getParametres().getNomUtilisateur());
	    	userMDP.setText(ControleConnexion.getParametres().getMotDePasse());
	    	serverAdress.setText(ControleConnexion.getParametres().getAdresse());
	    }
	    
	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
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

/**
 * Saves the current person data to the specified file.
 * 
 * @param file
 */
public void ecriture(Parametres param) {
	try {
		File fichier = new File("dbsetting.dat");
		ObjectOutputStream flux = new ObjectOutputStream(
		new FileOutputStream(fichier));
		flux.writeObject(param);
		flux.close();
		}
	catch (IOException ioe) {
				System.err.println(ioe);
		}

}


@FXML
private void handleSave() {
	serveurBD = "jdbc:mysql://"+serverAdress.getText()+"/gestcmandsapp"; 
	Parametres param = new Parametres(userNom.getText(),userMDP.getText(),serverAdress.getText(),serveurBD);
	ControleConnexion.editLaconnexionStatique(param.getServeurBD(), param.getNomUtilisateur(), param.getMotDePasse());
	ControleConnexion.setLesParametres(param);
	ecriture(param);

}

@FXML
private void handleBack() {
	this.dialogStage.close();
}
	
}
