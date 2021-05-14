package ui.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.concurrent.Task;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Service; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import ui.DoJob;
import ui.MainApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import ai.Test;
import ui.Coup;

public class TrainingController implements Initializable {
	
	@FXML
	Button lancement = new Button();
	
	@FXML
	TextField errorAffichage = new TextField();
	
	private MainApp mainApp; 
	
	@FXML 
	ProgressBar progressBar = new ProgressBar(); 
	
	@FXML
	ProgressIndicator progressCircle = new ProgressIndicator();

	@FXML
	Button retourMenu;
	
	//private static Task <Double>task; 
	
	
	
	@FXML
	public void lancementApplication()
	{
		/*
			Test t = new Test(); 
			t.main(null);
		*/
		
		//errorAffichage.setText("lol");
			
		
		test(9);
	}
	
	public TrainingController() {}
	
	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp; 
	}

	@FXML 
    public void Pause()
    {
			
    }	
	
	@FXML
	public void Stop()
	{
			
	}

	@FXML
	public void retourMenu()
	{
		mainApp.showMenuOverview();
	}
	
	
	public void test(int size){
		try {
			System.out.println();
			System.out.println("START TRAINING ...");
			System.out.println();
			//
			int[] layers = new int[]{ size, 128, size };
			//
			double error = 0.0 ;
			MultiLayerPerceptron net = new MultiLayerPerceptron(layers, 0.1, new SigmoidalTransferFunction());
			double epochs = 1000000000;

			System.out.println("---");
			System.out.println("Load data ...");
			HashMap<Integer, Coup> mapTrain = loadCoupsFromFile("./resources/train_dev_test/train.txt");
			//HashMap<Integer, Coup> mapDev = loadCoupsFromFile("./resources/train_dev_test/dev.txt");
			//HashMap<Integer, Coup> mapTest = loadCoupsFromFile("./resources/train_dev_test/test.txt");
			System.out.println("---");
			//TRAINING ...
			
			DoJob task = new DoJob();
			
			progressBar.progressProperty().bind(task.progressProperty());
			progressCircle.progressProperty().bind(task.progressProperty());
			errorAffichage.textProperty().bind(task.messageProperty());
			
			
			new Thread(task).start(); 
			
			//progressBar.progressProperty().unbind();
						
			//progressCircle.progressProperty().unbind();			
			
			//task = getTask(mapTrain); 	
					
			//task.messageProperty().addListener(new ChangeListener<String>( {});
			
			
			
			/*for(int i = 0; i < epochs; i++){

				Coup c = null ;
				while ( c == null )
					c = mapTrain.get((int)(Math.round(Math.random() * mapTrain.size())));

				error += net.backPropagate(c.in, c.out);

				if ( i % 10000 == 0 ) System.out.println("Error at step "+i+" is "+ (error/(double)i));
			}
			error /= epochs ;
			if ( epochs > 0 )
				System.out.println("Error is "+error);
			//
			System.out.println("Learning completed!");

			//TEST ...
			double[] inputs = new double[]{0.0, 1.0};
			double[] output = net.forwardPropagation(inputs);

			System.out.println(inputs[0]+" or "+inputs[1]+" = "+Math.round(output[0])+" ("+output[0]+")"); 
			*/ 
			
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
	
	

	public static HashMap<Integer, Coup> loadCoupsFromFile(String file){
		System.out.println("loadCoupsFromFile from "+file+" ...");
		HashMap<Integer, Coup> map = new HashMap<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
			String s = "";
			while ((s = br.readLine()) != null) {
				String[] sIn = s.split("\t")[0].split(" ");
				String[] sOut = s.split("\t")[1].split(" ");

				double[] in = new double[sIn.length];
				double[] out = new double[sOut.length];

				for (int i = 0; i < sIn.length; i++) {
					in[i] = new Double(sIn[i]);
				}

				for (int i = 0; i < sOut.length; i++) {
					out[i] = new Double(sOut[i]);
				}

				Coup c = new Coup(9, "");
				c.in = in ;
				c.out = out ;
				map.put(map.size(), c);
			}
			br.close();
		} 
		catch (Exception e) {
			System.out.println("Test.loadCoupsFromFile()");
			e.printStackTrace();
			System.exit(-1);
		}
		return map ;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	
}
