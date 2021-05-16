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

	private Morpion morpion;

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
	} //pour retourner au menu

	public void playWithAI(MouseEvent event) //pour jouer avec l'IA
	{
		boolean result = false;

		int case_id = Integer.valueOf(((BorderPane)event.getSource()).getId());
		if(morpion.getCasesValue(case_id) == 0) { //si la case est valide (c'est a dire pas jouée)
			Image img = new Image(getClass().getResource("images/circle.png").toExternalForm()); //on charge l'image du cercle
			ImageView imView = new ImageView();
			imView.setImage(img);
			FadeTransition transition = new FadeTransition(Duration.millis(1000), imView);
			transition.setFromValue(0);
			transition.setToValue(1);

			((BorderPane) event.getSource()).setCenter(imView); //on place le cercle au centre de la case jouée
			transition.play(); //on joue la transition
			morpion.setCasesValue(case_id, 1); //on met la case comme ayant été jouée par le joueur 1
			turn++;
			result = morpion.verify(case_id, 1); //on vérifie si il y a un gagnant ou égalité

			playerTurn.setText("Joueur " + 2); //on affiche que c'est le tour du joueur 2
			playerTurn.setFill(Color.BLUE); //avec un texte de couleur bleu

			if (result) { //si il y a vainqueur
				mainApp.showWinOverview(playPane, "Félicitation Joueur 1 !", with_AI); //on félicite le joueur
			} else if (turn == 9 && !result) { //si il y a égalité
				mainApp.showWinOverview(playPane, "Egalité !", with_AI);
			} else {

				int pion = morpion.IAturn(); //on fait joueur l'IA (elle choisi une case a jouer)


				morpion.setCasesValue(pion, -1); //on met la case comme ayant été jouée par l'IA

				//même étapes que pour le joueur 1...
				Image img2 = new Image(getClass().getResource("images/cross.png").toExternalForm());
				ImageView imView2 = new ImageView();
				imView2.setImage(img2);
				FadeTransition transition2 = new FadeTransition(Duration.millis(1000), imView2);
				transition2.setFromValue(0);
				transition2.setToValue(1);
				BorderPane p1 = (BorderPane) gridp.lookup("#" + pion);
				p1.setCenter(imView2);
				transition2.play();
				result = morpion.verify(pion, -1);

				playerTurn.setText("Joueur " + 1);
				playerTurn.setFill(Color.RED);

				if (result) {
					mainApp.showWinOverview(playPane, "Félicitation à L'IA !", with_AI);
				} else if (turn == 9 && !result) {
					mainApp.showWinOverview(playPane, "Egalité !", with_AI);
				}
				turn++;
			}
		}



	}

	public void playWithFriend(MouseEvent event) //pour jouer avec un ami
	{
		boolean result = false;

		int case_id = Integer.valueOf(((BorderPane)event.getSource()).getId()); //récuperer l'ID associé a la case
		if(turn % 2 == 0 && morpion.getCasesValue(case_id) == 0) //savoir le joueur et que la case n'est pas déjà jouée
		{
			Image img = new Image(getClass().getResource("images/circle.png").toExternalForm()); //on charge l'image du cercle
			ImageView imView = new ImageView();
			imView.setImage(img);

			//Circle c = new Circle(20);
			FadeTransition transition= new FadeTransition(Duration.millis(1000), imView); //on met une transition
			transition.setFromValue(0);
			transition.setToValue(1);

			((BorderPane)event.getSource()).setCenter(imView); //on la place sur la case jouée
			transition.play(); //on joue la transition associée
			morpion.setCasesValue(case_id, 1); //on met la case comme ayant été jouée par le joueur 1

			result = morpion.verify(case_id, 1); //on vérifie si il a gagné ou égalité
			playerTurn.setText("Joueur " + 2); //on dit que c'est le tour du joueur 2
			playerTurn.setFill(Color.BLUE);

			turn++;

		}
		else if(turn % 2 != 0 && morpion.getCasesValue(case_id) == 0) //même chose mais pour le joueur 2
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
		if(result) //si il y a un gagnant
		{
			if( turn % 2 == 1)
			{
				mainApp.showWinOverview(playPane, "Félicitation Joueur 1 !", with_AI); //on félicite le joueur 1
			}
			else
			{
				mainApp.showWinOverview(playPane, "Félicitation Joueur 2 !", with_AI); //ou le joueur 2
			}

		}
		else if(turn == 9 && !result) // il y a égalité
		{
			mainApp.showWinOverview(playPane, "Egalité !", with_AI);
		}
	}

	@FXML
	public void handleOnMouseClicked(MouseEvent event) //lorsque l'une des case est cliquée
	{
		if(with_AI == true) //si l'IA est activée, on joue avec
		{
			playWithAI(event);
		}
		else //sinon on joue contre un ami
		{
			playWithFriend(event);
		}
	}

}
