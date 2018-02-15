package gest.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Parametres  implements java.io.Serializable, Cloneable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String nomUtilisateur;
private String motDePasse;
private String serveurBD;
private String driverSGBD = "org.gjt.mm.mysql.Driver";
private String adresse;


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
	Parametres param = lecture();
	if (param == null){
		nomUtilisateur = "root";
		motDePasse = "toor";
		adresse="localhost";
	}else {
		nomUtilisateur = param.getNomUtilisateur();
		motDePasse = param.getMotDePasse();
		adresse=param.getAdresse();
	}
	
	// Ces identifiants sont ceux entrés dans la configuration du SGBDR
	// On l'initialise dans ce etat au debut du programme;
	serveurBD = "jdbc:mysql://"+adresse+"/gestcmandsapp"; 
	}
public Parametres lecture() {
	try {
			File fichier = new File("dbsetting.dat");
			ObjectInputStream flux = new ObjectInputStream(
			new FileInputStream(fichier));
			Parametres parametres = (Parametres) flux.readObject();
			flux.close();
			return parametres;
		} catch (IOException ioe) {
			//System.err.println(ioe);
		} catch (ClassNotFoundException cnfe) {
			//System.err.println(cnfe);
		}
	return null;
}
public Parametres (String nom, String mdp, String adresse,String serveurBD) { 
	this.nomUtilisateur = nom;
	this.motDePasse = mdp;
	this.adresse = adresse;
	this.serveurBD=serveurBD;
	}

}