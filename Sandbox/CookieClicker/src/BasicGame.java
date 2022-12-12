import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.util.List;
import java.util.Locale;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class BasicGame implements GameLoop {

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 750, 750, 20);
    }

    MainCookie mainCookie;
    double currentCookies;
    int cookiesPerClick;
    double cookiesPerSecond;
    int cursorCost;
    int timer;
    List<Building> buildings = Building.SetBuildingList();
    boolean showShop = false;
    int selectedBuilding = 1;

    @Override
    public void init() {
        //initialize cookie variables
        currentCookies = 0;
        cookiesPerSecond =0;
        cookiesPerClick = 1;

        timer = 0;

        cursorCost = 15;

        //initialize main cookie
        mainCookie = new MainCookie();
        mainCookie.size = SaxionApp.getWidth() / 3;
        mainCookie.boundingCircle = new Ellipse2D.Double(SaxionApp.getWidth()/2-mainCookie.size/2,SaxionApp.getHeight()/2-mainCookie.size/2,mainCookie.size, mainCookie.size);

        //initialize SaxionApp settings
        SaxionApp.setFill(Color.black);
        SaxionApp.setTextDrawingColor(Color.white);
        SaxionApp.turnBorderOff();





    }

    @Override
    public void loop() {
        SaxionApp.clear();
        DrawMainScreen();
        if (showShop)
        {
            DrawShopInterface();
        }


        timer+=20;
        if (timer>=1000)
        {
            UpdateCookieCount();
            timer = 0;
        }





    }



    private void DrawMainScreen() {
        //background and game elements
        SaxionApp.drawImage("Sandbox/CookieClicker/background.jpg", 0, 0, 1000, 1000);
        SaxionApp.drawImage("Sandbox/CookieClicker/ShopButton.png", SaxionApp.getWidth()-100,SaxionApp.getHeight()-50,90,40);

        //Cookie counter
        SaxionApp.setFill(Color.black);
        SaxionApp.drawRectangle(0,SaxionApp.getHeight() / 5,1000,75);
        SaxionApp.drawText(FormatAmount(currentCookies) + " cookies", SaxionApp.getWidth()/2-50, SaxionApp.getHeight() / 5 + 15, 28);
        SaxionApp.drawText(FormatAmount(cookiesPerSecond) + " cookies per second", SaxionApp.getWidth()/2-60, SaxionApp.getHeight() / 5 + 40, 24);

        //Main Cookie
        SaxionApp.drawImage(mainCookie.imageFile, SaxionApp.getWidth()/2-mainCookie.size/2,SaxionApp.getHeight()/2-mainCookie.size/2,mainCookie.size, mainCookie.size);

        //Temporary timer
        SaxionApp.drawText("Time: "+timer, SaxionApp.getWidth()/2-60, 10, 24);
    }

    private void DrawShopInterface() {

        //Shop background
        SaxionApp.setFill(Color.black);
        SaxionApp.drawRectangle(0,0,SaxionApp.getWidth(),SaxionApp.getHeight());
        //TODO: Replace Rectangle with Shop interface image

        //draw detailed screen
        SaxionApp.setFill(Color.blue);
        SaxionApp.drawRectangle(10,40,SaxionApp.getWidth()-20,SaxionApp.getHeight()/4);
        SaxionApp.drawText(buildings.get(selectedBuilding).name, SaxionApp.getWidth()/2-40,45,24);


        //draw buildings
        int xPos;
        int yPos  = SaxionApp.getWidth() / 4 + 20;
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).cost <= currentCookies)
            {
                SaxionApp.setFill(Color.green);
            }else{
                SaxionApp.setFill(Color.red);
            }
            if (i % 2 == 0){
                xPos = SaxionApp.getWidth() / 2 - 200;
                yPos += 60;
            }else {
                xPos = SaxionApp.getWidth() / 2 + 10;
            }
            SaxionApp.drawRectangle(xPos, yPos, 200, 50);
            SaxionApp.drawText(buildings.get(i).name, xPos, yPos, 25);

        }
    }

    private String FormatAmount(int amount) {
        //overload method when int is used
        return FormatAmount((double) amount);
    }
    private String FormatAmount(double amount) {
        amount = (int) ( amount * 10);
        amount = amount / 10;
        //formats numbers over 1 million to "1.1 million"
        if (amount >= 1000000)
        {
            return String.format(Locale.US, "%,.1f", amount/1000000) + " million" ;
        }
        else if (amount >= 1000){
            //formats numbers over 1000 to 1,312
            //DecimalFormat formatter = new DecimalFormat("#,###");
            //return formatter.format(amount);
            return String.format(Locale.US, "%,.0f", amount);

        }
        else if (amount == Math.floor(amount)) {
            //removes decimals if number is whole
            return String.format(Locale.US, "%.0f", amount);
        } else {
            //show 1 decimal if number is not whole
            return String.format(Locale.US, "%.1f", amount);
        }
    }

    private void UpdateCookieCount() {
        currentCookies+=cookiesPerSecond;
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        //Resize test, Keyboard- en mouseEvents werken niet na resize
        //if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_1)
        //{
        //    SaxionApp.resize(750,750);
        //}
        //if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_2)
        //{
        //    SaxionApp.resize(1000,1000);
        //}

    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.isMouseDown() & mouseEvent.isLeftMouseButton())
        {
            if (mainCookie.boundingCircle.contains(mouseEvent.getX(),mouseEvent.getY()))
            {
                currentCookies+=cookiesPerClick;
                mainCookie.clickCount+=1;
            }
            if (new Rectangle(SaxionApp.getWidth()-100,SaxionApp.getHeight()-50,90,40).contains(mouseEvent.getX(),mouseEvent.getY()))
            {
                //if (currentCookies >= cursorCost)
                //{
                //    currentCookies-=cursorCost;
                //    cookiesPerSecond = cookiesPerSecond + 0.1;
                //}
                showShop = !showShop;
            }
            if (showShop)
            {
                //shopEvent(MouseEvent mouseEvent);
            }

        }
    }
}

