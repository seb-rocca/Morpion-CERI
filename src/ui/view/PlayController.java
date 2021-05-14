package ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ui.MainApp;

import java.util.ArrayList;

public class PlayController {
	private MainApp mainApp; 
	private boolean with_AI;

	@FXML
	private AnchorPane playPane;

	@FXML
	private Text playerTurn;

	private int turn = 0;

	private ArrayList<Integer> cases = new ArrayList<Integer>();
	
	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp;

		for(int i = 0; i < 9; i++)
		{
			cases.add(0);

		}

		playerTurn.setText("Joueur " + 1);
		playerTurn.setFill(Color.RED);
	}
	
	public void setAI(boolean ai)
	{
		this.with_AI = ai; 
		System.out.println(with_AI);
	}

	@FXML
	public void retourMenu()
	{
		mainApp.showMenuOverview();
	}

	public Boolean verify(int case_id, int current_player)
	{
		if( verify_diagonal(current_player) || verify_column(case_id, current_player) || verify_line(case_id, current_player) )
		{
			victory(current_player);
			return true;
		}
		return false;

	}

	public Boolean verify_diagonal(int current_player)
	{
			if(cases.get(0) == current_player && cases.get(4) == current_player && cases.get(8) == current_player)
			{
				return true;
			}
			else if(cases.get(2) == current_player && cases.get(4) == current_player && cases.get(6) == current_player)
			{
				return true;
			}

			return false;
	}

	public Boolean verify_column(int case_id, int current_player)
	{
		if(case_id/3 == 0 && cases.get(case_id+3) == current_player && cases.get(case_id+6) == current_player )
		{
			return true;
		}

		else if(case_id/3 == 1 && cases.get(case_id-3) == current_player && cases.get(case_id+3) == current_player)
		{
			return true;
		}

		else if(case_id/3 == 2 && cases.get(case_id-3) == current_player && cases.get(case_id-6) == current_player)
		{
			return true;
		}

		return false;
	}

	public Boolean verify_line(int case_id, int current_player)
	{
		if(case_id%3 == 0 && cases.get(case_id+1) == current_player && cases.get(case_id+2) == current_player)
		{
			return true;
		}

		else if(case_id%3 == 1 && cases.get(case_id+1) == current_player && cases.get(case_id-1) == current_player)
		{
			return true;
		}

		else if(case_id%3 == 2 && cases.get(case_id-1) == current_player && cases.get(case_id-2) == current_player)
		{
			return true;
		}

		return false;

	}

	public void victory(int player)
	{
		mainApp.showWinOverview(playPane, "Félicitation Joueur " + player + " !");
	}

	public void equal()
	{
		mainApp.showWinOverview(playPane, "Egalité !");
	}

	@FXML
	private void handleOnMouseClicked(MouseEvent event)
	{
		boolean result = false;
		//System.out.println("You clicked Pane: " + ((BorderPane)event.getSource()).getId());
		int case_id = Integer.valueOf(((BorderPane)event.getSource()).getId());
		if(turn % 2 == 0 && cases.get(case_id) == 0)
		{
			((BorderPane)event.getSource()).setCenter(new Circle(20));
			cases.set(case_id, 1);
			turn++;
			result = verify(case_id, 1);
			playerTurn.setText("Joueur " + 2);
			playerTurn.setFill(Color.BLUE);

		}
		else if(turn % 2 != 0 && cases.get(case_id) == 0)
		{
			Text t  = new Text();
			t.setText("X");
			t.setFont(new Font(96));
			((BorderPane)event.getSource()).setCenter(t) ;;
			cases.set(case_id, 2);
			turn++;
			result = verify(case_id, 2);
			playerTurn.setText("Joueur " + 1);
			playerTurn.setFill(Color.RED);

		}
		if(turn == 9 && !result)
		{
			equal();
		}
	}

}
