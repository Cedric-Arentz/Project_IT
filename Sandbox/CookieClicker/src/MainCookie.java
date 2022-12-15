import nl.saxion.app.SaxionApp;

import java.awt.geom.Ellipse2D;

public class MainCookie {

    String imageFile = "Sandbox/CookieClicker/cookie.png";
    int size = SaxionApp.getWidth() / 3;

    int clickCount = 0;
    int currentCookies;
    int cookiesPerSecond;
    int cookiesPerClick = 1;
    Ellipse2D boundingCircle = new Ellipse2D.Double(SaxionApp.getWidth()/2-this.size/2,SaxionApp.getHeight()/2-this.size/2,this.size, this.size);

}
