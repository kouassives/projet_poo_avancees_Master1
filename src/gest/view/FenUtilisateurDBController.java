package gest.view;

import java.io.File;
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

	private static ArrayList<Parametres> userData = new ArrayList<Parametres>();
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
	        /* Pour Recuperer les donner et les mettre dans une tableview
	         * On peut recuperer seulement la liste des clients avec mainApp.getClientData() 
	         * Pour inserer les donner dans la table de la fenetre associer a ce controlleur
	         * dataTable.setItems(mainApp.getClientData());
	         */
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
 * Returns the person file preference, i.e. the file that was last opened.
 * The preference is read from the OS specific registry. If no such
 * preference can be found, null is returned.
 * 
 * @return
 */
public static File getPersonFilePath() {
    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    String filePath = prefs.get("filePath", null);
    if (filePath != null) {
        return new File(filePath);
    } else {
        return null;
    }
}

/**
 * Sets the file path of the currently loaded file. The path is persisted in
 * the OS specific registry.
 * 
 * @param file the file or null to remove the path
 */
public static void setPersonFilePath(File file) {
    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    if (file != null) {
        prefs.put("filePath", file.getPath());

    } else {
        prefs.remove("filePath");

    }
}

public static ArrayList<Parametres> getUserData() {
	return userData;
}

public static void setUserData(ArrayList<Parametres> userData) {
	FenUtilisateurDBController.userData = userData;
}

/**
 * Loads person data from the specified file. The current person data will
 * be replaced.
 * 
 * @param file
 */
public static void loadPersonDataFromFile(File file) {
    try {
        JAXBContext context = JAXBContext
                .newInstance(UtilisateurDB.class);
        Unmarshaller um = context.createUnmarshaller();

        // Reading XML from the file and unmarshalling.
        UtilisateurDB wrapper = (UtilisateurDB) um.unmarshal(file);

        userData.clear();
        userData.addAll(wrapper.getPersons());

        // Save the file path to the registry.
        setPersonFilePath(file);

    } catch (Exception e) { // catches ANY exception
        
        }
}

/**
 * Saves the current person data to the specified file.
 * 
 * @param file
 */
public void savePersonDataToFile(File file) {
    try {
        JAXBContext context = JAXBContext
                .newInstance(UtilisateurDB.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Wrapping our person data.
        UtilisateurDB wrapper = new UtilisateurDB();
        wrapper.setPersons(userData);

        // Marshalling and saving XML to the file.
        m.marshal(wrapper, file);

        // Save the file path to the registry.
        setPersonFilePath(file);
    } catch (Exception e) { // catches ANY exception
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not save data");
        alert.setContentText("Could not save data to file:\n" + file.getPath());

        alert.showAndWait();
    }
}

/**
 * Opens a FileChooser to let the user select a file to save to.
 */
@FXML
private void handleSaveAs() {
    FileChooser fileChooser = new FileChooser();

    // Set extension filter
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
            "XML files (*.xml)", "*.xml");
    fileChooser.getExtensionFilters().add(extFilter);

    // Show save file dialog
    File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

    if (file != null) {
        // Make sure it has the correct extension
        if (!file.getPath().endsWith(".xml")) {
            file = new File(file.getPath() + ".xml");
        }
        savePersonDataToFile(file);
    }
}


@FXML
private void handleSave() {
	userData.clear();
	serveurBD = "jdbc:mysql://"+serverAdress.getText()+"/gestcmandsapp"; 
	Parametres param = new Parametres(userNom.getText(),userMDP.getText(),serverAdress.getText(),serveurBD);
	userData.add(param);
	ControleConnexion.editLaconnexionStatique(param.getServeurBD(), param.getNomUtilisateur(), param.getMotDePasse());
	ControleConnexion.setLesParametres(param);
	
	File personFile = getPersonFilePath();
    if (personFile != null) {
        savePersonDataToFile(personFile);
    } else {
        handleSaveAs();
    }
}

@FXML
private void handleBack() {
	this.dialogStage.close();
}
	
}
