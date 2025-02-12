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
import javafx.event.EventHandler;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class UI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
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
                    if (Double.parseDouble(weight.getText()) < 0)
                    {
                        edgeError.setVisible(true);
                    }
                    else
                    {
                        Edge edge = graph.addEdge(tnode1.getText(), tnode2.getText(), weight.getText());
                        if (Double.parseDouble(weight.getText()) < 0 || edge == null)
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
            }
        });
        
        Label inProgress = new Label("Simulation running!");
        inProgress.setLayoutY(390);
        inProgress.setVisible(false);
        inProgress.setStyle("-fx-text-fill: red");
        
        Button bellmanFord = new Button("Bellman-Ford");
        bellmanFord.setLayoutY(270);
        bellmanFord.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                graph.bellmanFord(inProgress);
            }
        });
        Button dijkstra = new Button("Dijkstra");
        dijkstra.setLayoutY(300);
        dijkstra.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                graph.dijkstra(inProgress);
            }
        });
        
        Button floydWarshall = new Button("Floyd-Warshall");
        floydWarshall.setLayoutY(330);
        floydWarshall.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                graph.floydWarshall(inProgress);
            }
        });
        
        Button dfs = new Button("Depth-First Search");
        dfs.setLayoutY(360);
        dfs.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Timeline timeline = new Timeline();
                boolean vis[] = new boolean[graph.getSize()+1];
                int d = graph.dfs(1, 0.0, 0, vis, timeline, inProgress);
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(d), event -> inProgress.setVisible(false)));
                timeline.play();
            }
        });
        /* Rectangle timeComplexity = new Rectangle();
        timeComplexity.setWidth(120);
        timeComplexity.setHeight(150);
        timeComplexity.setX(570);
        timeComplexity.setY(10);
        timeComplexity.setFill(Color.rgb(220, 220, 220));        
        Label tComp = new Label("TIME COMPLEXITY \n\nAlgorithm:_ \nTime(ms):_ \nTime complexity:_");
        tComp.setLayoutX(575);
        tComp.setLayoutY(15);*/
        
        Button reset = new Button("Reset Distances");
        reset.setLayoutX(550);
        reset.setLayoutY(500);
        reset.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
            graph.resetDists();
        }
        });
        
        Button clear = new Button("Clear Graph");
        clear.setLayoutY(540);
        clear.setLayoutX(550);
        clear.setStyle("-fx-background-color: #f09090");
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
                pane.getChildren().add(bellmanFord);
                pane.getChildren().add(inProgress);
                pane.getChildren().add(dijkstra);
                pane.getChildren().add(floydWarshall);
                pane.getChildren().add(dfs);
                //pane.getChildren().add(timeComplexity);
            }
        });
        
        // this code drags the button

        // button added to pane and pane added to scene
        pane.getChildren().add(addNode);
        pane.getChildren().add(addEdge);
        pane.getChildren().add(tnode1);
        pane.getChildren().add(tnode2);
        pane.getChildren().add(weight);
        pane.getChildren().add(reset);
        pane.getChildren().add(clear);
        pane.getChildren().add(edgeError);
        pane.getChildren().add(bellmanFord);
        pane.getChildren().add(inProgress);
        pane.getChildren().add(dijkstra);
        pane.getChildren().add(floydWarshall);
        pane.getChildren().add(dfs);
        //pane.getChildren().add(timeComplexity);
        //pane.getChildren().add(tComp);
        
        Scene scene = new Scene(pane,700, 600);
        primaryStage.setTitle("SPA Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.show();
    }
}
