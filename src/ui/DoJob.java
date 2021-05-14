package ui;

import javafx.concurrent.Task;
import ui.view.TrainingController;
import ui.Coup;

public class DoJob extends Task<Integer> {
	
	double epochs = 1000000000;
	
	@Override
	protected Integer call() throws Exception {
		
		//HashMap<Integer, Coup> mapTrain = loadCoupsFromFile("./resources/train_dev_test/train.txt");
		//HashMap<Integer, Coup> mapDev = loadCoupsFromFile("./resources/train_dev_test/dev.txt");
		//HashMap<Integer, Coup> mapTest = loadCoupsFromFile("./resources/train_dev_test/test.txt");
		
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

		System.out.println(inputs[0]+" or "+inputs[1]+" = "+Math.round(output[0])+" ("+output[0]+")"); */
		for(int i = 0; i < 10; i++)
		{
			System.out.println(i + 1);
			updateProgress("oui", i + 1, 10);
			
			Thread.sleep(500);
			
			if(isCancelled())
			{
				return i; 
			}
		}
		return 10; 
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) 
	{
		updateMessage("Cancelled");
		return super.cancel(mayInterruptIfRunning);
	}

	
	protected void updateProgress(String msg, double workDone, double max)
	{
		updateMessage("progress: " + msg);
		super.updateProgress(workDone, max);
		
	}
	
	
}
