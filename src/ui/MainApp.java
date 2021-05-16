package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
		this.lancementStage.setTitle("Morpion - Menu");
		this.lancementStage.setResizable(false); 
		
		initRootLayout();

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

	public void showMenuOverview()
	{
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MenuOverview.fxml"));
			AnchorPane menuOverview = (AnchorPane) loader.load();

			this.lancementStage.setTitle("Morpion - Menu");

			MenuController controller = loader.getController();
			controller.setMainApp(this);

			rootLayout.setCenter(menuOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 public void showTrainingOverview() {
	        try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/TrainingOverview.fxml"));
	            AnchorPane trainingOverview = (AnchorPane) loader.load();

	            this.lancementStage.setTitle("Morpion - Paramètres l'IA");
	            // Set person overview into the center of root layout.
	            TrainingController controller = loader.getController();
	            controller.setMainApp(this); 
	            
	            rootLayout.setCenter(trainingOverview);
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
			AnchorPane rulesOverview = (AnchorPane) loader.load();
			this.lancementStage.setTitle("Morpion - Règles du jeu");

			RulesController controller = loader.getController();
			controller.setMainApp(this);

			rootLayout.setCenter(rulesOverview);
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
			AnchorPane descriptionOverview = (AnchorPane) loader.load();
			this.lancementStage.setTitle("Morpion - Qui sommes-nous ?");

			DescriptionController controller = loader.getController();
			controller.setMainApp(this);

			rootLayout.setCenter(descriptionOverview);
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
		 AnchorPane winOverview = (AnchorPane) loader.load();

		 WinController controller = loader.getController();
		 controller.setMainApp(this, withAI);
		 controller.sendResult(s);
		 p.getChildren().add(winOverview);
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
			  controller.setMainApp(this, with_ai);
			  //controller.setAI(with_ai, difficulty);
			  
			  rootLayout.setCenter(playOverview); 
			  
		  }
		  catch (IOException e) {
			  e.printStackTrace();
		  }
	 }

	public static void main(String[] args) {
		launch(args);
	}
}
