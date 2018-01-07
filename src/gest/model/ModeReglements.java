package gest.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import connection.ControleConnexion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Entity
public class ModeReglements implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Connection laConnexion = ControleConnexion.getConnexion();
	// propriete de type ArrayList qui contiendra les
	//enregistrements de la BD 
	
	private ArrayList<ModeReglements> lesEnreg = new ArrayList<ModeReglements>();
	
	@Id
	@GeneratedValue
    private IntegerProperty code;
	
	@Basic
    private StringProperty type;
    
    @Transient
    private transient boolean ignor�;
    
    /*
     * Constructeur 1.
     * Utilisé par JPA.
     */
    public ModeReglements(int code,String type) {
    	this.code = new SimpleIntegerProperty(code);
    	this.type = new SimpleStringProperty(type);
    }
    /*
     * Constructeur 2.
     * Utilisé par JPA.
     */
    
    public ModeReglements(){
    	super();
    	lireRecupCRUD();
    }
    
    /*
     * Constructeur 3
     * Pour la gestion des commandes
     */
    public ModeReglements(String type){
    	this();
    	this.type = new SimpleStringProperty(type);
    }

    /*
     * Accesseurs
     */
    public int getCode() {
        return code.get();
    }
    public IntegerProperty codeProperty() {
        return code;
    }
    
    public String getType() {
        return type.get();
    }
    public StringProperty typeProperty() {
        return type;
    }
    
 // Getter pour transmettre 1'ArrayList 
 	// ---------
 	public ArrayList<ModeReglements> getlesEnreg(){
 		return lesEnreg; 
 	} 
    
    /*
     * Mutateur
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     * Décrit le mode de règlement
     * de manière textuelle.
     */
    @Override
    public String toString() {
    	return type.get();
    }
    
    public void lireRecupCRUD() {
    	try {
			Statement state = laConnexion.createStatement();
			ResultSet rs = state.executeQuery ("SELECT * FROM mode_reglements");
			while (rs.next()) {
				int code = rs.getInt("code");
				String type = rs.getString("type");
				
				lesEnreg.add(new ModeReglements(code,type));
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
}
