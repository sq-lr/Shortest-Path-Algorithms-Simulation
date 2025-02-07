package spasimulations;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("spasimulations.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 400, 300));
        
        Graph graph = new Graph();
        Pane pane = new Pane();
        
        Button addNode = new Button("Add Node");
        addNode.setLayoutY(50);
        addNode.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
           Node n = new Node();
           graph.addNode(n);
           pane.getChildren().add(n.getCirc());
           pane.getChildren().add(n.getLabel());
        }
        });

        Button addEdge = new Button("Add Edge");
        addEdge.setLayoutY(180);

        TextField tnode1 = new TextField();
        tnode1.setPromptText("node 1");
        tnode1.setLayoutY(90);
        //tnode1.setLayoutX(70);
        tnode1.setPrefWidth(50);
        TextField tnode2 = new TextField();
        tnode2.setPromptText("node 2");
        tnode2.setLayoutY(120);
        //tnode2.setLayoutX(130);
        tnode2.setPrefWidth(50);
        // MY CODE
        TextField weight = new TextField();
        weight.setPromptText("weight");
        weight.setLayoutY(150);
        //weight.setLayoutX(70);
        weight.setPrefWidth(50);
        Label edgeError = new Label("Invalid/existing edge.");
        edgeError.setStyle("-fx-text-fill: red");
        edgeError.setVisible(false);
        edgeError.setLayoutY(210);
        
        addEdge.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if ((!tnode1.getText().isEmpty() && !tnode1.getText().isEmpty()) 
                    && !weight.getText().isEmpty()) {
                    Edge edge = graph.addEdge(tnode1.getText(), tnode2.getText(), weight.getText());
                    if (edge == null)
                    {
                        edgeError.setVisible(true);
                    }
                    else
                    {
                        edgeError.setVisible(false);
                        pane.getChildren().add(0, edge.getLine());
                        pane.getChildren().add(edge.getLabel());
                    }
                }
            }
        });
        
        Label inProgress = new Label("Simulation running!");
        inProgress.setLayoutY(390);
        inProgress.setStyle("-fx-text-fill: red");
        inProgress.setVisible(false);

        TextField destination = new TextField();
        destination.setPromptText("Destination");
        destination.setLayoutY(240);
        //tnode1.setLayoutX(70);
        destination.setPrefWidth(50);
        
        Button bellmanFord = new Button("Bellman-Ford");
        bellmanFord.setLayoutY(270);
        bellmanFord.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(!destination.getText().isEmpty())
                    graph.bellmanFord(destination.getText(), inProgress);
            }
        });
        
        Button dijkstra = new Button("Dijkstra");
        dijkstra.setLayoutY(300);
        dijkstra.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(!destination.getText().isEmpty())
                    graph.dijkstra(destination.getText(), inProgress);
            }
        });
        
        Label flwsOut = new Label();
        flwsOut.setVisible(false);
        flwsOut.setLayoutY(430);
        flwsOut.setLayoutX(10);
        
        Button floydWarshall = new Button("Floyd-Warshall");
        floydWarshall.setLayoutY(330);
        floydWarshall.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(!destination.getText().isEmpty())
                {
                    flwsOut.setText(""+graph.floydWarshall(destination.getText()));
                    flwsOut.setVisible(true);
                    System.out.println(graph.floydWarshall(destination.getText()));
                }
            }
        });

        Rectangle fwOutput = new Rectangle();
        fwOutput.setWidth(120);
        fwOutput.setHeight(150);
        fwOutput.setX(5);
        fwOutput.setY(420);
        fwOutput.setFill(Color.rgb(200, 200, 200));
        
        Button dfs = new Button("Depth-First Search");
        dfs.setLayoutY(360);
        dfs.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(!destination.getText().isEmpty())
                {
                    Timeline timeline = new Timeline();
                    boolean vis[] = new boolean[graph.getSize()+1];
                    graph.dfs(1, 0.0, 0, vis, timeline);
                    timeline.play();
                }
            }
        });
        
        Button reset = new Button("Reset Distances");
        reset.setLayoutX(550);
        reset.setLayoutY(470);
        reset.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
            graph.resetDists();
        }
        });
        
        Button clear = new Button("Clear Graph");
        clear.setLayoutX(550);
        clear.setLayoutY(500);
        clear.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
            graph.reset();
            pane.getChildren().clear();
            pane.getChildren().add(addNode);
            pane.getChildren().add(addEdge);
            pane.getChildren().add(tnode1);
            pane.getChildren().add(tnode2);
            pane.getChildren().add(weight);
            pane.getChildren().add(reset);
            pane.getChildren().add(clear);
            pane.getChildren().add(edgeError);
            pane.getChildren().add(destination);
            pane.getChildren().add(bellmanFord);
            pane.getChildren().add(inProgress);
            pane.getChildren().add(dijkstra);
            pane.getChildren().add(floydWarshall);
            pane.getChildren().add(fwOutput);
            pane.getChildren().add(flwsOut);
            pane.getChildren().add(dfs);
        }
        });
        
        // button added to pane and pane added to scene
        pane.getChildren().add(addNode);
        pane.getChildren().add(addEdge);
        pane.getChildren().add(tnode1);
        pane.getChildren().add(tnode2);
        pane.getChildren().add(weight);
        pane.getChildren().add(reset);
        pane.getChildren().add(clear);
        pane.getChildren().add(edgeError);
        pane.getChildren().add(destination);
        pane.getChildren().add(bellmanFord);
        pane.getChildren().add(inProgress);
        pane.getChildren().add(dijkstra);
        pane.getChildren().add(floydWarshall);
        pane.getChildren().add(fwOutput);
        pane.getChildren().add(flwsOut);
        pane.getChildren().add(dfs);
        
        Scene scene = new Scene(pane, 700, 600);
        primaryStage.setTitle("draggin these   buttons");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
