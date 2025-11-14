package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX extends Application {
    public final static Canvas canva = new Canvas(900, 580);
    public final static double WIDTH = canva.getWidth();
    public final static double HEIGHT = canva.getHeight();

    @Override
    public void start(Stage stage) throws IOException {

        var root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        var context = canva.getGraphicsContext2D();
        //root.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, null, null)));
        var camera = new Camera();
        var camelot = new Camelot();





        AnimationTimer timer = new AnimationTimer() {
            private long dernierTemps = System.nanoTime();

            @Override
            public void handle(long temps) {
                double deltaTemps = (temps - dernierTemps) * 1e-9;

                camelot.update(deltaTemps);
                camera.suivreCamelot(camelot);

                camelot.draw(context,camera);



                dernierTemps = temps;


            }
        };
        timer.start();


        root.getChildren().add(canva);
        stage.setTitle("Camelot à vélo");
        stage.getIcons().add(new Image("journal.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}