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
import java.lang.Math;

/**
 * Write a description of JavaFX class Edge here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Edge 
{
    private Line line;
    private Node n1;
    private Node n2;
    private double len;
    private Label weight;
    
    public Edge(Node n1, Node n2, double len) {
        this.n1 = n1;
        this.n2 = n2;
        this.len = len; // LEN ADDED
        weight = new Label(""+len);
        line = new Line();
        line.setStartX(n1.getX());
        line.setStartY(n1.getY());
        line.setEndX(n2.getX());
        line.setEndY(n2.getY());
        weight.setLayoutX((n1.getX()+n2.getX())/2.0);
        weight.setLayoutY((n1.getY()+n2.getY())/2.0);
        weight.setStyle("-fx-background-color: yellow");
        //weight.setText(""+len);
        
        //find some way to append instructions insteead of override/reset!!
        Button circ1 = n1.getCirc();
        Button circ2 = n2.getCirc();
        
        line.startXProperty().bind(circ1.layoutXProperty().add(circ1.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind( circ1.layoutYProperty().add(circ1.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind( circ2.layoutXProperty().add( circ2.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind( circ2.layoutYProperty().add( circ2.getBoundsInParent().getHeight() / 2.0));
        
        //weight.layoutXProperty().bind(line.startXProperty().add(Math.abs(line.startXProperty().get()-line.endXProperty().get()) / 2.0));
        weight.layoutXProperty().bind(line.startXProperty().add(line.endXProperty()).multiply(0.5));
        weight.layoutYProperty().bind(line.startYProperty().add(line.endYProperty()).multiply(0.5));
        //weight.layoutYProperty().bind(line.startYProperty().add(Math.abs(line.startYProperty().get()-line.endYProperty().get()) / 2.0));
        //+line.endXProperty())/2.0
    }
    
    public Node getStart()
    {
        return n1;
    }
    
    public Node getEnd()
    {
        return n2;
    }
    
    public Line getLine()
    {
        return line;
    }

    public Label getLabel(){
        return weight;
    }
    
    public boolean equals(Edge edge)
    {
        return ( (n1 == edge.getStart() && n2 == edge.getEnd()) || (n1 == edge.getEnd() && n2 == edge.getStart()) );
    }

    public void highlight()
    {
        line.setStyle(line.getStyle() + "-fx-stroke: red;");
    }

    public void noHighlight()
    {
        line.setStyle(line.getStyle() + "-fx-stroke: black;");
    }

}
