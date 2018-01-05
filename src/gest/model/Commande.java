package gest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import connection.ControleConnexion;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	public Commande(String code, String code_client, double total_ttc, int codeModeReglement, LocalDate date) {
		this.code = new SimpleStringProperty(code);
		this.code_client = new SimpleStringProperty(code_client);
		this.total_ttc = new SimpleDoubleProperty(total_ttc);
		this.codeModeReglement = new SimpleIntegerProperty(codeModeReglement);
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
			"cli.prenom, mode.code "+
			"FROM commandos AS com, clients AS cli, "+
			"mode_reglements AS mode " +
			"WHERE com.code_mode_reglement = mode.code " +
			"AND com.code client = cli.code");
			while (rs.next()) { 
				// Informations generales commande
				String code = rs.getString("com.code");
				double total_ttc = rs.getDouble("total_ttc");
				LocalDate date = rs.getDate("date").toLocalDate(); 
				// Informations client
				String codeClient = rs.getString("cli.code");
				// Information mode reglement
				int modeReglement = rs.getInt("mode.code"); 
				lesEnreg.add(new Commande(code,
						codeClient,
						total_ttc,
						modeReglement, 
						date));
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("ALERTE");
	        alert.setHeaderText("Probleme rencontre");
	        alert.setContentText("");
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
			requete = "DELETE commandes, lignescommandes" + " FROM commandes, lignescommandes " + "WHERE code commande = code AND code = ?";
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
	        alert.setContentText("");
	    	alert.showAndWait();
		}
		return bSuppression;
	}
	
	public ArrayList<Commande> chercherCRUD(String recherche){
		String requete = ""; 
		requete += "SELECT com.code, com.total_ttc," + " com.date, cli.code,cli.nom, cli.prenom, mode.code ";
		requete += "FROM commandes AS com, clients AS cli," + 
		" mode reglements AS mode ";
		requete += "WHERE com,code_mode_reglement = mode.code ";
		requete += "AND com.code client = cli.code AND (";
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
				// Informations mode reglement
				int code_mode_reglement = rs.getInt("mode.code"); 
				lesEnreg.add(new Commande(code,
						codeClient,
						total_ttc,
						code_mode_reglement,
						date)); 
			}
		}catch (SQLException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Resultat");
	        alert.setHeaderText("Probleme rencontre");
	        alert.setContentText("");
	    	alert.showAndWait();
		} 
			return lesEnreg; 
	}
	
	public ArrayList<Commande> chercherRapideCRUD_Commande(String vCode){
		String requete = "SELECT c.*, m.* FROM commandes AS c, " +
		"mode reglements AS m " +
		"WHERE c.code_mode_reglement =" +
		" m.code AND c.code LIKE '" + vCode +"'";
		try {
			Statement state = laConnexion.createStatement();
			ResultSet rs = state.executeQuery(requete);
			while (rs.next()){
				String code = rs.getString("c.code");
				String codeClient = rs.getString("c.code_client");
				double total_ttc = rs.getDouble("total_ttc");
				int code_mode_reglement = rs.getInt("code_mode_reglement");
				LocalDate date = rs.getDate("date").toLocalDate();
				//String type = rs.getString("type"); 
			
			lesEnreg.add(new Commande(code,
				codeClient,
				total_ttc,
				code_mode_reglement,
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
