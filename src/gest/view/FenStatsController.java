package gest.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ControleConnexion;
import gest.MainApp;
import gest.etat.JasperMySQL_Parametres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;


public class FenStatsController {
	
	@FXML
    private PieChart pieChart;
	@FXML
    private Label anneeTextfield;
	@FXML
    private Label notification;
	
	@FXML final Label caption = new Label("");
    
	private static Connection laConnexion = ControleConnexion.getConnexion();
	
	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
private String annee;
//private  DefaultPieDataset data = new DefaultPiedDataset();
	public String getAnnee() {
	return annee;
}

public void setAnnee(String annee) {
	this.annee = annee;
	anneeTextfield.setText(annee);
}

	private MainApp mainApp;

	  /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    }
	    
	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }
	   public void recupData() {
		   String requete="SELECT MONTH(c.date), COUNT(c.date), m.nom "
		   		+ "FROM commandes as c,mois as m "
		   		+ "WHERE YEAR(date) = '"+annee
		   		+ "' and m.id = MONTH(c.date) GROUP BY MONTH(date)";
		   
		   try {
			   Statement state = laConnexion.createStatement();
			   ResultSet rs = state.executeQuery(requete);
			   while(rs.next()) {
				   pieChartData.add(new PieChart.Data(rs.getString("m.nom"),rs.getInt("COUNT(c.date)")));
			   }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    	pieChart.setData(pieChartData);

	    	if (pieChart.getData().isEmpty())
	    		notification.setText("Il y a pas de données statistique pour l'année que vous avez entrez");
	   }

	   @FXML
	    private void handlePrintStats() throws JRException{
	    	if (!pieChart.getData().isEmpty())
	    	{
	    		JasperMySQL_Parametres.printCommande("stats.jrxml");
	    		
	    	}
	    	else
	    	{
	    		Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("INFORMATION");
		        alert.setHeaderText("Aucune donnée à imprimer");
		        alert.setContentText("Vérifier l'année");
		    	alert.showAndWait();
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



}
