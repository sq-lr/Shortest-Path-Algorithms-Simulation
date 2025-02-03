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
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.Pane;

/**
 * Write a description of JavaFX class Graph here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Graph
{
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private HashMap<Node, ArrayList<Node> > adjList;
    private HashMap<String, Node> numToNode;
    
    public Graph()
    {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        adjList = new HashMap<Node, ArrayList<Node> >();
        numToNode = new HashMap<String, Node>();
    }
    public void addNode(Node n)
    {
        nodes.add(n);
        numToNode.put(n.getCirc().getText(), n);
        adjList.put(n, new ArrayList<Node>());
    }

    public Edge addEdge(String n1, String n2, String w)
    {
        Node node1 = numToNode.get(n1);
        Node node2 = numToNode.get(n2);
        int len = Integer.parseInt(w);
        Edge newEdge = new Edge(node1, node2, len);
        
        /*if (edges.contains(newEdge))
        {
            System.out.println("duplicate edge");
            return null;
        }*/
        
        adjList.get(node1).add(node2);
        edges.add(newEdge);
        return newEdge;
    }
    public void reset()
    {
        nodes.clear();
        edges.clear();
        adjList.clear();
        numToNode.clear();
        Node.resetCounter();
    }
}