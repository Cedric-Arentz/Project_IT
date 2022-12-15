import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Menu implements Runnable {

    public static void main(String[] args) {
        SaxionApp.start(new Menu(), 1000, 1000 );
    }

    public void run() {

        SaxionApp.drawText("Menu", 450,300,35);
        SaxionApp.drawText("Save Game", 435,350,25);
        SaxionApp.drawText("Load Game", 435,400,25);
        
    }

}
