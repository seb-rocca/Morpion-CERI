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
	private Button stop;

	@FXML
	private ComboBox<String> iaDifficulty = new ComboBox<>();

	private Training task = new Training();

	@FXML
	private Spinner<Double> epochSpinner;

	@FXML
	public void lancementApplication() //lorsque l'utilisateur appuie sur le bouton "start" de l'interface
	{
		lancement.setDisable(true); //on desactive le bouton start lorsqu'on clique sur le bouton start
		stop.setDisable(false); //on active le bouton stop lorsqu'on clique sur le bouton start

		test(9); //on lance le test
	}
	
	public void setMainApp(MainApp mainApp)
	{
		iaDifficulty.setItems(FXCollections.observableArrayList(
				"Facile",
				"Difficile"
		)); //pour les valeurs de la combobox
		iaDifficulty.getSelectionModel().selectFirst(); //on choisi la premiere valeur founie ("Facile")
		this.mainApp = mainApp;
		epochSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(10000, 999999999, 1000000)); //on défini les valeurs min et max du spinner
		task.setButton(lancement);

	}

    @FXML
	public void setEpoch()
	{
		task.setEpochs(epochSpinner.getValue());
	}

	@FXML
	public void setDifficulty() //on défini la difficulté du modele
	{
		task.setDifficulty(iaDifficulty.getValue());
		mainApp.setDifficulty("./resources/models/"+ iaDifficulty.getValue() + ".srl");
		System.out.println(iaDifficulty.getValue());
	}

	@FXML
	public void Stop() //pour stopper l'entrainement
	{

		task.cancel(true);
		task = new Training();
		task.setButton(lancement);

	}

	@FXML
	public void retourMenu() //pour retourner au menu (arrête l'entrainement au passage)
	{
		task.cancel(true);
		mainApp.showMenuOverview();
	}
	
	
	public void test(int size){ //pour effectuer l'entrainement

		try {

			//on relie les élements de l'interface au valeurs du thread pour être actualisées
			progressBar.progressProperty().bind(task.progressProperty());
			progressCircle.progressProperty().bind(task.progressProperty());
			errorDisplay.textProperty().bind(task.messageProperty());

			new Thread(task).start(); //on lance le thread d'entrainement
			
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
