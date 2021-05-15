package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.model.IASettings;
import ui.view.*;

public class MainApp extends Application {

	
	private Stage lancementStage; 
	private BorderPane rootLayout;

	private String difficulty = "./resources/models/Facile.srl";

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}


	
	@Override
	public void start(Stage primaryStage) {
		this.lancementStage = primaryStage;
		this.lancementStage.setTitle("Morpion");
		this.lancementStage.setResizable(false); 
		
		initRootLayout();
		
		//showLaunchOverview(); 
		showMenuOverview(); 
	}
	
	 public void initRootLayout() {
	        try {
	            // Load root layout from fxml file.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
	            
	            rootLayout = (BorderPane) loader.load();
	            
	            // Show the scene containing the root layout.
	            	            
	            Scene scene = new Scene(rootLayout);
	            lancementStage.setScene(scene);
	            lancementStage.show();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public void showTrainingOverview() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/TrainingOverview.fxml"));
	            AnchorPane launchOverview = (AnchorPane) loader.load();
	            
	            //rootLayout.setCenter(launchOverview);
	            this.lancementStage.setTitle("Morpion - Paramètres l'IA");
	            // Set person overview into the center of root layout.
	            TrainingController controller = loader.getController();
	            controller.setMainApp(this); 
	            
	            rootLayout.setCenter(launchOverview);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public void showMenuOverview()
	 {
		 try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/MenuOverview.fxml"));
	            AnchorPane MenuOverview = (AnchorPane) loader.load();

			 	this.lancementStage.setTitle("Morpion - Menu");
	            // Set person overview into the center of root layout.
	            MenuController controller = loader.getController();
	            controller.setMainApp(this); 
	            
	            rootLayout.setCenter(MenuOverview);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }



	 public void showRulesOverview()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RulesOverview.fxml"));
			AnchorPane MenuOverview = (AnchorPane) loader.load();
			this.lancementStage.setTitle("Morpion - Règles du jeu");

			// Set person overview into the center of root layout.
			RulesController controller = loader.getController();
			controller.setMainApp(this);

			rootLayout.setCenter(MenuOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showDescriptionOverview()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/DescriptionOverview.fxml"));
			AnchorPane MenuOverview = (AnchorPane) loader.load();
			this.lancementStage.setTitle("Morpion - Qui sommes-nous ?");

			// Set person overview into the center of root layout.
			DescriptionController controller = loader.getController();
			controller.setMainApp(this);

			rootLayout.setCenter(MenuOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	 public void showWinOverview(AnchorPane p, String s, boolean withAI)
	 {
		 try
		 {
		 FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(MainApp.class.getResource("view/WinOverview.fxml"));
		 this.lancementStage.setTitle("Morpion - " + s);
		 AnchorPane WinOverView = (AnchorPane) loader.load();

		 WinController controller = loader.getController();
		 controller.setMainApp(this, withAI);
		 controller.sendResult(s);
		 p.getChildren().add(WinOverView);
		 } catch (IOException e) {
			 e.printStackTrace();
		 }



	 }
	 
	 public void showPlayOverview(boolean with_ai)
	 {
		  try {
			  FXMLLoader loader = new FXMLLoader(); 
			  loader.setLocation(MainApp.class.getResource("view/PlayOverview.fxml"));
			  AnchorPane playOverview = (AnchorPane) loader.load();

			  this.lancementStage.setTitle("Morpion - Jouer");

			  PlayController controller = loader.getController(); 
			  controller.setMainApp(this);
			  controller.setAI(with_ai, difficulty);
			  
			  rootLayout.setCenter(playOverview); 
			  
		  }
		  catch (IOException e) {
			  e.printStackTrace();
		  }
	 }
	 
	 public Stage getLancementStage()
	 {
		 return lancementStage; 
	 }

	public static void main(String[] args) {
		launch(args);
	}
}
