package connection;

 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.jdbc.Connection;

import gest.model.Parametres;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControleConnexion {
	static Parametres lesParametres;
	static boolean etatConnexion;
	static Connection laConnectionStatique; 
static {
	boolean ok = true;
	lesParametres = new Parametres(); 
	try 
 { 
		Class.forName(lesParametres.getDriverSGBD());
		etatConnexion = true;
 }
catch(ClassNotFoundException e)
	{
	Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Chargement de Pilotes JDBC");
    alert.setContentText("Classes non trouvees" + " pour le chargement " + "du pilote JDBC MySQL");
    ok = false;
    etatConnexion = false;
    alert.showAndWait();
	}
	if (ok == true){ 
		try { 
			String urlBD = lesParametres.getServeurBD();
			String nomUtilisateur = lesParametres.getNomUtilisateur();
			String MDP = lesParametres.getMotDePasse();
			// Creation d'une connexion
			// contenant les parametres de connexion
			laConnectionStatique = (Connection) DriverManager.getConnection(urlBD, nomUtilisateur, MDP);
			etatConnexion = true;
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText("Connection");
		    alert.setContentText("Impossible de se connecter" + " a la base de données ");
			etatConnexion = false;
			alert.showAndWait();
		}
	}
}

public static Parametres getParametres(){
	return lesParametres; 
}

public static boolean getControleConnexion(){
	return etatConnexion;
}

public static Connection getConnexion() {
	return laConnectionStatique;
	}
public static boolean controle(String Nom, String MotDePasse){
	boolean verificationSaisie; 
	if (Nom.equals(lesParametres.getNomUtilisateur()) && MotDePasse.equals(lesParametres.getMotDePasse())){
		verificationSaisie = true;
	}
	else
	{
		Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setHeaderText("Mauvais identifiants saisis");
	    alert.setContentText("Verifier votre saisie");
		etatConnexion = false;
		verificationSaisie = false;
		alert.showAndWait();
		
	}
return verificationSaisie;

}

public static void fermetureSession() {
	try {
		laConnectionStatique.close(); 
	}
catch (SQLException e) {
	Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Fermeture Connection");
    alert.setContentText("Problème rencontré" + " à la fermeture de la connexion");
	etatConnexion = false;
	alert.showAndWait();
		
	}
}

public static void transfertDonnees() throws SQLException {
	// simple parcours de jeu d'enregistrements
	System.out.println("Parcours du jeu d'enregistrements");
	System.out.println("  "); 
	//Interface Statement : pour transmettre
	// des instructions SQL simples
	// la fermeture d'un Statement engendre la fermeture
	// automatique de tous les ResultSet associes
	Statement leStatement = null;
	// jeu d'enregistrements
	ResultSet jeuEnreg = null;
	// variables
	String vCode = "";
	String vNom = "";
	String vPrenom = "";
	String chaineSQL = "select * from clients"; 
	leStatement = laConnectionStatique.createStatement();
	jeuEnreg = leStatement.executeQuery(chaineSQL); 
	while (jeuEnreg.next()) {
		// utilisation des n° de colonnes
		vCode = jeuEnreg.getString(1);
		vNom = jeuEnreg.getString(2);
		// ou des noms de colonnes
		vPrenom = jeuEnreg.getString("prenom"); 
		System.out.println(vCode+", "+ vNom+", "+ vPrenom );
	}
}

}