package ui;

import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import ui.view.TrainingController;
import ui.Coup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Training extends Task<Integer> {
	
	private double epochs = 1000000;

	private Button button;

	private Boolean isCanceled = false;

	private String difficulty = "Facile";

	public void setEpochs(double e)
	{
		this.epochs = e;
	}
	public void setButton(Button b)
	{
		this.button = b;
	}
	public void setDifficulty(String d) { this.difficulty = d;}
	
	@Override
	protected Integer call() throws Exception {

		int size = 9;
		double learningRate = 0.1; //0.1
		int h = 128;

		if(difficulty == "Difficile") {
			size = 9;
			learningRate = 0.9;
			h = 128;
		}

		System.out.println();
			System.out.println("START TRAINING ...");
			System.out.println("epoch : " + epochs);
			System.out.println("Type d'IA : " + difficulty);
			//
			int[] layers = new int[]{ size, h, size };
			//
			double error = 0.0 ;
			MultiLayerPerceptron net = new MultiLayerPerceptron(layers, learningRate, new SigmoidalTransferFunction());
			//double epochs = 1000000000;

			System.out.println("---");
			System.out.println("Load data ...");
			updateMessage("Loading data", 0, 0);
			HashMap<Integer, Coup> mapTrain = loadCoupsFromFile("./resources/train_dev_test/train.txt");
			HashMap<Integer, Coup> mapDev = loadCoupsFromFile("./resources/train_dev_test/dev.txt");
			HashMap<Integer, Coup> mapTest = loadCoupsFromFile("./resources/train_dev_test/test.txt");
			System.out.println("---");
			//TRAINING ...*/

		for(int i = 0; i < epochs; i++){
			if(isCanceled == true)
			{
				break;
			}
			Coup c = null ;
			while ( c == null )
				c = mapTrain.get((int)(Math.round(Math.random() * mapTrain.size())));

			error += net.backPropagate(c.in, c.out);

			if ( i % 10000 == 0 ){
				System.out.println("Error at step "+i+" is "+ (error/(double)i));
				updateMessage("Error at step "+i+" is "+ (error/(double)i), (i*100)/epochs, 100);
			}
		}
		error /= epochs ;
		if ( epochs > 0 && isCanceled == false) {
			System.out.println("Error is " + error);
			updateMessage("Error is " + error + " at the end", epochs, epochs);
			System.out.println("Learning completed!");

		}
		else
		{
			updateMessage("Learning phase cancelled");
			System.out.println("Learning canceled!");
		}

		net.save("./resources/models/"+ difficulty +".srl");
		button.setDisable(false);


		//TEST ...
		double[] inputs = new double[]{0.0, 1.0};
		double[] output = net.forwardPropagation(inputs);

		System.out.println(inputs[0]+" or "+inputs[1]+" = "+Math.round(output[0])+" ("+output[0]+")");


		return 10;
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) 
	{
		updateMessage("Cancelled");
		button.setDisable(false);
		isCanceled = mayInterruptIfRunning;
		return super.cancel(mayInterruptIfRunning);
	}

	
	protected void updateMessage(String msg, double workDone, double max)
	{
		updateMessage(msg);
		super.updateProgress(workDone, max);
		
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
	
	
}
