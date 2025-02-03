package spasimulations;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.shape.*;


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
    private static int counter = 1;
    
    public Node() {
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
        circ.setStyle(circ.getStyle() + "fx-background-color: green;");
    }

    public void redHighlight()
    {
        circ.setStyle(circ.getStyle() + "-fx-background-color: red;");
    }

    public void noHighlight()
    {
        circ.setStyle(circ.getStyle() + "-fx-background-color: -fx-body-color;");
    }
}