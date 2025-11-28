package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class MainJavaFX extends Application {
    public final static Canvas canva = new Canvas(900, 580);
    public final static double WIDTH = canva.getWidth();
    public final static double HEIGHT = canva.getHeight();

    @Override
    public void start(Stage stage) {

        var root = new Pane();
        var stack = new StackPane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var context = canva.getGraphicsContext2D();

        stack.getChildren().add(canva);
        root.getChildren().add(stack);
        stack.setStyle("-fx-background-color: black");

        //Crée partie
        var partie = new Partie();
//        partie.ecranChargementNiveau();

        //Gestion des touches pressées
        touchesPressees(scene);

        AnimationTimer timer = new AnimationTimer() {
            private long dernierTemps = System.nanoTime();

            @Override
            public void handle(long temps) {
                double deltaTemps = (temps - dernierTemps) * 1e-9;

                partie.update(deltaTemps);
                partie.enCollisionJournal();
                partie.draw(context);


                dernierTemps = temps;

            }
        };



        timer.start();


        stage.setTitle("Camelot à vélo");
        stage.getIcons().add(new Image("journal.png"));
        stage.setScene(scene);
        stage.show();
    }

    public void touchesPressees(Scene scene) {
        scene.setOnKeyPressed((e) -> {
            Input.setKeyPressed(e.getCode(), true);
        });

        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}