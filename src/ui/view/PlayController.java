package ui.view;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ui.MainApp;
import ui.model.Morpion;

public class PlayController {
	private MainApp mainApp; 
	private boolean with_AI;

	@FXML
	private AnchorPane playPane;

	@FXML
	private Text playerTurn;

	@FXML
	private GridPane gridp;

	private Morpion morpion; // = new Morpion(gridp);

	private int turn = 0;

	public void setMainApp(MainApp mainApp, boolean with_AI)
	{
		this.mainApp = mainApp;
		playerTurn.setText("Joueur " + 1);
		playerTurn.setFill(Color.RED);
		morpion = new Morpion(gridp, mainApp.getDifficulty(), with_AI);
		this.with_AI = with_AI;
	}

	@FXML
	public void retourMenu()
	{
		mainApp.showMenuOverview();
	}

	public void playWithAI(MouseEvent event)
	{
		boolean result = false;

		int case_id = Integer.valueOf(((BorderPane)event.getSource()).getId());
		Image img = new Image(getClass().getResource("images/circle.png").toExternalForm());
		ImageView imView = new ImageView();
		imView.setImage(img);
		FadeTransition transition= new FadeTransition(Duration.millis(1000), imView);
		transition.setFromValue(0);
		transition.setToValue(1);

		((BorderPane)event.getSource()).setCenter(imView);
		transition.play();
		morpion.setCasesValue(case_id, 1);
		turn++;
		result = morpion.verify(case_id, 1);

		playerTurn.setText("Joueur " + 2);
		playerTurn.setFill(Color.BLUE);

		if(result)
		{
				mainApp.showWinOverview(playPane, "Félicitation Joueur 1 !", with_AI);
		}
		else if(turn == 9 && !result)
		{
			mainApp.showWinOverview(playPane, "Egalité !", with_AI);
		}
		else {

			int pion = morpion.IAturn();


			morpion.setCasesValue(pion, -1);
			Image img2 = new Image(getClass().getResource("images/cross.png").toExternalForm());
			ImageView imView2 = new ImageView();
			imView2.setImage(img2);
			FadeTransition transition2 = new FadeTransition(Duration.millis(1000), imView2);
			transition2.setFromValue(0);
			transition2.setToValue(1);
			BorderPane p1 = (BorderPane)  gridp.lookup("#" + pion);
			p1.setCenter(imView2);
			transition2.play();
			result = morpion.verify(pion, -1);


			if(result)
			{
				mainApp.showWinOverview(playPane, "Félicitation à L'IA !", with_AI);
			}
			else if(turn == 9 && !result)
			{
				mainApp.showWinOverview(playPane, "Egalité !", with_AI);
			}
			turn++;
		}



	}

	public void playWithFriend(MouseEvent event)
	{
		boolean result = false;
		//System.out.println("You clicked Pane: " + ((BorderPane)event.getSource()).getId());
		int case_id = Integer.valueOf(((BorderPane)event.getSource()).getId());
		if(turn % 2 == 0 && morpion.getCasesValue(case_id) == 0)
		{
			Image img = new Image(getClass().getResource("images/circle.png").toExternalForm());
			ImageView imView = new ImageView();
			imView.setImage(img);

			//Circle c = new Circle(20);
			FadeTransition transition= new FadeTransition(Duration.millis(1000), imView);
			transition.setFromValue(0);
			transition.setToValue(1);

			((BorderPane)event.getSource()).setCenter(imView);
			transition.play();
			morpion.setCasesValue(case_id, 1);

			result = morpion.verify(case_id, 1);
			playerTurn.setText("Joueur " + 2);
			playerTurn.setFill(Color.BLUE);

			turn++;

		}
		else if(turn % 2 != 0 && morpion.getCasesValue(case_id) == 0)
		{
			Image img = new Image(getClass().getResource("images/cross.png").toExternalForm());
			ImageView imView = new ImageView();
			imView.setImage(img);
			FadeTransition transition= new FadeTransition(Duration.millis(1000), imView);
			transition.setFromValue(0);
			transition.setToValue(1);
			((BorderPane)event.getSource()).setCenter(imView) ;;
			transition.play();
			morpion.setCasesValue(case_id, -1);

			result = morpion.verify(case_id, -1);
			playerTurn.setText("Joueur " + 1);
			playerTurn.setFill(Color.RED);

			turn++;

		}
		if(result)
		{
			if( turn % 2 == 1)
			{
				mainApp.showWinOverview(playPane, "Félicitation Joueur 1 !", with_AI);
			}
			else
			{
				mainApp.showWinOverview(playPane, "Félicitation Joueur 2 !", with_AI);
			}

		}
		else if(turn == 9 && !result)
		{
			mainApp.showWinOverview(playPane, "Egalité !", with_AI);
		}
	}

	@FXML
	public void handleOnMouseClicked(MouseEvent event)
	{
		if(with_AI == true)
		{
			playWithAI(event);
		}
		else
		{
			playWithFriend(event);
		}
	}

}
