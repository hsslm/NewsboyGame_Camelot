package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
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
        var stack = new StackPane();

        //touchesPressees(scene);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        stack.getChildren().add(canva);
        root.getChildren().add(stack);
        stack.setStyle("-fx-background-color: black");


        var context = canva.getGraphicsContext2D();

        var camera = new Camera();

        var camelot = new Camelot();
        var decor = new Decor();


        AnimationTimer timer = new AnimationTimer() {
            private long dernierTemps = System.nanoTime();

            @Override
            public void handle(long temps) {
                double deltaTemps = (temps - dernierTemps) * 1e-9;


                camelot.update(deltaTemps);
                camera.suivreCamelot(camelot);

                context.clearRect(0, 0, WIDTH, HEIGHT);
                decor.draw(context, camera);
                camelot.draw(context, camera);


                dernierTemps = temps;


            }
        };
        timer.start();
        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                // Ferme JavaFX
                Platform.exit();
            } else {
                Input.setKeyPressed(e.getCode(), true);
            }
        });
        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });


        stage.setTitle("Camelot à vélo");
        stage.getIcons().add(new Image("journal.png"));
        stage.setScene(scene);
        stage.show();
    }

    /*private static void touchesPresses(Scene scene) {
        //Quitter avec le bouton ESCAPE
        scene.setOnKeyPressed((e) -> {

            switch (e.getCode()) {
                case KeyCode.ESCAPE:
                    //ferme programm
                    Platform.exit();
                    break;
                case KeyCode.VK_RIGHT :
                    break;
                    case KeyCode.Left


            }
            if (e.getCode() == KeyCode.ESCAPE) {// Ferme JavaFXPlatform.exit();
            } else {
                Input.setKeyPressed(e.getCode(), true);
            }
        });
        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
    }

     */

    public static void main(String[] args) {
        launch();
    }
}