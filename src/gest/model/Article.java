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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType; 
public class Article {
	// PROPRIETES
	//==========
	// Proprietes de base de la classe
	//----------
	private StringProperty code;
	private StringProperty codeCategorie;
	private StringProperty designation;
	private IntegerProperty quantite;
	private DoubleProperty prix_unitaire;
	private ObjectProperty<LocalDate> date;
	// Propriete pour etablir la connexion avec la BD
	//----------------
	private static Connection laConnexion = ControleConnexion.getConnexion();
	// propriete de type ArrayList qui contiendra les
	//enregistrements de la BD 
	
	private ArrayList<Article> lesEnreg = new ArrayList<Article>();
	// Getters de base
	//-------------
	public String getCode() {
		return code.get(); 
	}
	public StringProperty codeProperty() {
		return code;
	}
	
	public String getCodeCategorie() {
		return codeCategorie.get(); 
	}
	public StringProperty codeCategorieProperty() {
		return codeCategorie;
	}
	
	public String getDesignation() {
		return designation.get();
	}
	public StringProperty designationProperty() {
		return designation;
	}
	
	public int getQuantite(){
		return quantite.get(); 
	}
	public IntegerProperty quantiteProperty() {
		return quantite;
	}
	
	public double getPrix_unitaire(){
		return prix_unitaire.get(); 
	}
	public DoubleProperty prixUnitaireProperty() {
		return prix_unitaire;
	}
	
	public LocalDate getDate(){
		return date.get(); 
	}
	public ObjectProperty<LocalDate> dateProperty() {
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
		this.code.set(code); 
	}
	public void setCodeCategorie(String code_categorie){
		this.codeCategorie.set(code_categorie); 
	}
	public void setDesignation(String designation){
		this.designation.set(designation); 
	}
	public void setQuantite(int quantite) {
		this.quantite.set(quantite); 
	}
	public void setPrix_unitaire(double prix_unitaire){
		this.prix_unitaire.set(prix_unitaire); 
	}
	public void setDate(LocalDate date) {
		this.date.set(date);
	}
	// CONSTRUCTEURS
	public Article(String code, String code_categorie, String designation, int quantite, double prix_unitaire,
			LocalDate date) {
		this.code = new SimpleStringProperty(code);
		this.codeCategorie = new SimpleStringProperty(code_categorie);
		this.designation = new SimpleStringProperty(designation);
		this.quantite = new SimpleIntegerProperty(quantite);
		this.prix_unitaire = new SimpleDoubleProperty(prix_unitaire);
		this.date = new SimpleObjectProperty<LocalDate>(date);
	}
	public Article() {
		lireRecupCRUD();
	}
	public Article(String code) {
		this.code.set(code);
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
				LocalDate date_creation = rs.getDate("date").toLocalDate();
				
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
	
	public boolean creerCRUD(String vCode, String vReference, String vDesignation, int vQuantite, double vPu, LocalDate vDate) {
		boolean bCreation = false;
		String requete = null;
		try {
			// On vérifie initialement s'il existe une catégorie de l'article a ajouter
			Statement state = laConnexion.createStatement();
			String requete2 = "SELECT count(*) AS nb FROM categories WHERE code = '"+vReference+"'";
			ResultSet rs = state.executeQuery(requete2);
			rs.next();
			if (rs.getInt("nb")==0) {
				requete2 = "INSERT INTO categories VALUES (?,?)"; 
				PreparedStatement prepare2 = laConnexion.prepareStatement(requete2);
				prepare2.setString(1, vReference);
				prepare2.setString(2, vDesignation);
				
				prepare2.executeUpdate();
				
			}
			
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
	        alert.setHeaderText("Ajout dans la DB non effectue : ");
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
			"code_categorie = ?, "+
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
		requete += "OR code_categorie LIKE '%" + recherche + "%' ";
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
			LocalDate date_creation = rs.getDate("date").toLocalDate(); 
			lesEnreg.add(new Article(code, code_categorie, designation, quantite, prix_unitaire, date_creation)); 
		}
	}catch (SQLException e) {
		Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Resultat");
        alert.setHeaderText("Probleme rencontre :");
        alert.setContentText(e.getMessage());
    	alert.showAndWait();
	}
	return lesEnreg; 
	}
	
	
}
