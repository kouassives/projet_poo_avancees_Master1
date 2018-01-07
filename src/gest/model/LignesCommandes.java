package gest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ControleConnexion;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LignesCommandes {

	//propriétés
	//----------
		private StringProperty codeCommande;
		private StringProperty codeArticle;
		private StringProperty designation;
		private IntegerProperty quantite;
		private DoubleProperty prixUnitaire;
		private DoubleProperty total;
		
		//Pour obtenir la connexion statique
		private static Connection laConnexion = ControleConnexion.getConnexion();
		//pour stocker les enregistrements de la BD
		private final ArrayList<LignesCommandes> lesEnreg = new ArrayList<LignesCommandes>();
		//Constructeurs
		//----------
		//Constructeur 1
		public LignesCommandes(String codeCommande, String codeArticle, String designation,int quantite,double prixUnitaire, double total) {
			this.codeCommande = new SimpleStringProperty(codeCommande);
			this.codeArticle = new SimpleStringProperty(codeArticle);
			this.designation = new SimpleStringProperty(designation);
			this.quantite = new SimpleIntegerProperty(quantite);
			this.prixUnitaire = new SimpleDoubleProperty(prixUnitaire);
			this.total = new SimpleDoubleProperty(total);
		}
		//Constructeur 2
		public LignesCommandes() {
			lireRecupCRUD();
		}
		//Constructeur 3 pour initier la recherche
		public LignesCommandes(String codeArticle) {
			this.codeArticle.set(codeArticle);
		}
		
		
		// Accesseurs
		//-----------
		public String getCodeCommande() {
			return codeCommande.get();
		}
		public StringProperty codeCommandeProperty() {
			return codeCommande;
		}
		
		public String getcodeArticle() {
			return codeArticle.get();
		}
		public StringProperty codeArticleProperty() {
			return codeArticle;
		}
		
		
		public String getdesignation() {
			return designation.get();
		}
		public StringProperty designationProperty() {
			return designation;
		}
		
		public Integer getquantite() {
			return quantite.get();
		}
		public IntegerProperty quantiteProperty() {
			return quantite;
		}
		
		public double gettotal() {
			return total.get();
		}
		public DoubleProperty totalProperty() {
			return total;
		}
		
		public double getprixUnitaire() {
			return prixUnitaire.get();
		}
		public DoubleProperty prixUnitaireProperty() {
			return prixUnitaire;
		}
		
		
		public ArrayList<LignesCommandes> getLesEnreg() {
			return lesEnreg;
		}
		
		//Mutateur
		public void setCodeCommande(String codeCommande) {
			this.codeCommande.set(codeCommande);
		}
		
		public void setCodeArticle(String codeArticle) {
			this.codeArticle.set(codeArticle);
		}
		
		public void setDesignation(String designation) {
			this.designation.set(designation);
		}
		
		public void setQuantite(int quantite) {
			this.quantite.set(quantite);
		}
		
		public void setPrixUnitaire(int prixUnitaire) {
			this.prixUnitaire.set(prixUnitaire);
		}
		
		public void setTotal(int total) {
			this.total.set(total);
		}
		
		
		
		public void lireRecupCRUD() {
			try {
				Statement state = laConnexion.createStatement();
				ResultSet rs = state.executeQuery("SELECT lcom.code_commande," +
				" lcom.code_article, lcom.quantite,lcom.prix_unitaire,lcom.total," +
				"art.designation "+
				"FROM lignes_commandes AS lcom, articles AS art "+
				"WHERE art.code = lcom.code_article");
				while (rs.next()) { 
					// Informations generales commande
					String codeCommande = rs.getString("lcom.code_commande");
					String codeArticle = rs.getString("lcom.code_article");
					String designation = rs.getString("art.designation");
					int quantite = rs.getInt("lcom.quantite");
					double prixUnitaire = rs.getDouble("lcom.prix_unitaire");
					double total = rs.getDouble("lcom.total");
					// Informations client
					lesEnreg.add(new LignesCommandes(codeCommande,
							codeArticle,
							designation,
							quantite,
							prixUnitaire,
							total));
				}
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("ALERTE");
		        alert.setHeaderText("Probleme rencontre");
		        alert.setContentText(e.getMessage());
		    	alert.showAndWait();
		    }
		}
		
	public boolean creerCRUD(String codeCommande, String codeArticle, int quantite, double prixUnitaire,double total) {
			boolean bCreation = false;
			String requete = null;
			try {
				requete = "INSERT INTO lignes_commandes VALUES (?,?,?,?,?)"; 
		
				PreparedStatement prepare = laConnexion.prepareStatement(requete);
				prepare.setString(1, codeCommande);
				prepare.setString(2, codeArticle);
				prepare.setInt(3, quantite);
				prepare.setDouble (4, prixUnitaire);
				prepare.setDouble (5, total);
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
		
	/*	
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
			requete += "SELECT com.code, com.total_ttc," + " com.date, cli.code,cli.nom, cli.prenom, mode.type ";
			requete += "FROM commandes AS com, clients AS cli," + 
			" mode reglements AS mode ";
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
					String nomPrenom_client = rs.getString("cli.nom")+ rs.getString("cli.prenom");
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
		        alert.setContentText("");
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
		
			*/
}
