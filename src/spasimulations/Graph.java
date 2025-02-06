package spasimulations; 

import java.lang.classfile.components.ClassPrinter;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.layout.Pane;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


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
    private ArrayList<ArrayList<Integer> > adjList;
    private HashMap<Pair<Integer, Integer>, Edge> pairToEdge;
    private Node[] numToNode;
    private final int maxNodes = 100000;
    
    public Graph()
    {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        adjList = new ArrayList<ArrayList<Integer> >();
        for(int i = 0; i < maxNodes; i++)  {
            adjList.add(new ArrayList<Integer>());
        }
        pairToEdge = new HashMap<Pair<Integer, Integer>, Edge>();
        numToNode = new Node[maxNodes]; 
    }

    public void addNode(Node n)
    {
        nodes.add(n);
        numToNode[n.num()] = n;
    }

    public Edge addEdge(String n1, String n2, String len)
    {
        int num1 = Integer.parseInt(n1);
        int num2 = Integer.parseInt(n2);
        Node node1 = numToNode[num1];
        Node node2 = numToNode[num2];
        Edge newEdge = new Edge(node1, node2, Double.valueOf(len));
        
        for (Edge edge : edges)
        {
            if (edge.equals(newEdge))
            {
                return null;
            }
        }
        adjList.get(num1).add(num2);
        adjList.get(num2).add(num1);
        edges.add(newEdge);
        pairToEdge.put(new Pair<Integer, Integer>(num1, num2), newEdge);
        pairToEdge.put(new Pair<Integer, Integer>(num2, num1), newEdge);
        return newEdge;
    }

    public void reset()
    {
        nodes.clear();
        edges.clear();
        adjList = new ArrayList<ArrayList<Integer> >(maxNodes);
        pairToEdge.clear();
        numToNode = new Node[maxNodes];
        Node.resetCounter();
    }
    
    
    public double dijkstra(String dest)
    {
        PriorityQueue<Pair<Double, Integer> > pq = new PriorityQueue<Pair<Double, Integer> >(Comparator.comparing(Pair::getKey)); 
        pq.add(new Pair<Double, Integer>(0.0, 1)); 
        boolean[] vis = new boolean[nodes.size()+1];
        while(!pq.isEmpty())
        {
            double curDis = pq.peek().getKey();
            int curNode = pq.peek().getValue();
            pq.poll();
            if(vis[curNode])
                continue;
            vis[curNode] = true;
            numToNode[curNode].setDist(curDis);
            ArrayList<Integer> children = adjList.get(curNode);
            for(int c: children)
            {
                if(!vis[c])
                {
                    double newDis = curDis + pairToEdge.get(new Pair<Integer, Integer>(curNode, c)).getLen();
                    pq.add(new Pair<Double, Integer>(newDis, c) );
                }
            }
        }
        return numToNode[Integer.parseInt(dest)].getDist();
    }
    
    public double bellmanFord(String dest, Label inProgress) {
        int destNum = Integer.parseInt(dest);
        Timeline timeline = new Timeline();
        double delay = 0; // Start time delay
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> inProgress.setVisible(true)));
    
        for (int i = 0; i < Node.numNodes() - 1; i++) {
            for (Edge e : edges) {
                Node start = e.getStart();
                Node end = e.getEnd();
                double weight = e.getLen();
    
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> e.highlight()));
                delay += 500; 
                if (start.getDist() + weight < end.getDist()) {
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> end.greenHighlight()));
                    delay += 500;
    
                    double finalDist = (double) (start.getDist() + weight);
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), Double.toString(finalDist), event -> end.setDistLabel(finalDist)));
                    delay += 500;

                    end.setDist(finalDist);
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> end.noHighlight()));
                    delay += 500;
                }

                if (end.getDist() + weight < start.getDist()) {
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> start.greenHighlight()));
                    delay += 500;
    
                    double finalDist = (double) (end.getDist() + weight);
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), Double.toString(finalDist), event -> start.setDistLabel(finalDist)));
                    delay += 500;

                    start.setDist(finalDist);
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> start.noHighlight()));

                    delay += 500;
                }
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> e.noHighlight()));
                delay += 500;
            }
        }
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> inProgress.setVisible(false)));
        timeline.play();
        return numToNode[destNum].getDist();
    }
}
