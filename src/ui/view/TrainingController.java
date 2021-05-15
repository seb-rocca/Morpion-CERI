package ui.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.concurrent.Task;
import ui.Training;
import ui.MainApp;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import ui.Coup;

public class TrainingController implements Initializable {
	
	@FXML
	private Button lancement;
	
	@FXML
	private TextField errorDisplay;
	
	private MainApp mainApp;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private ProgressIndicator progressCircle;

	@FXML
	private Button retourMenu;

	@FXML
	private Button stop;

	@FXML
	private ComboBox<String> iaDifficulty = new ComboBox<>();

	private Training task = new Training();

	@FXML
	private Spinner<Double> epochSpinner;

	@FXML
	public void lancementApplication()
	{
		/*
			Test t = new Test(); 
			t.main(null);
		*/

		lancement.setDisable(true);
		stop.setDisable(false);

		test(9);
	}
	
	public void setMainApp(MainApp mainApp)
	{
		iaDifficulty.setItems(FXCollections.observableArrayList(
				"Facile",
				"Difficile"
		));
		iaDifficulty.getSelectionModel().selectFirst();
		this.mainApp = mainApp;
		epochSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10000, 999999999, 1000000));
		task.setButton(lancement);

	}

    @FXML
	public void setEpoch()
	{
		task.setEpochs(epochSpinner.getValue());
	}

	@FXML
	public void setDifficulty()
	{
		task.setDifficulty(iaDifficulty.getValue());
		mainApp.setDifficulty("./resources/models/"+ iaDifficulty.getValue() + ".srl");
		System.out.println(iaDifficulty.getValue());
	}

	@FXML
	public void Stop()
	{

		task.cancel(true);
		task = new Training();
		task.setButton(lancement);

	}

	@FXML
	public void retourMenu()
	{
		task.cancel(true);
		mainApp.showMenuOverview();
	}
	
	
	public void test(int size){

		try {

			progressBar.progressProperty().bind(task.progressProperty());
			progressCircle.progressProperty().bind(task.progressProperty());
			errorDisplay.textProperty().bind(task.messageProperty());

			new Thread(task).start();
			
		} 
		catch (Exception e) {
			System.out.println("Test.test()");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static Task<Double> getTask(HashMap<Integer, Coup> mapTrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	
}
