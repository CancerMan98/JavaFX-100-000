package test;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import static javafx.application.Application.launch;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Test extends Application {
    
    private static final String dotsURL = "test/5.png";
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // toplevel pane
        StackPane rootPane = new StackPane();
        Pane container = new Pane();
        container.setId("pane");
        rootPane.getChildren().add(container);

        Scene scene = new Scene(rootPane, 1280, 800);
        
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        int spawnNodes = 700;

        for (int i = 0; i < spawnNodes; i++) {
            spawnNode(scene, container);
        }


        primaryStage.setTitle("$100,00");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void spawnNode(Scene scene, Pane container) {

        Image dots = new Image(dotsURL);
        Circle node = new Circle(0);
        node.setManaged(false);
        node.setFill(new ImagePattern(dots, 1, 1, 1, 1, true));

        node.setCenterX(Math.random() * scene.getWidth());
        node.setCenterY(Math.random() * scene.getHeight());
        

        container.getChildren().add(node);


        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(node.radiusProperty(), 0),
                        new KeyValue(node.centerXProperty(), node.getCenterX()),
                        new KeyValue(node.centerYProperty(), node.getCenterY()),
                        new KeyValue(node.opacityProperty(), 0)),
                new KeyFrame(
                        Duration.seconds(5 + Math.random() * 5),
                        new KeyValue(node.opacityProperty(), Math.random()),
                        new KeyValue(node.radiusProperty(), Math.random() * 50)),
                new KeyFrame(
                        Duration.seconds(10 + Math.random() * 20),
                        new KeyValue(node.radiusProperty(), 0),
                        new KeyValue(node.centerXProperty(), Math.random() * scene.getWidth()),
                        new KeyValue(node.centerYProperty(), Math.random() * scene.getHeight()),
                        new KeyValue(node.opacityProperty(), 0))
        );

        timeline.setCycleCount(1);
        

        timeline.setOnFinished(evt -> {
            container.getChildren().remove(node);
            spawnNode(scene, container);
        });

        timeline.play();
    }
}