import nl.saxion.app.SaxionApp;

import java.awt.geom.Ellipse2D;

public class GoldenCookie {
    int x;
    int y;
    Ellipse2D boundingCircle = new Ellipse2D.Double(this.x,this.y,this.size, this.size);
    double direction;
    int duration = 20;
    String imageFile = "Sandbox/CookieClicker/goldencookie.png";
    int size = 75;
    boolean active = false;

    public void spawnGoldenCookie() {
        this.duration = 20000;
        this.x = SaxionApp.getRandomValueBetween(25, 725);
        this.y = SaxionApp.getRandomValueBetween(25, 725);
        this.active = true;
    }

}
