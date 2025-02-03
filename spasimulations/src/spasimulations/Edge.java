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
import javafx.scene.text.Text;  

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
    private int len;
    private Text label;
    
    public Edge(Node n1, Node n2, int len) {
        this.n1 = n1;
        this.n2 = n2;
        this.len = len; // LEN ADDED
        label.setText(len);
        line = new Line();
        line.setStartX(n1.getX());
        line.setStartY(n1.getY());
        line.setEndX(n2.getX() );
        line.setEndY(n2.getY() );
        
        //find some way to append instructions insteead of override/reset!!
        Button circ1 = n1.getCirc();
        Button circ2 = n2.getCirc();
        
        line.startXProperty().bind(circ1.layoutXProperty().add(circ1.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind( circ1.layoutYProperty().add(circ1.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind( circ2.layoutXProperty().add( circ2.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind( circ2.layoutYProperty().add( circ2.getBoundsInParent().getHeight() / 2.0));
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
