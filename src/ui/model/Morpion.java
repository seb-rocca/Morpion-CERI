package ui.model;

import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Morpion {

    private MultiLayerPerceptron net = new MultiLayerPerceptron(new int[]{ 9, 128, 9}, 0.1, new SigmoidalTransferFunction());

    private GridPane gridp;
    private ArrayList<Integer> cases = new ArrayList<Integer>();

    public Morpion(GridPane gridp, String file, boolean with_AI) {

        if(with_AI)
        {
            System.out.println("Chargement du fichier..." + file);
            net.load(file);
        }

        for(int i = 0; i < 9; i++)
        {
            cases.add(0);
        }

        this.gridp = gridp;
    }

    public Boolean verify_diagonal(int current_player)
    {
        if(cases.get(0) == current_player && cases.get(4) == current_player && cases.get(8) == current_player)
        {
            paint(0, 4, 8);
            return true;
        }
        else if(cases.get(2) == current_player && cases.get(4) == current_player && cases.get(6) == current_player)
        {
            paint(2, 4, 6);
            return true;
        }

        return false;
    }

    public Boolean verify_column(int case_id, int current_player)
    {
        if(case_id/3 == 0 && cases.get(case_id+3) == current_player && cases.get(case_id+6) == current_player )
        {
            paint(case_id, case_id+3, case_id+6);
            return true;
        }

        else if(case_id/3 == 1 && cases.get(case_id-3) == current_player && cases.get(case_id+3) == current_player)
        {
            paint(case_id-3, case_id, case_id+3);
            return true;
        }

        else if(case_id/3 == 2 && cases.get(case_id-3) == current_player && cases.get(case_id-6) == current_player)
        {
            paint( case_id-6,case_id-3, case_id);
            return true;
        }

        return false;
    }

    public Boolean verify_line(int case_id, int current_player)
    {
        if(case_id%3 == 0 && cases.get(case_id+1) == current_player && cases.get(case_id+2) == current_player)
        {
            paint(case_id, case_id+1, case_id+2);
            return true;
        }

        else if(case_id%3 == 1 && cases.get(case_id+1) == current_player && cases.get(case_id-1) == current_player)
        {
            paint(case_id-1, case_id, case_id+1);
            return true;
        }

        else if(case_id%3 == 2 && cases.get(case_id-1) == current_player && cases.get(case_id-2) == current_player)
        {
            paint(case_id-2, case_id-1, case_id);
            return true;
        }

        return false;

    }

    public int IAturn()
    {
        double[] casesIA = new double[9];
        int cmpt = 0;
        for(int i : cases)
        {
            casesIA[cmpt] = i;
            cmpt++;
        }
        double[] probabilite = net.forwardPropagation(casesIA);
        double max = 0;
        int pion = 0;
        int cmpt2 = 0;
        for(double a : probabilite)
        {
            if(a > max && getCasesValue(cmpt2) == 0)
            {
                max = a;
                pion = cmpt2;
            }
            cmpt2++;

        }

        return pion;
    }

    public Boolean verify(int case_id, int current_player)
    {
        if( verify_diagonal(current_player) || verify_column(case_id, current_player) || verify_line(case_id, current_player) )
        {
            //victory(current_player);
            return true;
        }
        return false;

    }

    public int getCasesValue(int case_id)
    {
        return cases.get(case_id);
    }

    public void setCasesValue(int case_id, int v)
    {
        cases.set(case_id, v);
    }

    public void paint(int c1, int c2, int c3) {
        BorderPane p1 = (BorderPane) gridp.lookup("#" + String.valueOf(c1));
        BorderPane p2 = (BorderPane) gridp.lookup("#" + String.valueOf(c2));
        BorderPane p3 = (BorderPane) gridp.lookup("#" + String.valueOf(c3));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(p1.styleProperty(), "-fx-background-color: rgba(0, 230, 64, 0)")),
                new KeyFrame(Duration.seconds(1), new KeyValue(p1.styleProperty(), "-fx-background-color:rgba(0, 230, 64, 1)"))


        );

        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(p2.styleProperty(), "-fx-background-color: rgba(0, 230, 64, 0)")),
                new KeyFrame(Duration.seconds(1), new KeyValue(p2.styleProperty(), "-fx-background-color:rgba(0, 230, 64, 1)"))
        );

        Timeline timeline2 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(p3.styleProperty(), "-fx-background-color: rgba(0, 230, 64, 0)")),
                new KeyFrame(Duration.seconds(1), new KeyValue(p3.styleProperty(), "-fx-background-color:rgba(0, 230, 64, 1)"))
        );

        SequentialTransition seqT = new SequentialTransition();
        seqT.getChildren().addAll(timeline, timeline1, timeline2);

        seqT.play();
    }

    public ArrayList<Integer> getCases()
    {
        return cases;
    }



}
