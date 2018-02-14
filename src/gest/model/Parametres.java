package gest.model;

import java.io.File;

import java.util.ArrayList;

import gest.view.FenUtilisateurDBController;

public class Parametres {
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



}