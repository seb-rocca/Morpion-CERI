package ui.view;

import com.sun.javafx.geom.PathIterator;
import com.sun.javafx.geom.RectBounds;
import com.sun.javafx.geom.Shape;
import com.sun.javafx.geom.transform.BaseTransform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Cross {

    private Pane p = new Pane();

    public Cross()
    {
            p.setPrefSize(200, 200);

            Line l = new Line();
            l.setStartX(200);
            l.setEndX(0);
            l.setStartY(200);
            l.setEndY(0);

            Line l2 = new Line();
            l2.setStartX(0);
            l2.setEndX(200);
            l.setStartY(0);
            l.setEndY(200);

            p.getChildren().add(l);
            p.getChildren().add(l2);
    }

    public Pane getPane()
    {
        return p;
    }


}
