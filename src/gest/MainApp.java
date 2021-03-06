package gest;
/**
 * KOUASSI YVES ANSELME MAGLOIRE
 */
import java.io.IOException;
import java.util.ArrayList;


import gest.model.Article;
import gest.model.Client;
import gest.model.Commande;
import gest.model.LignesCommandes;
import gest.view.FenArticlesController;
import gest.view.FenChoixArticleController;
import gest.view.FenChoixClientController;
import gest.view.FenCommandesController;
import gest.view.FenConnexionController;
import gest.view.FenExportController;
import gest.view.FenFicheClientController;
import gest.view.FenMenuPrincipalController;
import gest.view.FenStatsController;
import gest.view.FenTableClientController;
import gest.view.FenTableCommandesController;
import gest.view.FenTableauBordController;
import gest.view.FenUtilisateurDBController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import messervices.MonService;
import messervices.MonServiceService;

public class MainApp extends Application {

	private final String nomEntrepriste= "OXYGEN";
	private ArrayList<Client> clientData = new ArrayList<Client>();
	private ArrayList<Article> articleData = new ArrayList<Article>();
	private ArrayList<Commande> commandeData = new ArrayList<Commande>();
	private ArrayList<LignesCommandes> lignesCommandesData = new ArrayList<LignesCommandes>();
    // Important pour passer au type de list Observable
    ObservableList<Client> clientDataObservale = FXCollections.observableArrayList(clientData);
    ObservableList<Article> articleDataObservale = FXCollections.observableArrayList(articleData);
    ObservableList<Commande> commandeDataObservale = FXCollections.observableArrayList(commandeData);
    ObservableList<LignesCommandes> lignesCommandeDataObservale = FXCollections.observableArrayList(lignesCommandesData);
    
    
    String urlLogo = "file:resources/images/icone_eclipse.png";
    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Client> getClientData() {
        return clientDataObservale;
    }

    public ObservableList<Article> getArticleData() {
        return articleDataObservale;
    }
    
    public ObservableList<Commande> getCommandeData() {
        return commandeDataObservale;
    }
    
    public ObservableList<LignesCommandes> getLignesCommandeData() {
        return lignesCommandeDataObservale;
    }
    
    //Utiliser pour la recherche assist� dans FenArticles.FXML
    public void reloadListArticle() {
    	articleDataObservale.clear();
    	articleDataObservale=FXCollections.observableArrayList((new Article()).getLesEnreg());
    }
    
    //Utiliser pour la recherche assist� dans FenCommandes.FXML
    public void reloadListCommande() {
    	commandeDataObservale.clear();
    	commandeDataObservale=FXCollections.observableArrayList((new Commande()).getLesEnreg());
    }
    
    
	private Stage primaryStage;
	
	public MainApp() {
        /*
         * Les chargements de donn�es sont effectu�es dans la fenetre
         * FenControleConnexion apr�s que la v�rification du nom et
         * du mot de passe se soit pass� avec succ�s
         */
		
	}
	
	public void chargerLesDonnees() {
		// Add some sample data
		// Traimenents des donnn�es
		// Reccuperation des clients dans la base de donn�es
		// Add some sample data
		clientDataObservale=FXCollections.observableArrayList((new Client()).getlesEnreg());
				
		articleDataObservale=FXCollections.observableArrayList((new Article()).getLesEnreg());
				
		commandeDataObservale=FXCollections.observableArrayList((new Commande()).getLesEnreg());
				
		lignesCommandeDataObservale=FXCollections.observableArrayList((new LignesCommandes()).getLesEnreg());
		
		//lignesCommandeDataObservale.clear();
		
	}

	@Override
	public void start(Stage primaryStage) {
		this.setPrimaryStage(primaryStage);
        this.getPrimaryStage().setTitle("Gestion");

        // Set the application icon.
       this.getPrimaryStage().getIcons().add(new Image(urlLogo));

        initPrimaryLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initPrimaryLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenConnexion.fxml"));
            AnchorPane FenConnexion = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(FenConnexion);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().setResizable(false);
            // Give the controller access to the main app.
            FenConnexionController controller = loader.getController();
            controller.setMainApp(this);
            
            getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showFenMenuPrincipal() {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenMenuPrincipal.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Menu Principal");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneMenuPrincipal = new Scene(page);
            dialogStage.setScene(sceneMenuPrincipal);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenMenuPrincipalController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            
            // Show the dialog
            dialogStage.show();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }
	    }

	
	public void showFenTableClient() {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenTableClient.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(nomEntrepriste+" Clients");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneTableClient = new Scene(page);
            dialogStage.setScene(sceneTableClient);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenTableClientController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            
            // Show the dialog
            dialogStage.show();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }
	    }
	
	public boolean showFenFicheClient(Client client,String demande) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenFicheClient.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(demande +" un client");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneFicheClient = new Scene(page);
            dialogStage.setScene(sceneFicheClient);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenFicheClientController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //controller.setMainApp(this);
            controller.setClient(client);
            controller.setTitleLabel(demande + " un client");
            controller.setbuttonAction(demande);
            // gestion du cas de la recher 
            if(demande.equals("Rechercher"))
            	{
            		controller.getOkButton().setVisible(true);
            		controller.disableField();
            	}
            
            // Show the dialog
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        	}
			catch (IOException e) {
            e.printStackTrace();
            return false;
            }
	    }
	
	public void showFenArticle() {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenArticles.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(nomEntrepriste+" Articles");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneArticle = new Scene(page);
            dialogStage.setScene(sceneArticle);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenArticlesController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            
            // Show the dialog
            dialogStage.show();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }
	    }


	public void showFenCommandes() {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenCommandes.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(nomEntrepriste+" Commandes");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneCommandes = new Scene(page);
            dialogStage.setScene(sceneCommandes);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenCommandesController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            
            // Show the dialog
            dialogStage.show();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }
	    }

	
	public void showFenTableCommandes() {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenTableCommandes.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(nomEntrepriste+" Commandes");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneTableCommandes = new Scene(page);
            dialogStage.setScene(sceneTableCommandes);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenTableCommandesController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            
            // Show the dialog
            dialogStage.showAndWait();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }
	    }
	
	public boolean showFenChoixClient(Client client) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenChoixClient.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(nomEntrepriste+" Choix Client");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneChoixClient = new Scene(page);
            dialogStage.setScene(sceneChoixClient);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenChoixClientController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //controller.setMainApp(this);
            controller.setClient(client);
            
            
            // Show the dialog
            dialogStage.showAndWait();
            return controller.isrowDoubleClicked();
        	}
			catch (IOException e) {
            e.printStackTrace();
            return false;
            }

	    }
	
	public boolean showFenChoixArticle(Article article) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenChoixArticle.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(nomEntrepriste+" Choix Article");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneChoixArticle = new Scene(page);
            dialogStage.setScene(sceneChoixArticle);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenChoixArticleController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //controller.setMainApp(this);
            controller.setArticle(article);
            
            
            // Show the dialog
            dialogStage.showAndWait();
            return controller.isrowDoubleClicked();
        	}
			catch (IOException e) {
            e.printStackTrace();
            return false;
            }

	    }
	
	public void showFenExport(String codeCommande, String model) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenExport.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Exportation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneExport = new Scene(page);
            dialogStage.setScene(sceneExport);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenExportController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //controller.setMainApp(this);
            controller.setCode(codeCommande);
            controller.setModel(model);
            
            // Show the dialog
            dialogStage.showAndWait();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }

	    }
	
	
	public void showFenUtilisateurDB() {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenUtilisateurDB.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(nomEntrepriste+" Commandes");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneUtilisateurDB = new Scene(page);
            dialogStage.setScene(sceneUtilisateurDB);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenUtilisateurDBController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            
            // Show the dialog
            dialogStage.showAndWait();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }
	    }
	
	public void showFenTableauBord() {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenTableauBord.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(nomEntrepriste+" Tableau de bord");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneFenTableauBord = new Scene(page);
            dialogStage.setScene(sceneFenTableauBord);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(false);
            
            // Set the person into the controller.
            FenTableauBordController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            
            // Show the dialog
            dialogStage.showAndWait();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }
	    }
	

	public void showFenStats(String annee) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FenStats.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(nomEntrepriste+" statistique");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene sceneFenStats = new Scene(page);
            dialogStage.setScene(sceneFenStats);
            dialogStage.getIcons().add(new Image(urlLogo));
            dialogStage.centerOnScreen();
            dialogStage.setResizable(true);
            
            // Set the person into the controller.
            FenStatsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            controller.setAnnee(annee);
            controller.recupData();

            // Show the dialog
            dialogStage.showAndWait();
        	}
			catch (IOException e) {
            e.printStackTrace();
            }
	    }
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}	

	
}
	


