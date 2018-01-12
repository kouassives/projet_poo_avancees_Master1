package gest.view;

import java.time.LocalDate;
import java.util.ArrayList;

import gest.MainApp;
import gest.model.Client;
import gest.util.DateUtil;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FenFicheClientController {
	
	@FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, String> code;
    @FXML
    private TableColumn<Client, String> nom;
    @FXML
    private TableColumn<Client, String> prenom;
    @FXML
    private TableColumn<Client, String> carte_Fidele;
    @FXML
    private TableColumn<Client, String> date_Creation;

	
	
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
	private TextField adresseTextField;
	@FXML
	private TextField codePostalTextField;
	@FXML
	private TextField telFixeTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField villeTextField;
	@FXML
	private TextField mobilisTextField;
	@FXML
	private Label dateDuJour;
	@FXML
	private Label titleLabel;
	@FXML
	private Button buttonAction;
	@FXML
	private Button okButtun;
	
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
  
  public void setbuttonAction(String action) {
		buttonAction.setText(action);
	}
  
  public Button getOkButton() {
		return okButtun;
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
	  if (buttonAction.getText().equals("Rechercher"))
	  {
		  if(codeTextField.getText() != null && codeTextField.getText().length() !=0)
		  {
			  client = new Client(codeTextField.getText());
		      //Recherche dans la base de données
			  ArrayList<Client> nouvelleListe = client.chercherCRUD(codeTextField.getText());
			  ObservableList<Client> nouvelleListeDataObservale = FXCollections.observableArrayList(nouvelleListe);
			  	clientTable.setItems(nouvelleListeDataObservale);
			  	code.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
		    	nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		    	prenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		    	carte_Fidele.setCellValueFactory(cellData -> {
		    		Integer carte = cellData.getValue().carte_FideleProperty().intValue();
		    		
		    		String carteAsString;
		    		if(carte==1)
		            {
		                carteAsString = "Oui";
		            }
		            else
		            {
		            	carteAsString = "Non";
		            }
					return new ReadOnlyStringWrapper(carteAsString);
		        });
		    		date_Creation.setCellValueFactory(cellData -> {
		    		String datechaine = DateUtil.format(cellData.getValue().date_creationProperty().getValue());
		    		return new ReadOnlyStringWrapper(datechaine);
		    	});
		    	
		    
		    	clientTable.getSelectionModel().selectedItemProperty().addListener(
		    		            (observable, oldValue, newValue) -> showClientDetails(newValue));
		    
		  }else {
			// Show the error message.
	          Alert alert = new Alert(AlertType.ERROR);
	          alert.initOwner(dialogStage);
	          alert.setTitle("Champs Vide");
	          alert.setHeaderText("Veillez écrire au moins l'un des champs");
	          alert.setContentText("");

	          alert.showAndWait();
		  }  
	  }
	  else
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

  @FXML
  private void handleOk() {
		  int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
		  if (selectedIndex >= 0) {
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
		    else
		    {
		    	// Show the error message.
		          Alert alert = new Alert(AlertType.ERROR);
		          alert.initOwner(dialogStage);
		          alert.setTitle("Champs Vide");
		          alert.setHeaderText("Veillez selectionner le client recherché");
		          alert.setContentText("");
	
		          alert.showAndWait();
		    }
	
  }
  private void showClientDetails(Client client) {
  	if(client != null) {
  		nomTextField.setText(client.getNom());
  		prenomTextField.setText(client.getPrenom());
  		codeTextField.setText(client.getCode());
  		if (client.isCarte_Fidele()==1)
  			carteFideleCheckBox.setSelected(true);
  		else 
  			carteFideleCheckBox.setSelected(false);
  		dateCreationTextField.setText(DateUtil.format(client.date_creationProperty().getValue()));
  	}
  	else {
  		nomTextField.setText("");
  		prenomTextField.setText("");
  		codeTextField.setText("");
  		dateCreationTextField.setText("");
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
  
  public void disableField() {
	  
   		dateCreationTextField.setDisable(true);
   		carteFideleCheckBox.setDisable(true);
   		adresseTextField.setDisable(true);
   		codePostalTextField.setDisable(true);
   		telFixeTextField.setDisable(true);
   		emailTextField.setDisable(true);
 		dateDuJour.setDisable(true);
 		villeTextField.setDisable(true);
 		mobilisTextField.setDisable(true);
 		
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
