package gest.model;

//Pour la structure acceuil des donnees
import java.util.ArrayList;
//Pour la gestion de la propriété date
import java.util.Date;
//Pour la connexion base de donnees
import java.sql.Connection;
import connection.ControleConnexion;
//pour les requetes SQL
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Client extends Personne{
	// PROPRIETES
	// =======
	// Proprietes de base de la classe
	private boolean carte_fidele; 
	private Date date;
	// Propriete pour etablir la connexion avec la BD
	//----------------------------------
	private static Connection laConnexion = ControleConnexion.getConnexion();
	// propriete de type ArrayList qui contiendra les
	//enregistrements de la BD
	private final ArrayList<Client> lesEnreg = new ArrayList<Client>(); 
	// Getters de base
	// ---------
	public String getCode(){
		return code; 
	} 
	public String getNom(){
		return nom; 
	} 
	public String getPrenom() {
		return prenom; 
	} 
	public boolean isCarte_Fidele() {
		return carte_fidele; 
	} 
	public Date getDate_creation() { 
		return date; 
	} 
	// Getter pour transmettre 1'ArrayList 
	// ---------
	public ArrayList<Client> getlesEnreg(){
		return lesEnreg; 
	} 
	// Setters
	//-----------
	public void setCode(String code) {
		this.code = code; 
	} 
	public void setNom(String nom) {
		this.nom=nom; 
	} 
	public void setPrenom(String prenom){
		this.prenom = prenom; 
	} 
	public void 	setCarte_fidele(Boolean carte_fidele) {
		this.carte_fidele=carte_fidele; 
	} 
	public void setDate_creation(Date date_creation) {
		this.date = date_creation; 
	}
	
	// CONSTRUCTEURS
	// -------------
	// ler Constructeur
	public Client (String vCode, String vNom, String vPrenom, boolean vCarteFifele, Date vDateCreation) {
		super (vCode, vNom, vPrenom);
		this.code = vCode; this.nom = vNom;
		this.prenom = vPrenom; carte_fidele = vCarteFifele; 
		date = vDateCreation; 
	} 
	// 2eme Constructeur
	public Client(String vCode) {
		super(vCode);
		this.code = vCode; 
	}
	// 3eme constructeur
	public Client() {
		//lireRecupCRUD(); 
	}
	// 4eme constructeur
	public Client(String vNom, String vPrenom){
		nom = vNom;
		prenom = vPrenom; 
	}
}
