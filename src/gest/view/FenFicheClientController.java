package gest.view;

import java.time.LocalDate;

import gest.MainApp;
import gest.model.Client;
import gest.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FenFicheClientController {
	
	@FXML
	private TextField codeTextField;
	@FXML
	private TextField nomTextField;
	@FXML
	private TextField prenomTextField;
	@FXML
	private CheckBox carteFideleCheckBox;
	@FXML
	private TextField dateCreationTextField;
	@FXML
	private Label dateDuJour;
	@FXML
	private Label titleLabel;
	
	private MainApp mainApp;

	  /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    	 dateDuJour.setText(DateUtil.format(LocalDate.now()));
	    }
	    
	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }
	    
	private Stage dialogStage;
	private Client client;
    private boolean okClicked = false;
	/**
   * Sets the stage of this dialog.
   *
   * @param dialogStage
   */
  public void setDialogStage(Stage dialogStage) {
      this.dialogStage = dialogStage;
  }
  
  /**
   * Sets the person to be edited in the dialog.
   *
   * @param person
   */
  public void setClient(Client client) {
      this.client = client;
      
      codeTextField.setText(client.getCode());
      nomTextField.setText(client.getNom());
      prenomTextField.setText(client.getPrenom());
      // gestion du selected de CheckBox
      if (client.isCarte_Fidele()==1)
      carteFideleCheckBox.setSelected(true);
      else
    	  carteFideleCheckBox.setSelected(false);
      dateCreationTextField.setText(DateUtil.format(client.getDate_creation()));
  }
  
  
  @FXML
  private void handleValider() {
	  if (isInputValid()) {
		  this.client.setCode(codeTextField.getText());
		  this.client.setNom(nomTextField.getText());
		  this.client.setPrenom(prenomTextField.getText());
		  if (carteFideleCheckBox.isSelected())
			  this.client.setCarte_fidele(1);
		  else
			  this.client.setCarte_fidele(0);
		  this.client.setDate_creation(DateUtil.parse(dateCreationTextField.getText()));
		  
	      
	      okClicked = true;
	      dialogStage.close();
	  }
  }

  private boolean isInputValid() {
      String errorMessage = "";

      if (codeTextField.getText() == null || codeTextField.getText().length() == 0) {
          errorMessage += "Code non valide!\n";
      }
      if (nomTextField.getText() == null || nomTextField.getText().length() == 0) {
          errorMessage += "Nom non valide!\n";
      }
      if (prenomTextField.getText() == null || prenomTextField.getText().length() == 0) {
          errorMessage += "Prenom non valide!\n";
      }
      if (dateCreationTextField.getText() == null || dateCreationTextField.getText().length() == 0) {
          errorMessage += "Date non valide!\n";
      } else {
          if (!DateUtil.validDate(dateCreationTextField.getText())) {
              errorMessage += "Date non valide. Utilisez le format dd.mm.yyyy!\n";
          }
      }
      
      
      if (errorMessage.length() == 0) {
          return true;
      } else {
          // Show the error message.
          Alert alert = new Alert(AlertType.ERROR);
          alert.initOwner(dialogStage);
          alert.setTitle("Champs invalides");
          alert.setHeaderText("Veillez corriger ces champs: ");
          alert.setContentText(errorMessage);

          alert.showAndWait();

          return false;
      }
  }
  
  public boolean isOkClicked() {
		return okClicked;
  }
  
  
  public void setTitleLabel(String title) {
	  titleLabel.setText(title);
  }
  
  @FXML
  private void handleRevenir() {
		this.dialogStage.close();
	}
  


}
