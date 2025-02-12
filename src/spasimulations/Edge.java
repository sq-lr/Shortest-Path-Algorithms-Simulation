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
 * Creates an Edge (line) connecting Nodes
 *
 * @author Manya Bachheti
 * @author Luoxi Wu
 * @author Bridget Liang
 * @version 2025-02-12
 */
public class Edge 
{
    private Line line;
    private Node n1;
    private Node n2;
    private double len;
    private Label weight;
    
    /**
     * Edge constructor
     * 
     * @param n1 node 1
     * @param n2 node 2
     * @param len length/weight of edge
     */
    public Edge(Node n1, Node n2, double len) {
        this.n1 = n1;
        this.n2 = n2;
        this.len = len; // LEN ADDED
        weight = new Label(""+len);
        line = new Line();
        line.setStartX(n1.getX()); // sets line's start position at n1
        line.setStartY(n1.getY());
        line.setEndX(n2.getX()); // sets line's end position at n2
        line.setEndY(n2.getY());
        weight.setLayoutX((n1.getX()+n2.getX())/2.0); // places edge weight label at center of line
        weight.setLayoutY((n1.getY()+n2.getY())/2.0);
        weight.setStyle("-fx-background-color: yellow"); // highlight for visibility
        
        //find some way to append instructions insteead of override/reset!!
        Button circ1 = n1.getCirc();
        Button circ2 = n2.getCirc();
        
        //binds edge start & end to nodes, allowing line to move when nodes are dragged
        line.startXProperty().bind(circ1.layoutXProperty().add(circ1.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind( circ1.layoutYProperty().add(circ1.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind( circ2.layoutXProperty().add( circ2.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind( circ2.layoutYProperty().add( circ2.getBoundsInParent().getHeight() / 2.0));
        //binds weight label to center of edge
        weight.layoutXProperty().bind(line.startXProperty().add(line.endXProperty()).multiply(0.5));
        weight.layoutYProperty().bind(line.startYProperty().add(line.endYProperty()).multiply(0.5));
    }
    
    /**
     * return start node
     * @return node 1
     */
    public Node getStart()
    {
        return n1;
    }
    /**
     * return end node
     * @return node 2
     */
    public Node getEnd()
    {
        return n2;
    }
    /**
     * returns edge line
     * @return line
     */
    public Line getLine()
    {
        return line;
    }
    /**
     * returns weight label
     * @return weight
     */
    public Label getLabel(){
        return weight;
    }
    /**
     * equals method, checks if beginning & end nodes are the same for both edges
     * @param edge edge to be compared with this edge
     */
    public boolean equals(Edge edge)
    {
        return ( (n1 == edge.getStart() && n2 == edge.getEnd()) || (n1 == edge.getEnd() && n2 == edge.getStart()) );
    }
    
    /**
     * colors line red (for animation)
     */
    public void highlight()
    {
        line.setStyle(line.getStyle() + "-fx-stroke: red;");
    }

    /**
     * resets line color to black
     */
    public void noHighlight()
    {
        line.setStyle(line.getStyle() + "-fx-stroke: black;");
    }
    
    /**
     * returns the length/weight of the line
     */
    public double getLen()
    {
        return len;
    }
}

