package gest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import connection.ControleConnexion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Commande {
//propriétés
//----------
	private String code;
	private Client code_client;
	private double total_ttc;
	private int codeModeReglement;
	private ModeReglements mode_reglement;
	private Date date;
	
	//Pour obtenir la connexion statique
	private static Connection laConnexion = ControleConnexion.getConnexion();
	//pour stocker les enregistrements de la BD
	private final ArrayList<Commande> lesEnreg = new ArrayList<Commande>();
	//Constructeurs
	//----------
	//Constructeur 1
	public Commande(String code, Client code_client, double total_ttc, int codeModeReglement, Date date) {
		this.code = code;
		this.code_client = code_client;
		this.total_ttc = total_ttc;
		this.codeModeReglement = codeModeReglement;
		this.date = date;
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
		return code;
	}
	public Client getCode_client() {
		return code_client;
	}
	public double getTotal_ttc() {
		return total_ttc;
	}
	public int getCodeModeReglement() {
		return codeModeReglement;
	}
	public ModeReglements getMode_reglement() {
		return mode_reglement;
	}
	public Date getDate() {
		return date;
	}
	public ArrayList<Commande> getLesEnreg() {
		return lesEnreg;
	}
	
	public void lireRecupCRUD() {
		try {
			Statement state = laConnexion.createStatement();
			ResultSet rs = state.executeQuery("SELECT com.code," +
			" com.total_ttc, com.date, cli.nom, " +
			"cli.prenom, mode.type "+
			"FROM commandos AS com, clients AS cli, "+
			"mode_reglements AS mode " +
			"WHERE com.code_mode_reglement = mode.code " +
			"AND com.code client = cli.code");
			while (rs.next()) { 
				// Informations generales commande
				String code = rs.getString("code");
				double total_ttc = rs.getDouble("total_ttc");
				Date date = rs.getDate("date"); 
				// Informations client
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom"); 
				// Information mode reglement
				String type = rs.getString("type"); 
				lesEnreg.add(new Commande(code,
						new Client(nom, prenom),
						total_ttc,
						(new ModeReglements(type)).getCode(), 
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
		requete += "SELECT com.code, com.total_ttc," + " com.date, cli.nom, cli.prenom, mode.code ";
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
				String code = rs.getString("code");
				double total_ttc = rs.getDouble("total_ttc"); 
				Date date = rs.getDate("date"); 
				// Informations client
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom"); 
				// Informations mode reglement
				int code_mode_reglement = rs.getInt("code"); 
				lesEnreg.add(new Commande(code,
						new Client (nom, prenom),
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
				String code = rs.getString("code");
				String code_client = rs.getString("code_client");
				double total_ttc = rs.getDouble("total_ttc");
				int code_mode_reglement = rs.getInt("code_mode_reglement");
				Date date = rs.getDate("date");
				//String type = rs.getString("type"); 
			
			lesEnreg.add(new Commande(code,
				new Client(code_client),
				total_ttc,
				code_mode_reglement,
				//new ModeReglements(code_mode_reglement),
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
