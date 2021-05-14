package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
//
import java.util.HashMap;
import java.util.HashSet;

class Coup{

	public Coup(int size, String game) {
		if ( size > 0 ) {
			in = new double[size];
			out = new double[size];
			//
			this.nbCoups = 0 ;
		}
		this.game = game ;
	}

	public boolean cellAvailable(int position) {
		return in[position] == Coup.EMPTY ;
	}

	public int getNextTurnPiece(int piece) {
		return piece == Coup.X ? Coup.O : Coup.X ;
	}

	public void addInBoard(double[] board) {
		for (int i = 0; i < board.length; i++) {
			in[i] = board[i];
		}
	}

	@Override
	public String toString() {
		return "Coup [game=" +this.game+" in=" + Arrays.toString(in) + ", out=" + Arrays.toString(out) + ", joueurCourant=" + joueurCourant
				+ ", nbCoups=" + nbCoups + ", partieGagne=" + partieGagne + "]";
	}

	//CHAMPS ...
	public String game ; 
	public double[] in ;
	public double[] out ;
	//
	//
	public static int X = -1;
	public static int O = 1 ;
	//
	public static int EMPTY = 0 ;
	//
	public int joueurCourant = 0 ;
	//
	public int nbCoups ;
	//
	public boolean partieGagne = false ;
}

public class Test {

	public static void main(String[] args) {
		try {
			HashMap<Integer, Coup> coups = loadGames("./resources/dataset/Tic_tac_initial_results.csv");
			saveGames(coups, "./resources/train_dev_test/", 0.7);
			test(9); //default : 9
		} 
		catch (Exception e) {
			System.out.println("Test.main()");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void saveGames(HashMap<Integer, Coup> coups, String rep, double trainRate) {
		try {
			System.out.println("saveGames ...");
			PrintWriter pwTrain = new PrintWriter(new File(rep+"/train.txt")); // /train.txt
			PrintWriter pwDev = new PrintWriter(new File(rep+"/dev.txt")); // /dev.txt
			PrintWriter pwTest = new PrintWriter(new File(rep+"/test.txt")); // /test.txt

			int nbTrain = (int)(coups.size() * trainRate) ;
			int nbDev = (int)((coups.size()-nbTrain)/2.0) ;

			for ( int  pos : coups.keySet() ) {
				double[] in = coups.get(pos).in;
				double[] out = coups.get(pos).out;

				String sIn = "" ;
				for (int i = 0; i < in.length; i++) {
					sIn += in[i]+" ";
				}
				//
				String sOut = "" ;
				for (int i = 0; i < out.length; i++) {
					sOut += out[i]+" ";
				}

				if ( pos <= nbTrain ) {
					pwTrain.write(sIn.trim()+"\t"+sOut.trim()+"\n");
				}	
				else if ( pos <= (nbTrain + nbDev) ) {
					pwDev.write(sIn.trim()+"\t"+sOut.trim()+"\n");
				}	
				else
					pwTest.write(sIn.trim()+"\t"+sOut.trim()+"\n");
			}

			pwTrain.close();
			pwDev.close();
			pwTest.close();
		} 
		catch (Exception e) {
			System.out.println("Test.saveGames()");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static HashMap<Integer, double[]> getGameSequence(String x, String o, int size){
		HashMap<Integer, double[]> sequence = new HashMap<>();
		double[] board = new double[size];
		sequence.put(0, board);

		x = x.replace(",win", "").replace(",loss", "");
		o = o.replace(",win", "").replace(",loss", "");

		String[] tabX = x.split(",");
		String[] tabO = o.split(",");

		int len = tabX.length;
		if ( tabO.length > tabX.length )
			len = tabO.length ;

		for (int i = 0; i < len; i ++ ) {

			//			System.out.println("---");
			//			System.out.println("\ti: "+i);
			if ( tabX.length > i ) {
				board = new double[size];
				int c = new Integer(tabX[i]);
				//				System.out.println("c: "+c);
				board[c] = Coup.X ;
				sequence.put(sequence.size(), board);
			}
			//
			if ( tabO.length > i ) {
				board = new double[size];
				int c = new Integer(tabO[i]);
				board[c] = Coup.O ;
				sequence.put(sequence.size(), board);
				//				System.out.println("c: "+c);
			}

		}

		//System.out.println("sequence: "+Arrays.asList(sequence));
		return sequence ;
	}

	public static HashMap<Integer, Coup> loadGames(String fileName) {
		System.out.println("loadGames from "+fileName+ " ...");
		HashMap<Integer, Coup> map = new HashMap<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			String s = "";
			br.readLine();
			while ((s = br.readLine()) != null) {
				if ( ! s.endsWith("draw") ) {
					//
					String playerX = s.replace(",?", "");
					String playerO = br.readLine().replace(",?", "");
					//0,8,1,3,?,?,?,loss
					//4,7,2,6,?,?,?,win
					//
					HashMap<Integer, double[]> sequenceMoves = getGameSequence(playerX, playerO, 9);
					//
					int startEmptyBoard = 0 ; 
					if ( playerO.endsWith("win") ) {
						startEmptyBoard = 1 ;
					}
					boolean in = true ;
					double[] currentBoard = new double[9];
					//
					for (int pos = startEmptyBoard; pos < sequenceMoves.size(); pos ++ ) {
						double[] board = sequenceMoves.get(pos);
						if ( ! in ) {
							Coup c = new Coup(9, playerX+" "+playerO);
							c.in = currentBoard.clone() ;
							c.out = board ;
							map.put(map.size(), c);
						}
						in = !in ;
						for (int i = 0; i < board.length; i++) {
							if ( currentBoard[i] == 0.0 )
								currentBoard[i] = board[i];
						}
					}
				}
			}
			br.close();
		} 
		catch (Exception e) {
			System.out.println("Test.loadGames()");
			e.printStackTrace();
			System.exit(-1);
		}
		return map ;
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

	///////////

	public static void test(int size){
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
			HashMap<Integer, Coup> mapDev = loadCoupsFromFile("./resources/train_dev_test/dev.txt");
			HashMap<Integer, Coup> mapTest = loadCoupsFromFile("./resources/train_dev_test/test.txt");
			System.out.println("---");
			//TRAINING ...
			for(int i = 0; i < epochs; i++){

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
		} 
		catch (Exception e) {
			System.out.println("Test.test()");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void test(){
		try {
			System.out.println();
			System.out.println("START TRAINING ...");
			System.out.println();
			int[] layers = new int[]{ 2, 5, 1 };

			double error = 0.0 ;
			MultiLayerPerceptron net = new MultiLayerPerceptron(layers, 0.1, new SigmoidalTransferFunction());
			double samples = 1000000000 ;

			//TRAINING ...
			for(int i = 0; i < samples; i++){
				double[] inputs = new double[]{Math.round(Math.random()), Math.round(Math.random())};
				double[] output = new double[1];

				if((inputs[0] == 1.0) || (inputs[1] == 1.0))
					output[0] = 1.0;
				else
					output[0] = 0.0;



				error += net.backPropagate(inputs, output);

				if ( i % 100000 == 0 ) System.out.println("Error at step "+i+" is "+ (error/(double)i));
			}
			error /= samples ;
			System.out.println("Error is "+error);
			//
			System.out.println("Learning completed!");

			//TEST ...
			double[] inputs = new double[]{0.0, 1.0};
			double[] output = net.forwardPropagation(inputs);

			System.out.println(inputs[0]+" or "+inputs[1]+" = "+Math.round(output[0])+" ("+output[0]+")");
		} 
		catch (Exception e) {
			System.out.println("Test.test()");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}