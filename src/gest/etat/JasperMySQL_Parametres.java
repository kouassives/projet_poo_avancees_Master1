package gest.etat;

import java.io.File;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;

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
// pour la gestion du chemin et des diff�rents OS
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

	// Etapes 1 � 3 du processus Jasper
	// --------------------------------
	public static JasperPrint chargeEtcompile(String rapport,
			Collection<?> elements, Object... param�tres) throws JRException {
		try {
			// Etape 1
			String dossierJasper = Systeme.getRepertoireCourant()
					+ Systeme.getSeparateur() + "jasper"
					+ Systeme.getSeparateur();
			JasperDesign design = JRXmlLoader.load(dossierJasper + rapport);
			// Etape 2
			JasperReport report = JasperCompileManager.compileReport(design);
			// Etape 3

			// les param�tres sont pass�s en alternance:
			// d'abord la cl� puis la valeur,
			// ceci r�p�t� pour chaque param�tre
			Map<String, Object> mesParametres = new HashMap<String, Object>();
			if (param�tres != null) {
				for (int i = 0; i < param�tres.length; i += 2) {
					mesParametres
							.put((String) param�tres[i], param�tres[i + 1]);
				}
			}
			mesParametres.put("imagesDir", dossierJasper + "images");

			JRDataSource source = new JRBeanCollectionDataSource(elements);
			return JasperFillManager.fillReport(report, mesParametres, source);
		} catch (JRException e) {
			throw e;
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("La compilation du rapport a �chou�e :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
	    	
			return null;
		}
	}

	// apercu avant impression
	public static void apercu(String rapport, Collection<?> elements,
			Object... param�tres) {
		try {
			JasperPrint print = chargeEtcompile(rapport, elements, param�tres);
			apercu(print);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("Erreur lors de l'aper�u :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
	    	
		}
	}

	public static void apercu(JasperPrint print) {
		JasperViewer.viewReport(print, false);
	}

	// Impression du rapport tous les formats
	public static void imprimer(String rapport, Collection<?> elements,
			Object... param�tres) {
		try {
			JasperPrint print = chargeEtcompile(rapport, elements, param�tres);
			imprimer(print);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur");
	        alert.setHeaderText("L'impression a �chou�e :");
	        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
	    	alert.showAndWait();
			
		}
	}

	public static void imprimer(JasperPrint print) throws JRException {
		JasperPrintManager.printReport(print, true);
	}

	// Exporter formats Format quelconque � pr�ciser dans l'appel
	public static void export(FormatExport format, String prefixe, String rapport,
			Collection<?> elements, Object... param�tres) {
		JFileChooser save = new JFileChooser();
		save.setSelectedFile(new File(prefixe + "." + format.name().toLowerCase()));
		int retour = save.showSaveDialog(save);
		if (retour == JFileChooser.APPROVE_OPTION) {
			try {
				JasperPrint print = chargeEtcompile(rapport, elements,
						param�tres);
				format.export(print, save.getSelectedFile());
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Erreur");
		        alert.setHeaderText("L'export %s a rencontr� une erreur : ");
		        alert.setContentText( e.getMessage()+ "\n Veuillez contacter votre administrateur" );
		    	alert.showAndWait();
				
			}
		}
	}

	
}
