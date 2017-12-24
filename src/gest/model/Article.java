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
public class Article {
	// PROPRIETES
	//==========
	// Proprietes de base de la classe
	//----------
	private String code;
	private String code_categorie;
	private String designation;
	private int quantite;
	private double prix_unitaire;
	private Date date;
	// Propriete pour etablir la connexion avec la BD
	//----------------
	private static Connection laConnexion = ControleConnexion.getConnexion();
	// propriete de type ArrayList qui contiendra les
	//enregistrements de la BD 
	
	private ArrayList<Article> lesEnreg = new ArrayList<Article>();
	// Getters de base
	//-------------
	public String getCode() {
		return code; 
	}
	public String getCodeCategorie() {
		return code_categorie; 
	}
	public String getDesignation() {
		return designation;
	} 
	public int getQuantitc(){
		return quantite; 
	}
	public double getPrix_unitaire(){
		return prix_unitaire; 
	}
	public Date getDate(){
		return date; 
	}
	// Getter pour transmettre l'ArrayList
	//-----------------
	public ArrayList<Article>getLesEnreg(){ 
		return lesEnreg; 
	}
	// Setters
	//--------
	public void setCode(String code){
		this.code = code; 
	}
	public void setReference(String code_categorie){
		this.code_categorie = code_categorie; 
	}
	public void setDesignation(String designation){
		this.designation = designation; 
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite; 
	}
	public void setPrix_unitaire(double prix_unitaire){
		this.prix_unitaire = prix_unitaire; 
	}
	public void setDate(Date date) {
		this.date=date;
	}
	// CONSTRUCTEURS
	public Article(String code, String code_categorie, String designation, int quantite, double prix_unitaire,
			Date date) {
		this.code = code;
		this.code_categorie = code_categorie;
		this.designation = designation;
		this.quantite = quantite;
		this.prix_unitaire = prix_unitaire;
		this.date = date;
	}
	public Article() {
		lireRecupCRUD();
	}
	public Article(String code) {
		this.code = code;
	}
	
	public void lireRecupCRUD(){
		try {
			Statement state = laConnexion.createStatement();
			ResultSet rs = state.executeQuery ("SELECT * FROM articles");
			while (rs.next()) {
				String code = rs.getString("code");
				String code_categorie = rs.getString("code_categorie");
				String designation = rs.getString("designation");
				int quantite = rs.getInt("quantite");
				double prix_unitaire = rs.getDouble("prix_unitaire");
				Date date_creation = rs.getDate("date");
				
				lesEnreg.add(new Article(code, code_categorie, designation, quantite, prix_unitaire, date_creation));
			}
		}
		catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Resultat ");
	        alert.setHeaderText("Probleme rencontre :");
	        alert.setContentText( e.getMessage());
	    	alert.showAndWait();
		}
	}
	
	public boolean creerCRUD(String vCode, String vReference, String vDesignation, int vQuantite, double vPu, String vDate) {
		boolean bCreation = false;
		String requete = null;
		try {
			requete = "INSERT INTO articles VALUES (?,?,?,?,?,NOW())"; 
			PreparedStatement prepare = laConnexion.prepareStatement(requete);
			prepare.setString(1, vCode);
			prepare.setString(2, vReference);
			prepare.setString(3, vDesignation);
			prepare.setInt (4, vQuantite);
			prepare.setDouble(5, vPu);
			prepare.executeUpdate();
			bCreation = true;
		}
		catch(SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Probleme rencontre ");
	        alert.setHeaderText("Ajout dans la ED non effectue : ");
	        alert.setContentText( e.getMessage());
	    	alert.showAndWait();
		}
		return bCreation;
	}
	
	public boolean modifierCRUD(String vCode, String vReference, String vDesignation, int vQuantite, double vPu)
	{
		boolean bModification = true;
		String requete = null; 
		try {
			requete = "UPDATE articles SET " +
			"code categorie = ?, "+
			"designation = ?, " +
			"quantite = ?, " +
			"prix_unitaire = ? " +
			"WHERE code = ?"; 
			PreparedStatement prepare = laConnexion.prepareStatement(requete);
			prepare.setString(1, vReference);
			prepare.setString(2, vDesignation);
			prepare.setInt (3, vQuantite);
			prepare.setDouble(4, vPu);
			prepare.setString(5, vCode); 
			
			prepare.executeUpdate();
			bModification = true; 
		}catch (SQLException e) {
			bModification = false;
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Probleme rencontre ");
	        alert.setHeaderText("Modification dans la BD non effectuee : ");
	        alert.setContentText( e.getMessage());
	    	alert.showAndWait();
		}
		return bModification;
	}
	
	public boolean supprimerCRUD(String vCode){
		boolean bSuppression = true;
		String requete = null;
		try {
			requete = "DELETE FROM articles WHERE code = ?"; 
			PreparedStatement prepare = laConnexion.prepareStatement(requete);
			prepare.setString(1, vCode); 
			int nbEnregSup = prepare.executeUpdate();
			if (nbEnregSup == 0){
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Probleme rencontre ");
		        alert.setHeaderText("Aucune suppression effectuee dans la BD.");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}
			catch (SQLException e) {
				bSuppression = false;
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Probleme rencontre ");
		        alert.setHeaderText("Aucune suppression effectuee dans la BD.");
		        alert.setContentText("");
		    	alert.showAndWait();
			} 
			return bSuppression;
	}
	
	public ArrayList<Article> chercherCRUD(String recherche){
		String requete = "";
		requete += "SELECT * ";
		requete += "FROM articles ";
		requete += "WHERE code LIKE '%" + recherche + "%' ";
		requete += "OR code categorie LIKE '%" + recherche + "%' ";
		requete += "OR designation LIKE '%" + recherche + "%' "; 
	
	try{
		Statement state = laConnexion.createStatement();
		ResultSet rs = state.executeQuery(requete);
		while (rs.next()) {
			String code = rs.getString("code");
			String code_categorie = rs.getString("code_categorie");
			String designation = rs.getString("designation");
			int quantite = rs.getInt("quantite");
			double prix_unitaire = rs.getDouble("prix_unitaire");
			Date date_creation = rs.getDate("date"); 
			lesEnreg.add(new Article(code, code_categorie, designation, quantite, prix_unitaire, date_creation)); 
		}
	}catch (SQLException e) {
		Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Resultat");
        alert.setHeaderText("Probleme rencontre :");
        alert.setContentText("e.getMessage()");
    	alert.showAndWait();
	}
	return lesEnreg; 
	}
	
	
}
