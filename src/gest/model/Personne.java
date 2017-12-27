package gest.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Personne {

		StringProperty code;
		StringProperty nom;
		StringProperty prenom;
		
		public StringProperty codeProperty() {
			return code;
		}
		public String getCode() {
			return code.get();
		}


		public void setCode(String code) {
			this.code.set(code);
		}
		

		public StringProperty nomProperty() {
			return nom;
		}
		public String getNom() {
			return nom.get();
		}


		public void setNom(String nom) {
			this.nom.set(nom);
		}

		
		public StringProperty prenomProperty() {
			return prenom;
		}
		public String getPrenom() {
			return prenom.get();
		}


		public void setPrenom(String prenom) {
			this.prenom.set(prenom);
		}

		//1er constructeur
		public Personne(String vcode, String vnom, String vprenom) {
			this.code = new SimpleStringProperty(vcode);
			this.nom = new SimpleStringProperty(vnom);
			this.prenom = new SimpleStringProperty(vprenom);
		}
		
		//2eme constructeur pour les recherche
		public Personne(String vcode) {
			this.nom = new SimpleStringProperty(vcode);
		}
		
		//3eme constructeur pour une simple lecture de collection
		public Personne() {
		}
		
}
