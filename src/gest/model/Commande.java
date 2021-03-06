package gest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import connection.ControleConnexion;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Commande {
//propriétés
//----------
	private StringProperty code;
	private StringProperty code_client;
	private StringProperty nomPrenom_client;
	private DoubleProperty total_ttc;
	private IntegerProperty codeModeReglement;
	private StringProperty mode_reglement;
	private ObjectProperty<LocalDate> date;
	
	//Pour obtenir la connexion statique
	private static Connection laConnexion = ControleConnexion.getConnexion();
	//pour stocker les enregistrements de la BD
	private final ArrayList<Commande> lesEnreg = new ArrayList<Commande>();
	//Constructeurs
	//----------
	//Constructeur 1
	public Commande(String code, String code_client,String nomPrenom_client, double total_ttc, String mode_reglement, LocalDate date) {
		this.code = new SimpleStringProperty(code);
		this.code_client = new SimpleStringProperty(code_client);
		this.nomPrenom_client = new SimpleStringProperty(nomPrenom_client);
		this.total_ttc = new SimpleDoubleProperty(total_ttc);
		this.mode_reglement = new SimpleStringProperty(mode_reglement);
		this.date = new SimpleObjectProperty<LocalDate>(date);
	}
	//Constructeur 2
	public Commande() {
		lireRecupCRUD();
	}
	//Constructeur 3 pour initier la recherche
	public Commande(String code) {
	}
	
	
	// Accesseurs
	//-----------
	public String getCode() {
		return code.get();
	}
	public StringProperty CodeProperty() {
		return code;
	}
	
	public String getCode_client() {
		return code_client.get();
	}
	public StringProperty Code_clientProperty() {
		return code_client;
	}
	
	public String getNomPrenom_client() {
		return nomPrenom_client.get();
	}
	public StringProperty NomPrenom_clientProperty() {
		return nomPrenom_client;
	}
	
	public double getTotal_ttc() {
		return total_ttc.get();
	}
	public DoubleProperty Total_ttcProperty() {
		return total_ttc;
	}
	
	public int getCodeModeReglement() {
		return codeModeReglement.get();
	}
	public IntegerProperty CodeModeReglementProperty() {
		return codeModeReglement;
	}
	
	public String getMode_reglement() {
		return mode_reglement.get();
	}
	public StringProperty Mode_reglementProperty() {
		return mode_reglement;
	}
	
	public LocalDate getDate() {
		return date.get();
	}
	public ObjectProperty<LocalDate> DateProperty() {
		return date;
	}
	
	public ArrayList<Commande> getLesEnreg() {
		return lesEnreg;
	}
	
	public void lireRecupCRUD() {
		try {
			Statement state = laConnexion.createStatement();
			ResultSet rs = state.executeQuery("SELECT com.code," +
			" com.total_ttc, com.date, cli.nom,cli.code, " +
			"cli.prenom, mode.type "+
			"FROM commandes AS com, clients AS cli, "+
			"mode_reglements AS mode " +
			"WHERE com.code_mode_reglement = mode.code " +
			"AND com.code_client = cli.code");
			while (rs.next()) { 
				// Informations generales commande
				String code = rs.getString("com.code");
				double total_ttc = rs.getDouble("total_ttc");
				LocalDate date = rs.getDate("date").toLocalDate(); 
				// Informations client
				String codeClient = rs.getString("cli.code");
				String nomPrenom_client = rs.getString("cli.nom")+ " "+ rs.getString("cli.prenom");
				// Information mode reglement
				String mode_reglement = rs.getString("mode.type"); 
				lesEnreg.add(new Commande(code,
						codeClient,
						nomPrenom_client,
						total_ttc,
						mode_reglement, 
						date));
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("ALERTE");
	        alert.setHeaderText("Probleme rencontre");
	        alert.setContentText(e.getMessage());
	    	alert.showAndWait();
	    }
	}
	
	public boolean creerCRUD(String vCode, String vCode_Client, double vTotalTTC, int vCode_Mode_Reglement) {
		boolean bCreation = false;
		String requete = null;
		try {
			requete = "INSERT INTO commandes VALUES (?,?,?,?,NOW())"; 
	
			PreparedStatement prepare = laConnexion.prepareStatement(requete);
			prepare.setString(1, vCode);
			prepare.setString(2, vCode_Client);
			prepare.setDouble(3, vTotalTTC);
			prepare.setInt (4, vCode_Mode_Reglement); 
			prepare.executeUpdate();
			bCreation = true;
		}
		catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Probleme rencontre");
	        alert.setHeaderText("Ajout dans la BD non effectue : ");
	        alert.setContentText(e.getMessage());
	    	alert.showAndWait();
		}
		return bCreation;
	}
	
	public boolean modifierCRUD(String vCode, String vCode_Client, double vTotalTTC, int vCode_Mode_Reglement) { 
		boolean bModification = true;
		String requete = null;
		try {
				requete = "UPDATE commandes SET " +
				"code client = ?, " +
				"total ttc = ?, " +
				"code_mode_reglement = ? "+
				"WHERE code = ?"; 
			
				PreparedStatement prepare = laConnexion.prepareStatement(requete);
				prepare.setString(1, vCode_Client);
				prepare.setDouble(2, vTotalTTC);
				prepare.setInt (3, vCode_Mode_Reglement);
				prepare.setString(4, vCode); 
				
				prepare.executeUpdate();
				bModification = true; 
			} 
			catch (SQLException e) {
				bModification = false;
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Probleme rencontre");
		        alert.setHeaderText("Modification dans la BD non effectuee : ");
		        alert.setContentText(e.getMessage());
		    	alert.showAndWait();
			} 
			return bModification; 
	}
	
	public boolean supprimerCRUD(String vCode){
		boolean bSuppression = true;
		String requete = null;
		try {
			requete = "DELETE commandes , lignes_commandes FROM commandes, lignes_commandes " + "WHERE commandes.code = lignes_commandes.code_commande AND code = ?";
			PreparedStatement prepare = laConnexion.prepareStatement(requete);
			prepare.setString(1, vCode);	
			int nbEnregSup = prepare.executeUpdate();
			if (nbEnregSup == 0) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Probleme rencontre");
		        alert.setHeaderText("Aucune suppression effectuee dans la BD");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}
		catch (SQLException e) {
			bSuppression = false;
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Probleme rencontre");
	        alert.setHeaderText("Aucune suppression effectuee dans la BD");
	        alert.setContentText(e.getMessage());
	    	alert.showAndWait();
		}
		return bSuppression;
	}
	
	public ArrayList<Commande> chercherCRUD(String recherche){
		String requete = ""; 
		requete += "SELECT com.code, com.total_ttc," + " com.date, cli.code,cli.nom, cli.prenom, mode.type ";
		requete += "FROM commandes AS com, clients AS cli," + 
		" mode_reglements AS mode ";
		requete += "WHERE com.code_mode_reglement = mode.code ";
		requete += "AND com.code_client = cli.code AND (";
		requete += "com.code LIKE '%" + recherche + "%' ";
		requete += "OR cli.nom LIKE '%" + recherche + "%' ";
		requete += "OR cli.prenom LIKE '%" + recherche+ "%' ";
		requete += "OR com.total_ttc LIKE '%" + recherche + "%' ";
		requete += "OR mode.type LIKE '%" + recherche +"%')";
		try {
			Statement state = laConnexion.createStatement();
			ResultSet rs = state.executeQuery(requete);
			while (rs.next()) {
				// Informations generales commande
				String code = rs.getString("com.code");
				double total_ttc = rs.getDouble("total_ttc"); 
				LocalDate date = rs.getDate("date").toLocalDate(); 
				// Informations client
				String codeClient = rs.getString("cli.code");
				String nomPrenom_client = rs.getString("cli.nom")+ " "+ rs.getString("cli.prenom");
				// Informations mode reglement
				String mode_reglement = rs.getString("mode.type"); 
				lesEnreg.add(new Commande(code,
						codeClient,
						nomPrenom_client,
						total_ttc,
						mode_reglement,
						date)); 
			}
		}catch (SQLException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Resultat");
	        alert.setHeaderText("Probleme rencontre");
	        alert.setContentText(e.getMessage());
	    	alert.showAndWait();
		} 
			return lesEnreg; 
	}
	
	public ArrayList<Commande> chercherRapideCRUD_Commande(String vCode){
		String requete = "SELECT c.*, m.* FROM commandes AS c, " +
		"mode_reglements AS m " +
		"WHERE c.code_mode_reglement =" +
		" m.code AND c.code LIKE '" + vCode +"'";
		try {
			Statement state = laConnexion.createStatement();
			ResultSet rs = state.executeQuery(requete);
			while (rs.next()){
				String code = rs.getString("c.code");
				String codeClient = rs.getString("c.code_client");
				String nomPrenom_client = rs.getString("c.nom")+ rs.getString("c.prenom");
				double total_ttc = rs.getDouble("total_ttc");
				String mode_reglement = rs.getString("m.type");
				LocalDate date = rs.getDate("date").toLocalDate();
				//String type = rs.getString("type"); 
			
			lesEnreg.add(new Commande(code,
				codeClient,
				nomPrenom_client,
				total_ttc,
				mode_reglement,
				date)); 
			}
		}
		catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Resultat");
	        alert.setHeaderText("Probleme rencontre");
	        alert.setContentText(e.getMessage());
	    	alert.showAndWait();
		} 
			return lesEnreg; 
	}
	
}
