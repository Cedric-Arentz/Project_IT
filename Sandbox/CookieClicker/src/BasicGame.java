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

    enum Screen {
        Start,
        Game,
        Shop,
        Settings
    }
    Screen currentScreen = Screen.Start;
    MainCookie mainCookie;
    double currentCookies;
    int cookiesPerClick;
    double cookiesPerSecond;
    int timer;
    List<Building> buildings = Building.setBuildingList();
    boolean showShop = false;
    int selectedBuilding = 0;

    @Override
    public void init() {
        //initialize cookie variables
        currentCookies = 0;
        cookiesPerSecond =0;
        cookiesPerClick = 1;

        timer = 0;

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

        switch (currentScreen){
            case Start -> currentScreen = Screen.Game;
            case Shop -> DrawShopInterface();
            case Game -> DrawMainScreen();
            case Settings -> {}
        }


        timer+=20;
        if (timer>=1000)
        {
            UpdateCookieCount(cookiesPerSecond);
            timer = 0;
        }





    }



    private void DrawMainScreen() {
        //background and game elements
        SaxionApp.drawImage("Sandbox/CookieClicker/background.jpg", 0, 0, SaxionApp.getWidth(),SaxionApp.getHeight());
        SaxionApp.drawImage("Sandbox/CookieClicker/ShopButton.png", SaxionApp.getWidth()-100,SaxionApp.getHeight()-50,90,40);

        //Cookie counter
        SaxionApp.setFill(new Color(0,0,0,200));
        SaxionApp.drawRectangle(0,SaxionApp.getHeight() / 5,1000,75);
        SaxionApp.drawText(FormatAmount(currentCookies) + " cookies", SaxionApp.getWidth()/2-50, SaxionApp.getHeight() / 5 + 15, 28);
        SaxionApp.drawText(FormatAmount(cookiesPerSecond) + " cookies per second", SaxionApp.getWidth()/2-60, SaxionApp.getHeight() / 5 + 40, 24);

        //Main Cookie
        SaxionApp.drawImage(mainCookie.imageFile, SaxionApp.getWidth()/2-mainCookie.size/2,SaxionApp.getHeight()/2-mainCookie.size/2,mainCookie.size, mainCookie.size);

        //Temporary timer
        SaxionApp.drawText("Time: "+ timer, SaxionApp.getWidth()/2-60, 10, 24);
    }

    private void DrawShopInterface() {
        DrawMainScreen();

        //Shop background
        SaxionApp.setFill(new Color(0,0,0,200));
        SaxionApp.drawRectangle(0,0,SaxionApp.getWidth(),SaxionApp.getHeight());
        //TODO: Replace Rectangle with Shop interface image

        //draw detailed screen
        SaxionApp.setFill(Color.blue);
        SaxionApp.drawRectangle(10,40,SaxionApp.getWidth()-20,SaxionApp.getHeight()/4);
        //Image
        SaxionApp.setFill(Color.cyan);
        SaxionApp.drawRectangle(40,70,120,120);
        //Name
        SaxionApp.drawText(buildings.get(selectedBuilding).name, SaxionApp.getWidth()/2-50,45,30);
        //Description
        SaxionApp.drawText(buildings.get(selectedBuilding).description, 210,120,24);
        //Production info
        SaxionApp.drawText("Produces " + buildings.get(selectedBuilding).baseProduction + " cookies per second", 210,160,14);
        //Buy buttons
        SaxionApp.drawText("Buy 1: ", 550,120,18);
        if (buildings.get(selectedBuilding).getPrice(1) <= currentCookies)
        {
            SaxionApp.setFill(Color.green);
        }else {
            SaxionApp.setFill(Color.red);
        }
        SaxionApp.drawRectangle(620,115,100,30);
        SaxionApp.drawText(FormatAmount(buildings.get(selectedBuilding).getPrice(1)),625, 120,24);
        //buildings owned
        SaxionApp.drawText("Owned: "+ buildings.get(selectedBuilding).amount,615,190,22);


        //draw buildings
        int xPos;
        int yPos  = SaxionApp.getWidth() / 4 + 20;
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getPrice(1) <= currentCookies)
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
            SaxionApp.drawText(String.format("%15s", String.valueOf(buildings.get(i).amount)), xPos + 60, yPos + 10, 30);

        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {
        switch (currentScreen)
        {
            case Game -> {
                if (mouseEvent.isMouseDown() & mouseEvent.isLeftMouseButton())
                {
                    if (mainCookie.boundingCircle.contains(mouseEvent.getX(),mouseEvent.getY()))
                    {
                        currentCookies+=cookiesPerClick;
                        mainCookie.clickCount+=1;
                    }
                    if (new Rectangle(SaxionApp.getWidth()-100,SaxionApp.getHeight()-50,90,40).contains(mouseEvent.getX(),mouseEvent.getY()))
                    {
                        currentScreen = Screen.Shop;
                    }
                }
            }
            case Shop -> shopMouseEvent(mouseEvent);
        }
    }

    private void shopMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.isMouseDown() & mouseEvent.isLeftMouseButton())
        {
            //this for-loop checks for each building if it has been clicked
            int xPos;
            int yPos  = SaxionApp.getWidth() / 4 + 20;
            for (int i = 0; i < buildings.size(); i++) {
                if (i % 2 == 0){
                    xPos = SaxionApp.getWidth() / 2 - 200;
                    yPos += 60;
                }else {
                    xPos = SaxionApp.getWidth() / 2 + 10;
                }
                Rectangle currentRectangle = new Rectangle(xPos, yPos, 200, 50);
                if (currentRectangle.contains(mouseEvent.getX(),mouseEvent.getY()))
                {
                    selectedBuilding = i;
                }
            }
            //buy 1 button
            if (new Rectangle(620,115,100,30).contains(mouseEvent.getX(),mouseEvent.getY())){
                if (buildings.get(selectedBuilding).getPrice(1) <= currentCookies){
                    UpdateCookieCount(-buildings.get(selectedBuilding).getPrice(1));
                    buildings.get(selectedBuilding).amount++;
                    updateProduction();
                }
            }
        }
    }

    private void updateProduction() {
        double production = 0;
        for (Building building:buildings) {
            production += (building.amount * building.baseProduction);
        }
        cookiesPerSecond = production;
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        if (currentScreen == Screen.Shop | currentScreen == Screen.Settings) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE) {
                //leave shop menu with Escape Key
                currentScreen = Screen.Game;
            }
        }
        if (keyboardEvent.isKeyPressed() & keyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE)
        {
            currentCookies+=1000;
        }
        if (keyboardEvent.isKeyPressed() & keyboardEvent.getKeyCode() == KeyboardEvent.VK_BACK_SPACE)
        {
            currentCookies = 0;
        }
    }

    private String FormatAmount(int amount) {
        //overload method when int is used
        return FormatAmount((double) amount);
    }
    private String FormatAmount(double amount) {
        amount = (int) ( amount * 10);
        amount = amount / 10;
        if (amount > 100){
            amount = (int) amount;
        }
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

    private void UpdateCookieCount(double amount) {
        currentCookies+=amount;
    }
}

