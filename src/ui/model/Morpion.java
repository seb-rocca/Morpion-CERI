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

    private GridPane gridp; //on fournit la gridpane ou se situent toutes les cases pouvant être jouées
    private ArrayList<Integer> cases = new ArrayList<Integer>(); //on y défini les valeurs des cases (0: pas jouée, -1: joueur X, 1: joueur O)

    public Morpion(GridPane gridp, String file, boolean with_AI) {

        if(with_AI) //si l'IA est activée:
        {
            System.out.println("Chargement du fichier..." + file);
            net.load(file);//on charge le fichier associé
        }

        for(int i = 0; i < 9; i++)
        {
            cases.add(0); //on défini toutes les cases comme n'ayant pas été jouées
        }

        this.gridp = gridp; //on défini la gridpane contenant les cases pouvant être jouées
    }

    public Boolean verify_diagonal(int current_player) //on vérifie si il y a une diagonale gagnate
    {
        if(cases.get(0) == current_player && cases.get(4) == current_player && cases.get(8) == current_player) //diagonale d'en haut a gauche a en bas adroite
        {
            paint(0, 4, 8); //on colorie les cases portant les id 0, 4 et 8
            return true; //on retourne que la diagonale est gagnate
        }
        else if(cases.get(2) == current_player && cases.get(4) == current_player && cases.get(6) == current_player) //diagonale d'en haut a droite a en bas a gauche
        {
            paint(2, 4, 6);
            return true;
        }

        return false;
    }

    public Boolean verify_column(int case_id, int current_player) //on vérifie si il y a une colonne gagnate
    {
        if(case_id/3 == 0 && cases.get(case_id+3) == current_player && cases.get(case_id+6) == current_player ) //premiere colonne
         {
            paint(case_id, case_id+3, case_id+6); //on colorie la colonne
            return true; //on retourne que la colonne est gagnate
        }

        else if(case_id/3 == 1 && cases.get(case_id-3) == current_player && cases.get(case_id+3) == current_player) //deuxième colonne
        {
            paint(case_id-3, case_id, case_id+3);
            return true;
        }

        else if(case_id/3 == 2 && cases.get(case_id-3) == current_player && cases.get(case_id-6) == current_player) //troisième
        {
            paint( case_id-6,case_id-3, case_id);
            return true;
        }

        return false;
    }

    public Boolean verify_line(int case_id, int current_player) //on vérifie si il y a une ligne gagnante
    {
        if(case_id%3 == 0 && cases.get(case_id+1) == current_player && cases.get(case_id+2) == current_player) //on vérifie la première ligne
        {
            paint(case_id, case_id+1, case_id+2); //on colorie la ligne
            return true; //on retourne que la ligne est gagnate
        }

        else if(case_id%3 == 1 && cases.get(case_id+1) == current_player && cases.get(case_id-1) == current_player) //la deuxième
        {
            paint(case_id-1, case_id, case_id+1);
            return true;
        }

        else if(case_id%3 == 2 && cases.get(case_id-1) == current_player && cases.get(case_id-2) == current_player) //la troisième
        {
            paint(case_id-2, case_id-1, case_id);
            return true;
        }

        return false;

    }

    public int IAturn() //lorsqu'on fait jouer l'ia
    {
        double[] casesIA = new double[9];
        int cmpt = 0;
        for(int i : cases) //on convertit vers un tableau simple de type double
        {
            casesIA[cmpt] = i;
            cmpt++;
        }
        double[] probabilite = net.forwardPropagation(casesIA); //le tableau des probabiltités de chaque case (la case libre ayant plus haute sera la case jouée)
        double max = 0;
        int pion = 0;
        int cmpt2 = 0;
        for(double a : probabilite) //on recherche la case libre ayant la probabilité la plus haute en cherchant le maximum
        {
            if(a > max && getCasesValue(cmpt2) == 0) //si il y a une probabilité plus haute que max
            {
                max = a;
                pion = cmpt2;
            }
            cmpt2++;

        }

        return pion; //on retourne l'id de la case qui doit être jouée.
    }

    public Boolean verify(int case_id, int current_player) //on vérifie si le joueur est gagnant
    {
        if( verify_diagonal(current_player) || verify_column(case_id, current_player) || verify_line(case_id, current_player) )
        {
            //victory(current_player);
            return true;
        }
        return false;

    }

    public int getCasesValue(int case_id) //pour récuperer la valeur d'une case
    {
        return cases.get(case_id);
    }

    public void setCasesValue(int case_id, int v) //pour assigner la valeur d'une case
    {
        cases.set(case_id, v);
    }

    public void paint(int c1, int c2, int c3) { //pour peindre les cases
        BorderPane p1 = (BorderPane) gridp.lookup("#" + String.valueOf(c1));
        BorderPane p2 = (BorderPane) gridp.lookup("#" + String.valueOf(c2));
        BorderPane p3 = (BorderPane) gridp.lookup("#" + String.valueOf(c3));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(p1.styleProperty(), "-fx-background-color: rgba(0, 230, 64, 0)")), //on peint les cases en vert
                new KeyFrame(Duration.seconds(1), new KeyValue(p1.styleProperty(), "-fx-background-color:rgba(0, 230, 64, 1)")) //transition d'une durée de 1 seconde


        );

        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(p2.styleProperty(), "-fx-background-color: rgba(0, 230, 64, 0)")),
                new KeyFrame(Duration.seconds(1), new KeyValue(p2.styleProperty(), "-fx-background-color:rgba(0, 230, 64, 1)"))
        );

        Timeline timeline2 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(p3.styleProperty(), "-fx-background-color: rgba(0, 230, 64, 0)")),
                new KeyFrame(Duration.seconds(1), new KeyValue(p3.styleProperty(), "-fx-background-color:rgba(0, 230, 64, 1)"))
        );

        SequentialTransition seqT = new SequentialTransition(); //on définit comme étant une transition séquentielle
        seqT.getChildren().addAll(timeline, timeline1, timeline2); //et on y ajoute l'animation de chaque case

        seqT.play(); //et on joue l'animation
    }

}
