package gest.view;

import java.util.ArrayList;

import gest.etat.JasperMySQL_Parametres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

public class FenExportController {

	ObservableList<String> typeObservable = FXCollections.observableArrayList(new ArrayList<String>());
	
	@FXML
	private ComboBox<String> typeComboBox;
	
	private String code;
	private String model;
	
	public void setModel(String model) {
		this.model=model;
	}
	  /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    	typeObservable.add("PDF(*.pdf)");
	    	typeObservable.add("HTML(*.html)");
	    	typeObservable.add("DOCX(*.docx)");
	    	typeComboBox.setItems(typeObservable);
	    	typeComboBox.setItems(typeObservable);
	    	typeComboBox.setValue(typeObservable.get(0));
	    }
	    
	    
	@FXML
	private void handleExporter() throws JRException {
		if(model.equals("commande")){
			expcommande();
		}
		else if(model.equals("Clients")) {
			expClients();
		}
		else if(model.equals("Articles")) {
			expArticle();
		}
	}
	    
	
	private void expcommande() {
		JasperMySQL_Parametres.setCodeCommande(code);
		if (typeComboBox.getValue().equals("PDF(*.pdf)"))
		{
			if(JasperMySQL_Parametres.exportPdf("commande.jrxml"))
			{
				this.dialogStage.hide();
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("L'exportation a reussie :");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}else if (typeComboBox.getValue().equals("HTML(*.html)") ) {
			if(JasperMySQL_Parametres.exportHtml("commande.jrxml"))
			{
				this.dialogStage.hide();
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("L'exportation a reussie :");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}else if (typeComboBox.getValue().equals("DOCX(*.docx)") ) {
			if(JasperMySQL_Parametres.exportDocx("commande.jrxml"))
			{
				this.dialogStage.hide();
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("L'exportation a reussie :");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}

	}
	
	private void expClients() {
		JasperMySQL_Parametres.setCodeCommande("");
		if (typeComboBox.getValue().equals("PDF(*.pdf)"))
		{
			if(JasperMySQL_Parametres.exportPdf("Clients.jrxml"))
			{
				this.dialogStage.hide();
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("L'exportation a reussie :");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}else if (typeComboBox.getValue().equals("HTML(*.html)") ) {
			
			if(JasperMySQL_Parametres.exportHtml("Clients.jrxml"))
			{
				this.dialogStage.hide();
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("L'exportation a reussie :");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}else if (typeComboBox.getValue().equals("DOCX(*.docx)") ) {
			
			if(JasperMySQL_Parametres.exportDocx("Clients.jrxml"))
			{
				this.dialogStage.hide();
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("L'exportation a reussie :");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}

	}
	
	
	private void expArticle() {
		JasperMySQL_Parametres.setCodeCommande("");
		if (typeComboBox.getValue().equals("PDF(*.pdf)"))
		{
			if(JasperMySQL_Parametres.exportPdf("Articles.jrxml"))
			{
				this.dialogStage.hide();
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("L'exportation a reussie :");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}else if (typeComboBox.getValue().equals("HTML(*.html)") ) {
			
			if(JasperMySQL_Parametres.exportHtml("Articles.jrxml"))
			{
				this.dialogStage.hide();
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("L'exportation a reussie :");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}else if (typeComboBox.getValue().equals("DOCX(*.docx)") ) {
			
			if(JasperMySQL_Parametres.exportDocx("Articles.jrxml"))
			{
				this.dialogStage.hide();
				Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("L'exportation a reussie :");
		        alert.setContentText("");
		    	alert.showAndWait();
			}
		}

	}
	
	
	
	private Stage dialogStage;

	/**
 * Sets the stage of this dialog.
 *
 * @param dialogStage
 */
public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
}

public void setCode(String code) {
	this.code = code;
}


}
