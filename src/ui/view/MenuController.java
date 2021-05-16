package ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.MainApp;

public class MenuController {
	
	private MainApp mainApp; 
	
	@FXML
	Button play_alone = new Button();
	
	@FXML
	Button play_friend = new Button(); 
	
	@FXML
	Button settings = new Button(); 
	
	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp; 
	}
	
	//ici on défini les différentes navigations possibles vers les différentes scenes

	@FXML
	public void goToPlayAlone()
	{
		mainApp.showPlayOverview(true); 
	}
	
	@FXML
	public void goToPlayFriend()
	{
		mainApp.showPlayOverview(false);
	}

	@FXML
	public void goToRules()
	{
		mainApp.showRulesOverview();
	}
	
	@FXML
	public void goToAITraining()
	{
		mainApp.showTrainingOverview();
	}

	@FXML
	public void goToDescription()
	{
		mainApp.showDescriptionOverview();
	}



}
