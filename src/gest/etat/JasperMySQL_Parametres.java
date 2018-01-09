package gest.etat;

import java.io.File;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import connection.ControleConnexion;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
// pour la gestion du chemin et des différents OS
import gest.util.Systeme;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class JasperMySQL_Parametres {
	private static Connection laConnexion = ControleConnexion.getConnexion();
	private static JasperDesign design = null;
	private static JasperReport report = null;
	private static JasperPrint print= null;
	private static String codeCommande = "";
	private static String codeClient = "";
	private static String rechercheArticle = "";
	
	
	//Getters
	public static String getCodeCommande() {
		return codeCommande;
	}

	public static String getCodeClient() {
		return codeClient;
	}

	public static String getRechercheArticle() {
		return rechercheArticle;
	}

	//Setters
	public static void setCodeCommande(String codeCommande) {
		JasperMySQL_Parametres.codeCommande = codeCommande;
	}

	public static void setCodeClient(String codeClient) {
		JasperMySQL_Parametres.codeClient = codeClient;
	}

	public static void setRechercheArticle(String rechercheArticle) {
		JasperMySQL_Parametres.rechercheArticle = rechercheArticle;
	}

	// Etapes 1 à 3 du processus Jasper
	// --------------------------------
	
	public static void chargeEtcompile(String rapport) throws JRException {
		try{
			System.out.println(Systeme.getRepertoireCourant());
			design = JRXmlLoader.load(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+rapport);
			report = JasperCompileManager.compileReport(design);
			HashMap<String, Object> mesParametres = new HashMap<String, Object>();
			mesParametres.put("imageLogo",new String(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+"images"+Systeme.getSeparateur()+"icone_eclipse.png"));
			mesParametres.put("imageCommande",new String(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+"images"+Systeme.getSeparateur()+"Shopping-Bag-128.png"));
			mesParametres.put("codeCommande",new String(getCodeCommande()));
			print = JasperFillManager.fillReport(report,mesParametres,laConnexion);
			
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
		}
		
	}

	// apercu avant impression

	public static void apercu(String rapport) throws JRException {
		chargeEtcompile(rapport);
		try{
			JasperViewer.viewReport(print,false);
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("L'apercu a échouée :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
		}
	}

	/*// Impression du rapport tous les formats
	public static void imprimer(String rapport, Collection<?> elements,
			Object... paramètres) {
		try {
			JasperPrint print = chargeEtcompile(rapport, elements, paramètres);
			imprimer(print);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("L'impression a échouée :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
			
		}
	

	public static void imprimer(JasperPrint print) throws JRException {
		JasperPrintManager.printReport(print, true);
	}
	
	*/

	public static void printCommande(String rapport) throws JRException {
		chargeEtcompile(rapport);
		try {
			JasperPrintManager.printReport(print,true);
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("L'apercu a échouée :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
		}
		
	}
	
/*
	// Exporter formats Format quelconque à préciser dans l'appel
	public static void export(FormatExport format, String prefixe, String rapport,
			Collection<?> elements, Object... paramètres) {
		JFileChooser save = new JFileChooser();
		save.setSelectedFile(new File(prefixe + "." + format.name().toLowerCase()));
		int retour = save.showSaveDialog(save);
		if (retour == JFileChooser.APPROVE_OPTION) {
			try {
				JasperPrint print = chargeEtcompile(rapport, elements,
						paramètres);
				format.export(print, save.getSelectedFile());
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Erreur");
		        alert.setHeaderText("L'export %s a rencontré une erreur : ");
		        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
		    	alert.showAndWait();
				
			}
		}
	}}*/

	
}
