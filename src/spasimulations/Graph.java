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
import javafx.util.Pair;
import javafx.scene.layout.Pane;
import java.util.*;

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
        adjList.add(null);
        pairToEdge = new HashMap<Pair<Integer, Integer>, Edge>();
        numToNode = new Node[maxNodes]; 
    }

    public void addNode(Node n)
    {
        nodes.add(n);
        numToNode[n.num()] = n;
        adjList.add(new ArrayList<Integer>());
    }

    public Edge addEdge(String n1, String n2, String len)
    {
        int num1 = Integer.parseInt(n1);
        int num2 = Integer.parseInt(n2);
        
        if (num1 > nodes.size() || num2 > nodes.size())
        {
            return null;
        }
        
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
    
    public ArrayList<Node> getNodes()
    {
        return nodes;
    }
    
    public ArrayList<Edge> getEdges()
    {
        return edges;
    }
    
    public void resetDists()
    {
        for (Node n : nodes)
        {
            n.resetDist();
        }
    }

    public void reset()
    {
        nodes.clear();
        edges.clear();
        adjList = new ArrayList<ArrayList<Integer>>(maxNodes);
        adjList.add(null);
        pairToEdge.clear();
        numToNode = new Node[maxNodes];
        Node.resetCounter();
    }
    
    public double dijkstra(String dest, Label inProgress)
    {
        Timeline timeline = new Timeline();
        double delay = 0;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> inProgress.setVisible(true)));
        
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
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[curNode].greenHighlight()));
            delay += 500;
            vis[curNode] = true;
            ArrayList<Integer> children = adjList.get(curNode);
            for(int c: children)
            {
                Edge e = pairToEdge.get(new Pair<Integer, Integer>(curNode, c));
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> e.highlight()));
                delay += 500;
                double newDis = curDis + e.getLen();
                if(!vis[c] && newDis < numToNode[c].getDist())
                {
                    /*System.out.println(c);
                    System.out.println(numToNode[c].getDist());
                    System.out.println(newDis); */
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[c].yellowHighlight()));
                    delay += 500;
                    numToNode[c].setDist(newDis);
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[c].setDistLabel(newDis)));
                    delay += 500;
                    pq.add(new Pair<Double, Integer>(newDis, c));
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[c].noHighlight()));
                    delay += 500;
                }
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> e.noHighlight()));
                delay += 500;
            }
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[curNode].noHighlight()));
            delay += 500;
        }
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> inProgress.setVisible(false)));
        timeline.play();
        return numToNode[Integer.parseInt(dest)].getDist();
    }
    
    public double bellmanFord(String dest, Label inProgress) {
        int destNum = Integer.parseInt(dest);
        Timeline timeline = new Timeline();
        double delay = 0;
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
                    //delay += 500;

                    end.setDist(finalDist);
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> end.noHighlight()));
                    delay += 500;
                }

                if (end.getDist() + weight < start.getDist()) {
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> start.greenHighlight()));
                    delay += 500;
    
                    double finalDist = (double) (end.getDist() + weight);
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), Double.toString(finalDist), event -> start.setDistLabel(finalDist)));
                    //delay += 500;

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

    public double floydWarshall(String dest, Label inProgress)
    {
        int destNum = Integer.parseInt(dest);
        int n = nodes.size();
        double arr[][] = new double[n+1][n+1];
        double inf = 10000;
        Timeline timeline = new Timeline();
        double delay=0;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> inProgress.setVisible(true)));
        for(int i = 1; i <= n; i++)
        {
            for(int j = 1; j <= n; j++)
            {
                if(i == j)
                    arr[i][j] = 0;
                else
                    arr[i][j] = inf;
            }
        }
        for(Edge e: edges)
        {
            int start = e.getStart().num();
            int end = e.getEnd().num();
            arr[start][end] = e.getLen();
            arr[end][start] = e.getLen();
            if(start == 1)
            {
                numToNode[end].setDistLabel(e.getLen());
            }
            if(end == 1)
            {
                numToNode[start].setDistLabel(e.getLen());
            }
        }
        
        for(int i = 1; i <= n; i++)
        {
            final int fi = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[fi].greenHighlight()));
            delay += 500;
            for(int j = 1; j <= n; j++)
            {
                if(j == i)
                    continue;
                final int fj = j;
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[fj].yellowHighlight()));
                delay += 500;
                for(int k = 1; k <= n; k++)
                {
                    if(k == i || k == j)
                        continue;
                    final int fk = k;
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[fk].greenHighlight()));
                    delay += 500;
                    double newDis = arr[i][j] + arr[j][k];
                    if(newDis < arr[i][k])
                    {
                        arr[i][k] = newDis;
                        arr[k][i] = newDis;
                        if(i == 1)
                        {
                            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[fk].setDistLabel(newDis)));
                            delay += 500;
                        }
                        if(k == 1)
                        {
                            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[fi].setDistLabel(newDis)));
                            delay += 500;
                        }
                    }
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[fk].noHighlight()));
                    delay += 500;
                }
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[fj].noHighlight()));
                delay += 500;
            }
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[fi].noHighlight()));
            delay += 500;
        }
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> inProgress.setVisible(false)));
        timeline.play();
        return arr[1][destNum];
    }

    public int dfs(int node, double dist, int delay, boolean[] vis, Timeline timeline, Label inProgress)
    {
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> inProgress.setVisible(true)));
        if(dist > numToNode[node].getDist())
        {
            return delay;
        }
        vis[node] = true;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[node].greenHighlight()));
        delay += 500;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[node].setDistLabel(dist)));
        delay += 500;
        numToNode[node].setDist(dist);
        ArrayList<Integer> children = adjList.get(node);
        for(int c: children)
        {
            if(!vis[c])
            {
                Edge e = pairToEdge.get(new Pair<Integer, Integer>(node, c));
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> e.highlight()));
                delay += 500;
                double newDist = dist + e.getLen();
                delay = dfs(c, newDist, delay + 500, vis, timeline, inProgress);
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> e.noHighlight()));
                delay += 500;
            }
        }
        System.out.println(node);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), event -> numToNode[node].noHighlight()));
        delay += 500;
        vis[node] = false;
        return delay;
    }

    public int getSize()
    {
        return nodes.size();
    }
}
