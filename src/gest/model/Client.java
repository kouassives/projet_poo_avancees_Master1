package gest.model;

//Pour la structure acceuil des donnees
import java.util.ArrayList;
//Pour la gestion de la propriété date

//Pour la connexion base de donnees
import java.sql.Connection;
import connection.ControleConnexion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//pour les requetes SQL
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


public class Client extends Personne{
	// PROPRIETES
	// =======
	// Proprietes de base de la classe
	private IntegerProperty carte_fidele; 
	private ObjectProperty<LocalDate> date;
	// Propriete pour etablir la connexion avec la BD
	//----------------------------------
	private static Connection laConnexion = ControleConnexion.getConnexion();
	// propriete de type ArrayList qui contiendra les
	//enregistrements de la BD
	private final ArrayList<Client> lesEnreg = new ArrayList<Client>(); 
	// Getters de base
	// ---------
	public String getCode(){
		return code.get(); 
	} 
	public String getNom(){
		return nom.get(); 
	} 
	public String getPrenom() {
		return prenom.get(); 
	}
	
	public IntegerProperty carte_FideleProperty() {
		return carte_fidele;
	}
	
	public int isCarte_Fidele() {
		return carte_fidele.get(); 
	} 
	
	public ObjectProperty<LocalDate> date_creationProperty() {
		return date;
	}
	public LocalDate getDate_creation() { 
		return date.get(); 
	} 
	// Getter pour transmettre 1'ArrayList 
	// ---------
	public ArrayList<Client> getlesEnreg(){
		return lesEnreg; 
	} 
	// Setters
	//-----------
	public void setCode(String code) {
		this.code.set(code);
	} 
	public void setNom(String nom) {
		this.nom.set(nom); 
	} 
	public void setPrenom(String prenom){
		this.prenom.set(prenom);
	} 
	public void setCarte_fidele(Integer carte_fidele) {
		this.carte_fidele.set(carte_fidele); 
	} 
	public void setDate_creation(LocalDate date_creation) {
		this.date.set(date_creation); 
	}
	
	// CONSTRUCTEURS
	// -------------
	// ler Constructeur
	public Client (String vCode, String vNom, String vPrenom, int carte_fidele, LocalDate date_creation) {
		super (vCode, vNom, vPrenom);
		this.code = new SimpleStringProperty(vCode);
		this.nom = new SimpleStringProperty(vNom);
		this.prenom = new SimpleStringProperty(vPrenom);
		this.carte_fidele = new SimpleIntegerProperty(carte_fidele);
		this.date = new SimpleObjectProperty<LocalDate>(date_creation);
	} 
	// 2eme Constructeur
	public Client(String vCode) {
		super(vCode);
		this.code = new SimpleStringProperty(vCode);
	}
	// 3eme constructeur
	public Client() {
		lireRecupCRUD(); 
	}
	// 4eme constructeur
	public Client(String vNom, String vPrenom){
		this.nom = new SimpleStringProperty(vNom);
		this.prenom = new SimpleStringProperty(vPrenom);
	}
	
	
	//Les méthodes CRUD
	public void lireRecupCRUD() {
		try {
			Statement state = laConnexion.createStatement();
			ResultSet rs = state.executeQuery("SELECT * "+"FROM clients ORDER BY nom");
			while(rs.next()) {
				String code= rs.getString("code");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				int carte_fidele;
				carte_fidele=rs.getInt("carte_fidele");
				LocalDate date_creation = rs.getDate("date").toLocalDate();
				
				//Ajout à l'ArrayList
				lesEnreg.add(new Client(code, nom, prenom, carte_fidele, date_creation));
				
			}
			
		}catch(SQLException e){
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Problème rencontré: ");
	        alert.setHeaderText("Resultat");
	        alert.setContentText( e.getMessage());
	    	alert.showAndWait();
		}
	}
	
	public boolean creerCRUD(String vCode, String vNom, String vPrenom, int vCarte_fidele, String vDate) {
		boolean bCreation = false;
		String requete = null;
		try {
			requete = "INSERT INTO clients(code, nom, prenom, carte_fidele, date,adresse) VALUES('" + vCode +"','"+ vNom  + "','" + vPrenom + "','"+ vCarte_fidele +"','"+ vDate+ "','Abidjan')";
			Statement state = laConnexion.createStatement();
			state.executeUpdate(requete);
			bCreation = true; 
		} 
		catch (SQLException e){
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Problème rencontré");
	        alert.setHeaderText("Ajout dans la BD non effectue : ");
	        alert.setContentText( e.getMessage());
	    	alert.showAndWait();
		}
		return bCreation; 
	}
	
	public boolean modifierCRUD(String vCode, String vNom, String vPrenom, int vCarte_fidele, String vDate) {
		boolean bModification = true;
		String requete = null;
		try {
			requete = "UPDATE clients SET " + "nom = '" + vNom +"'," + "prenom = '" + vPrenom + "'," + "carte_fidele = '" + vCarte_fidele+"'," + "date = '" + vDate + "'" + " WHERE code = '" + vCode + "'";
			Statement state = laConnexion.createStatement();
			state.executeUpdate(requete);
			state.close();
			} 
			catch (SQLException e) {
				bModification = false;
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Modification dans la BD non effectuee.");
		        alert.setHeaderText("Probleme rencontré: ");
		        alert.setContentText( e.getMessage());
		    	alert.showAndWait();
			}
			return bModification;
	}

	public boolean supprimerCRUD(String vCode){
		boolean bSuppression = true;
		String requete = null;
		// Verifier avant quill n'existe aucune commande
		try {
			String requeteClient = "SELECT count(*) AS nbLignes FROM commandes " + " WHERE code_client LIKE '" + vCode +"'";
			Statement state = laConnexion.createStatement();
			ResultSet jeuEnreg = state.executeQuery(requeteClient);
			int nbLignes=0;
			jeuEnreg.next(); 
			nbLignes = jeuEnreg.getInt("nbLignes");
			if (nbLignes > 0) {
				bSuppression = false;
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Resultat");
		        alert.setHeaderText("");
		        alert.setContentText("Il existe des commandes pour ce client." + " Suppression interdite.");
		    	alert.showAndWait();
				bSuppression = false;
				}
				else{
					Alert alert = new Alert(AlertType.ERROR);
    		        alert.setTitle("Resultat");
    		        alert.setHeaderText("");
    		        alert.setContentText("Aucune commande pour ce client." + " Suppression autorisee.");
    		    	alert.showAndWait();
				}
			}
			catch (SQLException e){
				bSuppression = false;
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Probleme rencontre");
		        alert.setHeaderText("Aucune suppression effectuee dans la BD : ");
		        alert.setContentText( e.getMessage());
		    	alert.showAndWait();
			}
		    if (bSuppression == true) {
		    	try {
		    			requete = "DELETE FROM clients" + " WHERE code = '"+vCode+"'";
		    			Statement state = laConnexion.createStatement();
		    			int nbEnregSup = state.executeUpdate(requete);
		    			if (nbEnregSup == 0){
		    				Alert alert = new Alert(AlertType.ERROR);
		    		        alert.setTitle("Resultat");
		    		        alert.setHeaderText("");
		    		        alert.setContentText("Aucun enregistrement correspondant.");
		    		    	alert.showAndWait();
		    			}
		    		}
		    		catch (SQLException e){
		    			bSuppression = false;
		    			Alert alert = new Alert(AlertType.ERROR);
				        alert.setTitle("Probleme rencontre");
				        alert.setHeaderText("Aucune suppression effectuee dans la BD : ");
				        alert.setContentText( e.getMessage());
				    	alert.showAndWait();
		    		}
		    	}
				return bSuppression; 
		}
	
	public ArrayList<Client> chercherCRUD(String vCode,String vNom, String vPrenom){
		if(vCode.equals("")){
			vCode = "%";
			}
		if(vNom.equals("")){
			vNom = "%"; 
		}
		if(vPrenom.equals("")){
			vPrenom = "%"; 
		} 
		String requete = "SELECT * FROM clients" + " WHERE code LIKE '" + vCode +"'" + " AND nom LIKE '" + vNom + "'" + " AND prenom LIKE '" + vPrenom + "'"; 
		try {
			Statement state = laConnexion.createStatement();
			ResultSet rs = state.executeQuery(requete);
			while (rs.next()) {
				String code = rs.getString("code");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				int carte_fidele;
				carte_fidele=rs.getInt("carte_fidele");
				LocalDate date_creation = rs.getDate("date").toLocalDate();
				// ajout a l'Arraylist
				
				lesEnreg.add(new Client (code, nom, prenom, carte_fidele, date_creation)); 
			}
		} catch (SQLException e){
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Probleme rencontre");
	        alert.setHeaderText("Probleme rencontre pendant la recherche : ");
	        alert.setContentText( e.getMessage());
	    	alert.showAndWait();
		}
		return lesEnreg; 
	}

	public ArrayList<Client> chercherCRUD(String recherche) {
		String requete = "SELECT * FROM clients"
	+ " WHERE code LIKE '%" + recherche +"%'"
	+ " OR nom LIKE '%" + recherche + "%'"
	+ " OR prenom LIKE '%" + recherche + "%'";
	try {
		Statement state = laConnexion.createStatement();
		ResultSet rs = state.executeQuery(requete);
		while (rs.next()) {
			String code = rs.getString("code");
			String nom = rs.getString("nom");
			String prenom = rs.getString("prenom");
			int carte_fidele;
			carte_fidele=rs.getInt("carte_fidele");
			LocalDate date_creation = rs.getDate("date").toLocalDate();
			
			// ajout a l'Arraylist
			lesEnreg.add(new Client(code, nom, prenom, carte_fidele, date_creation)); 
		}
		}catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Resultat");
	        alert.setHeaderText("Problème rencontré pendant la recherche ");
	        alert.setContentText( e.getMessage());
	    	alert.showAndWait();
		}
		return lesEnreg; 
	}
}
