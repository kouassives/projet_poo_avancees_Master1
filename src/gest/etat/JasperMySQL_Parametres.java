package gest.etat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import connection.ControleConnexion;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import gest.MainApp;
// pour la gestion du chemin et des différents OS
import gest.util.Systeme;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;


public class JasperMySQL_Parametres {
	private static Connection laConnexion = ControleConnexion.getConnexion();
	private static JasperDesign design = null;
	private static JasperReport report = null;
	private static JasperPrint print= null;
	private static String model;
	private static String codeCommande = "";
	private static String codeClient = "";
	private static String codeArticle= "";
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
	
	public static void chargeEtcompile(String rapport){
		if (rapport.equals("commande.jrxml")){
				try{
					model="commande";
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
		if (rapport.equals("Clients.jrxml")){
			try{
				model="Clients";
				design = JRXmlLoader.load(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+rapport);
				report = JasperCompileManager.compileReport(design);
				HashMap<String, Object> mesParametres = new HashMap<String, Object>();
				mesParametres.put("imageLogo",new String(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+"images"+Systeme.getSeparateur()+"icone_eclipse.png"));
				mesParametres.put("imageClient",new String(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+"images"+Systeme.getSeparateur()+"Shopping-Bag-128.png"));
				print = JasperFillManager.fillReport(report,mesParametres,laConnexion);
				
			}catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Erreur");
		        alert.setHeaderText("");
		        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
		    	alert.showAndWait();
			}
		}
		
		if (rapport.equals("Articles.jrxml")){
			try{
				model="Articles";
				design = JRXmlLoader.load(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+rapport);
				report = JasperCompileManager.compileReport(design);
				HashMap<String, Object> mesParametres = new HashMap<String, Object>();
				mesParametres.put("imageLogo",new String(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+"images"+Systeme.getSeparateur()+"icone_eclipse.png"));
				mesParametres.put("imageArticle",new String(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+"images"+Systeme.getSeparateur()+"Shopping-Bag-128.png"));
				print = JasperFillManager.fillReport(report,mesParametres,laConnexion);
				
			}catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Erreur");
		        alert.setHeaderText("");
		        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
		    	alert.showAndWait();
			}
		}
		
		if (rapport.equals("stats.jrxml")){
			try{
				model="stats";
				design = JRXmlLoader.load(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+rapport);
				report = JasperCompileManager.compileReport(design);
				HashMap<String, Object> mesParametres = new HashMap<String, Object>();
				mesParametres.put("imageLogo",new String(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+"images"+Systeme.getSeparateur()+"icone_eclipse.png"));
				//mesParametres.put("imageArticle",new String(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+"images"+Systeme.getSeparateur()+"Shopping-Bag-128.png"));
				print = JasperFillManager.fillReport(report,mesParametres,laConnexion);
				
			}catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Erreur");
		        alert.setHeaderText("");
		        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
		    	alert.showAndWait();
			}
		}
			
	}

	// apercu avant impression

	public static void apercu(String rapport){
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

	

	public static void printCommande(String rapport){
		chargeEtcompile(rapport);
		try {
			JasperPrintManager.printReport(print,true);
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("L'impression non effectuée :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
		}
		
	}
	
	public static boolean exportPdf(String rapport){
		chargeEtcompile(rapport);
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Exportation de "+model);
			fileChooser.setInitialFileName(model+codeCommande);
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(null);
			if (file != null )
			{
				
				JasperExportManager.exportReportToPdfFile(print, file.getPath());
				return true;
				//JasperExportManager.exportReportToPdfFile(print, Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+"commandeExport.pdf");
				
				// exports report to pdf
				//JRPdfExporter exporter = new JRPdfExporter();
				//exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
				//exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(Systeme.getRepertoireCourant()+Systeme.getSeparateur()+"jasper"+Systeme.getSeparateur()+rapport + ".pdf")); // your output goes here
			}
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("L'exportation a échouée :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
		}
		return false;
	}
	
	public static boolean exportHtml(String rapport){
		chargeEtcompile(rapport);
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Exportation de "+ model);
			fileChooser.setInitialFileName(model+codeCommande);
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(null);
			if (file != null )
			{
				
				JasperExportManager.exportReportToHtmlFile(print, file.getPath());
				return true;
				
			}
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("L'exportation a échouée :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
		}
		return false;
	}
	public static boolean exportDocx(String rapport){
		chargeEtcompile(rapport);
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Exportation de "+ model);
			fileChooser.setInitialFileName(model+codeCommande);
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DOCX files (*.docx)", "*.docx");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(null);
			if (file != null )
			{
				Exporter exporter = new JRDocxExporter();
				exporter.setExporterInput(new SimpleExporterInput(print));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
				exporter.exportReport();
				return true;
				
			}
		}catch(Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("L'exportation a échouée :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
		}
		return false;
	}
	

	
}
