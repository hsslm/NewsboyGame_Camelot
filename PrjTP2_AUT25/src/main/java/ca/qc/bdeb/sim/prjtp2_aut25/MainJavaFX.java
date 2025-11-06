package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX extends Application {
    public final static Canvas canva = new Canvas(900,580);
    public final static double WIDTH = canva.getWidth();
    public final static double HEIGHT = canva.getHeight();



    @Override
    public void start(Stage stage) throws IOException {
        var root = new Pane();
        Scene scene = new Scene(root, 900, 580);














        stage.setTitle("Camelot à vélo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}