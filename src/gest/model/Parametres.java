package gest.model;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ControleConnexion;
import gest.view.FenUtilisateurDBController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Parametres {
private String nomUtilisateur;
private String motDePasse;
private String serveurBD;
private String driverSGBD = "org.gjt.mm.mysql.Driver";
private String adresse;

private static Connection laConnexion = ControleConnexion.getConnexion();
// propriete de type ArrayList qui contiendra les
//enregistrements de la BD
private final ArrayList<Parametres> lesEnreg = new ArrayList<Parametres>(); 


public String getNomUtilisateur() {
	return nomUtilisateur;
}
public void setNomUtilisateur(String nomUtilisateur) {
	this.nomUtilisateur = nomUtilisateur;
}
public String getMotDePasse() {
	return motDePasse;
}
public void setMotDePasse(String motDePasse) {
	this.motDePasse = motDePasse;
}
public String getServeurBD() {
	return serveurBD;
}
public void setServeurBD(String serveurBD) {
	this.serveurBD = serveurBD;
}
public String getDriverSGBD() {
	return driverSGBD;
}
public void setDriverSGBD(String driverSGBD) {
	this.driverSGBD = driverSGBD;
} 

public String getAdresse() {
	return adresse;
}
public void setAdresse(String adresse) {
	this.adresse = adresse;
}
//constructeur
public Parametres () {
	boolean verif = false;
	try{
		File file = FenUtilisateurDBController.getPersonFilePath();
		if (file!=null)
			{
			FenUtilisateurDBController.loadPersonDataFromFile(file);
			ArrayList<Parametres> param = FenUtilisateurDBController.getUserData();
			nomUtilisateur = param.get(0).getNomUtilisateur();
			motDePasse = param.get(0).getMotDePasse();
			adresse=param.get(0).getAdresse();
			verif=true;
			}
		}catch(Exception e)
	{
			e.printStackTrace();
	}
	if (!verif){
		nomUtilisateur = "root";
		motDePasse = "toor";
		adresse="localhost";
	}
	
	// Ces identifiants sont ceux entrés dans la configuration du SGBDR
	// On l'initialise dans ce etat au debut du programme;
	serveurBD = "jdbc:mysql://"+adresse+"/gestcmandsapp"; 
	}

public Parametres (String nom, String mdp, String adresse,String serveurBD) { 
	this.nomUtilisateur = nom;
	this.motDePasse = mdp;
	this.adresse = adresse;
	this.serveurBD=serveurBD;
	}

public void lireRecupCRUD() {
	try {
		while(true /* lecture dans le fichier xml*/) {
			//Ajout à l'ArrayList
			lesEnreg.add(new Parametres(/*nom,mdp,adresse*/));
		}
	}catch(Exception e){
		Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Problème rencontré: ");
        alert.setHeaderText("Resultat");
        alert.setContentText( e.getMessage());
    	alert.showAndWait();
	}
	}

}