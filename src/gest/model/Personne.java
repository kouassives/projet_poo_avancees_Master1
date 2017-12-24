package gest.model;

	public abstract class Personne {

		String code;
		String nom;
		String prenom;
		

		public String getCode() {
			return code;
		}


		public void setCode(String code) {
			this.code = code;
		}


		public String getNom() {
			return nom;
		}


		public void setNom(String nom) {
			this.nom = nom;
		}


		public String getPrenom() {
			return prenom;
		}


		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}

		//1er constructeur
		public Personne(String vcode, String vnom, String vprenom) {
			this.code = vcode;
			this.nom = vnom;
			this.prenom = vprenom;
		}
		
		//2eme constructeur pour les recherche
		public Personne(String vcode) {
			this.code = vcode;
		}
		
		//3eme constructeur pour une simple lecture de collection
		public Personne() {
		}
		
}
