package spasimulations;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.control.Label;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;


/**
 * Write a description of JavaFX class Node here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Node
{
    //ArrayList<Edge> edges = new ArrayList<Edge>();
    private Button circ;
    private double dist;
    private final int inf = 10000;
    private static int counter = 1;
    private Label distLabel;
    
    public Node() {
        if(counter == 1)
            dist = 0;
        else
            dist = inf;

        circ = new Button("" + counter++);
        circ.setShape(new Circle(100));
        circ.setStyle(
            "-fx-background-radius: 30em; " +
            "-fx-min-width: 30px; " +
            "-fx-min-height: 30px; " +
            "-fx-max-width: 50px; " +
            "-fx-max-height: 50px; " +
            "-fx-background-color: -fx-body-color;"
            //"-fx-background-insets: 0px; "
            //"-fx-padding: 0px;"
        );
        circ.setLayoutX(80+counter*10);
        circ.setLayoutY(0);
        
        circ.setOnMouseDragged(e -> {
            circ.setLayoutX(e.getSceneX()-10);
            circ.setLayoutY(e.getSceneY()-10);
        });
        circ.setOnMouseReleased(e-> {
            if (circ.getLayoutX() < 0) {
                circ.setLayoutX(0);
            }
            if (circ.getLayoutY() < 0) {
                circ.setLayoutY(0);
            }
        });

         distLabel = new Label(String.valueOf(dist));
         distLabel.setStyle("-fx-background-color: aqua");
         distLabel.layoutXProperty().bind(circ.layoutXProperty());
         distLabel.layoutYProperty().bind(circ.layoutYProperty().add(-20)); 
    }
    
    public static int numNodes()
    {  
        return counter-1;
    }

    public Button getCirc()
    {
        return circ;
    }
    
    public double getX()
    {
        return circ.getLayoutX() + 15; //x position of the center
    }
    
    public double getY()
    {
        return circ.getLayoutY() + 15; //y position of the center
    }
    
    public static void resetCounter()
    {
        counter = 1;
    }

    public void greenHighlight()
    {
        circ.setStyle(circ.getStyle() + "-fx-background-color: lightgreen;");
    }

    public void redHighlight()
    {
        circ.setStyle(circ.getStyle() + "-fx-background-color: red;");
    }

    public void noHighlight()
    {
        circ.setStyle(circ.getStyle() + "-fx-background-color: -fx-body-color;");
    }

    public int num()
    {
        return Integer.parseInt(circ.getText());
    }

    public Label getLabel()
    {
        return distLabel;
    }

    public Double getDist()
    {
        return dist;
    }

    public void setDistLabel(Double newDist)
    {
        distLabel.setText(String.valueOf(dist));
    }

    public void setDist(Double newDist)
    {
        dist = newDist;
    }
}

