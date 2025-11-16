package ca.qc.bdeb.sim.prjtp2_aut25;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.w3c.dom.Text;

import java.awt.*;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.GREEN;

public class EcranDeChargement {
    private String texte;
    private long tempsDebut;
    private boolean termine;

    public EcranDeChargement(String texte){

        this.texte = texte;
        this.tempsDebut = System.nanoTime();
    }

    public void draw(GraphicsContext context){

        //Fond noir
        context.setFill(BLACK);
        context.fillRect(0,0,MainJavaFX.WIDTH,MainJavaFX.HEIGHT);

        //Texte vert affichant le niveau
        context.setFill(GREEN);
        context.setFont(Font.font("Qilka",48));
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);

        context.fillText(texte,
                MainJavaFX.WIDTH/2,
                MainJavaFX.HEIGHT/2
        );

    }
}
