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

public class Main extends Application {

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
        Label edgeError = new Label("Edge already created.");
        edgeError.setStyle("-fx-text-fill: red");
        edgeError.setVisible(false);
        edgeError.setLayoutY(210);
        // CHECK MANYA'S CODE
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
        
        // this code drags the button
        
        // button added to pane and pane added to scene
        pane.getChildren().add(addNode);
        pane.getChildren().add(addEdge);
        pane.getChildren().add(tnode1);
        pane.getChildren().add(tnode2);
        pane.getChildren().add(weight);
        pane.getChildren().add(edgeError);
        
        
        Scene scene = new Scene(pane,700, 600);
        primaryStage.setTitle("draggin these   buttons");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
