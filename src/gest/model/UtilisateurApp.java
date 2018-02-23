package gest.model;

import java.util.ArrayList;

public class UtilisateurApp {
	String nom;
	String prenom;
	String pseudo;
	String motDePass;
	String cni;
	
	public UtilisateurApp() {
		list();
	}
	public UtilisateurApp(String pseudo,String mdp,String nom,String prenom,String cni) {
		this.pseudo=pseudo;
		this.nom=nom;
		this.motDePass=mdp;
		this.nom=nom;
		this.prenom=prenom;
		this.cni=cni;
	}
	private ArrayList<UtilisateurApp> listUtilisateur = new ArrayList<UtilisateurApp>();
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getPeusdo() {
		return pseudo;
	}
	public void setPeusdo(String peusdp) {
		this.pseudo = peusdp;
	}
	public String getMotDePass() {
		return motDePass;
	}
	public void setMotDePass(String motDePass) {
		this.motDePass = motDePass;
	}
	
	public String getCni() {
		return cni;
	}
	public void setCni(String cni) {
		this.cni = cni;
	}
	public ArrayList<UtilisateurApp> getListUtilisateur() {
		return listUtilisateur;
	}
	public void setListUtilisateur(ArrayList<UtilisateurApp> listUtilisateur) {
		this.listUtilisateur = listUtilisateur;
	}
	
	public ArrayList<UtilisateurApp> list(){
		listUtilisateur.add(new UtilisateurApp("kouassives","a","a","a","a"));
		listUtilisateur.add(new UtilisateurApp("kouassives","a","a","a","a"));
		return listUtilisateur;
	}
	
	
	public boolean creer(UtilisateurApp user) {
		
		return false;
	}
	
	public boolean modifier(UtilisateurApp user) {
		
		return false;
	}
	
	
}

