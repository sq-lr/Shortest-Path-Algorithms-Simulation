package spasimulations;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.*;


/**
 * Creates a circular draggable Node, with accessor & mutator methods
 *
 * @author Manya Bachheti
 * @author Luoxi Wu
 * @author Bridget Liang
 * @version 2025-02-12
 */
public class Node
{
    private Button circ; //circular node (button)
    private double dist; //distance value of node
    private final int inf = 10000; // arbitrary large value to represent infinity
    private static int counter = 1; // static, keeps track of number of nodes + which node to add next (# nodes = counter-1)
    private Label distLabel; // labels node with the distance value
    
    /**
     * Constructs a Node.
     */
    public Node() {
        if(counter == 1) // first node's distance value set to zero (distance from n1 to n1 = 0)
            dist = 0;
        else
            dist = inf;
        
        // create circular button node with number label
        circ = new Button("" + counter++);
        circ.setShape(new Circle(100));
        circ.setStyle(
            "-fx-background-radius: 30em; " +
            "-fx-min-width: 30px; " +
            "-fx-min-height: 30px; " +
            "-fx-max-width: 50px; " +
            "-fx-max-height: 50px; " +
            "-fx-background-color: -fx-body-color;"
        );
        circ.setLayoutX(80+counter*10); // new nodes are added in a line
        circ.setLayoutY(20);
        
        // allows user to drag node
        circ.setOnMouseDragged(e -> {
            circ.setLayoutX(e.getSceneX()-10);
            circ.setLayoutY(e.getSceneY()-10);
        });
        // user cannot recover node if node is dragged out of bounds (negative xy);
        // moves it back to nonnegative bounds
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
    
    /**
     * returns the total number of nodes
     * 
     * @return counter-1
     */
    public static int numNodes()
    {  
        return counter-1;
    }
    
    /**
     * accessor method for the node
     * 
     * @return the node
     */
    public Button getCirc()
    {
        return circ;
    }
    
    /**
     * returns x position of button (+15 for radius)
     * 
     * @return x position
     */
    public double getX()
    {
        return circ.getLayoutX() + 15; //x position of the center
    }
    
    /**
     * returns y position of button (+15 for radius)
     * 
     * @return y position
     */
    public double getY()
    {
        return circ.getLayoutY() + 15; //y position of the center
    }
    
    /**
     * resets the counter to one (used when resetting/clearing graph)
     */
    public static void resetCounter()
    {
        counter = 1;
    }
    
    /**
     * highlights node in green
     */
    public void greenHighlight()
    {
        circ.setStyle(circ.getStyle() + "-fx-background-color: lightgreen;");
    }
    
    /**
     * highlights node in yellow
     */
    public void yellowHighlight()
    {
        circ.setStyle(circ.getStyle() + "-fx-background-color: lightgoldenrodyellow;");
    }

    /**
     * resets node color
     */
    public void noHighlight()
    {
        circ.setStyle(circ.getStyle() + "-fx-background-color: -fx-body-color;");
    }

    /**
     * returns the number of this node
     * @return this node's number
     */
    public int num()
    {
        return Integer.parseInt(circ.getText());
    }
    
    /**
     * returns distance value label
     * @return distLabel
     */
    public Label getLabel()
    {
        return distLabel;
    }

    /**
     * returns distance value as double
     * @return dist
     */
    public Double getDist()
    {
        return dist;
    }
    
    /**
     * sets the distance label with distance value
     */
    public void setDistLabel(Double newDist)
    {
        distLabel.setText(String.valueOf(newDist));
    }

    /**
     * changes distance value
     */
    public void setDist(Double newDist)
    {
        dist = newDist;
    }

    /**
     * resets distance (used when resetting/clearing graph)
     */
    public void resetDist()
    {
        if (num() == 1)
        {
            dist = 0;
        }
        else
        {
            dist = inf;
        }
        setDistLabel(dist);
    }
}
