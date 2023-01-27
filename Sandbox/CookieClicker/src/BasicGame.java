import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.awt.*;

public class BasicGame implements GameLoop {

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 750, 750, 20);
    }

    enum Screen {
        Start,
        Game,
        Shop,
        Settings,
        Achievements
    }

    List<Text> text = new ArrayList<>();
    List<Integer> clicksInMinute = new ArrayList<>();
    List<Building> buildings = Building.setBuildingList();
    int clickMultiplier = 1;


    Screen currentScreen = Screen.Start;
    static MainCookie mainCookie;

    GoldenCookie goldenCookie;
    double cookiesPerSecond;
    int timer;
    int stopwatch;
    boolean debug = false;
    int selectedBuilding = 0;

    @Override
    public void init() {
        setNewGame();
        //initialize cookie variables
        timer = 0;

        //initialize main cookie
        mainCookie = new MainCookie();
        mainCookie.cookiesPerClick = 1;
        mainCookie.cookiesPerSecond = 0;

        //initialize golden cookie
        goldenCookie = new GoldenCookie();

        //initialize SaxionApp settings
        SaxionApp.setFill(Color.black);
        SaxionApp.setTextDrawingColor(Color.white);
        SaxionApp.turnBorderOff();
    }

    @Override
    public void loop() {
        SaxionApp.clear();

        switch (currentScreen) {
            case Start ->           drawStartScreen();
            case Shop ->            drawShopInterface();
            case Game ->            drawMainScreen();
            case Settings ->        drawSettingScreen();
            case Achievements ->    drawAchievementsScreen();
        }

        //floating text
        for (int i = 0; i < text.size(); i++) {
            double percentage = (double) text.get(i).duration / 1500;
            int transparency = (int) (percentage * 255);
            SaxionApp.setTextDrawingColor(new Color(255, 255, 255, transparency));

            SaxionApp.drawText(text.get(i).text, text.get(i).xPos, text.get(i).yPos, text.get(i).size);
            text.get(i).yPos = text.get(i).yPos - 1;
            text.get(i).duration -= 20;

            //remove text from list
            if (text.get(i).duration <= 0) {
                text.remove(i);
            }
        }
        SaxionApp.setTextDrawingColor(Color.white);


        if (currentScreen == Screen.Shop| currentScreen == Screen.Achievements | currentScreen == Screen.Settings){
            SaxionApp.drawImage("Assets/exit.png",580,665,140,55);
        }
        timer += 20;
        if (timer >= 1000) {
            UpdateCookieCount(cookiesPerSecond);
            stopwatch++;
            timer = 0;
            if (!clicksInMinute.isEmpty()){
                if (clicksInMinute.size() > 75 & clicksInMinute.size() < 125){
                    clickMultiplier = 2;
                } else if (clicksInMinute.size() >= 125 & clicksInMinute.size() < 150) {
                    clickMultiplier = 3;
                } else if (clicksInMinute.size() >= 150 & clicksInMinute.size() < 175) {
                    clickMultiplier = 4;
                } else if (clicksInMinute.size() >= 175 ) {
                    clickMultiplier = 5;
                }else {
                    clickMultiplier = 1;
                }
            }
            if (SaxionApp.getRandomValueBetween(1, 1000) == 73) {
                goldenCookie.spawnGoldenCookie();
                goldenCookie.active = true;
            }

            for (int i = 0; i < clicksInMinute.size(); i++) {
                if (clicksInMinute.get(i) <= stopwatch-15){
                    clicksInMinute.remove(i);
                }

            }
        }
    }

    private void drawStartScreen() {
        SaxionApp.drawImage("Assets/beginbackground.png",0,0,750,750);
        SaxionApp.drawImage("Assets/titlescreen.png",70,50,620,94);
        SaxionApp.drawImage("Assets/cookie.png",280,150,200,200);
        SaxionApp.setTextDrawingColor(Color.white);
        SaxionApp.drawImage("Assets/emptybutton.png", 100,620,250,75);
        SaxionApp.drawText("New Game", 140, 640,35);
        SaxionApp.drawImage("Assets/emptybutton.png", 400,620,250,75);
        SaxionApp.drawText("Load Game", 440, 640,35);


        //
        ////SaxionApp.drawText("Cookieclicker",SaxionApp.getWidth()/2-125,100,40);
        //SaxionApp.setFill(new Color(244,164,96));
        //SaxionApp.drawRectangle(SaxionApp.getWidth()/2-125,500,250,50);
        //SaxionApp.drawRectangle(SaxionApp.getWidth()/2-125,575,250,50);
        //SaxionApp.setTextDrawingColor(Color.black);
        ////Start screen text
        //SaxionApp.drawText("Start new game",SaxionApp.getWidth()/2-100,510,30);
        //SaxionApp.drawText("Load Game", SaxionApp.getWidth()/2-75,585,30);
    }

    private void drawMainScreen() {
        //background and game elements
        SaxionApp.drawImage("Assets/background.jpg", 0, 0, SaxionApp.getWidth(), SaxionApp.getHeight());
        SaxionApp.drawImage("Assets/MainscreenMenu.png",540,550,200,190);

        //Cookie counter
        SaxionApp.setFill(new Color(0, 0, 0, 200));
        SaxionApp.drawRectangle(0, SaxionApp.getHeight() / 5, 1000, 75);
        SaxionApp.drawText(FormatAmount(mainCookie.currentCookies) + " cookies", SaxionApp.getWidth() / 2 - 50, SaxionApp.getHeight() / 5 + 15, 28);
        SaxionApp.drawText(FormatAmount(cookiesPerSecond) + " cookies per second", SaxionApp.getWidth() / 2 - 60, SaxionApp.getHeight() / 5 + 40, 24);
        //click speed
        if (clickMultiplier > 1){
            if (clickMultiplier == 5){
                SaxionApp.drawImage("Assets/multipliermax.png", 105, 580, 115, 115);
            }else {
                SaxionApp.drawImage("Assets/multiplier.png", 105, 580, 115, 115);
            }
            SaxionApp.drawText("x"+clickMultiplier, 143,620 ,40 );
        }


        //Main Cookie
        SaxionApp.drawImage(mainCookie.imageFile, SaxionApp.getWidth() / 2 - mainCookie.size / 2, SaxionApp.getHeight() / 2 - mainCookie.size / 2, mainCookie.size, mainCookie.size);

        //debug info
        if (debug) {
            SaxionApp.drawText("Time: " + timer, SaxionApp.getWidth() / 2 - 60, 10, 24);
            SaxionApp.drawText("Total time: " + stopwatch, 600, 10, 20);
            SaxionApp.drawText(clicksInMinute.size() * 4+" clicks per minute", 100, SaxionApp.getHeight() / 5 + 25, 24);
        }

        //display golden cookie
        if (goldenCookie.active) {
            if (debug){
                System.out.println(goldenCookie.duration);
            }
            if (goldenCookie.duration <= 0) {
                goldenCookie.active = false;
            } else if (goldenCookie.duration < 8000) {
                double percentage = (float) goldenCookie.duration / 8000;
                goldenCookie.size = (int) (percentage * 75);
            } else if (goldenCookie.duration > 12000) {
                double percentage = (float) (20000 - goldenCookie.duration) / 8000;
                goldenCookie.size = (int) (percentage * 75);
            } else {
                goldenCookie.size = 75;
            }
            if (goldenCookie.duration == 12000){
                SaxionApp.playSound("Assets/goldencookie.wav");
            }
            goldenCookie.duration -= 20;
            SaxionApp.drawImage(goldenCookie.imageFile,
                    goldenCookie.x - goldenCookie.size / 2,
                    goldenCookie.y - goldenCookie.size / 2,
                    goldenCookie.size,
                    goldenCookie.size);
        }
    }

    private void drawShopInterface() {
        String emptyButton;

        drawMainScreen();

        //Shop background
        SaxionApp.setFill(new Color(0, 0, 0, 200));
        SaxionApp.drawRectangle(0, 0, SaxionApp.getWidth(), SaxionApp.getHeight());
        SaxionApp.drawImage("Assets/buildingbackground.png",125,230,500,410);

        //draw detailed screen
        SaxionApp.drawImage("Assets/DetailBackground.png",10, 40, 730, 200);
        //Image
        SaxionApp.drawImage(buildings.get(selectedBuilding).fileName,40,70,140,140);
        //Name
        SaxionApp.drawText(buildings.get(selectedBuilding).name, 325, 65, 30);
        //Description
        SaxionApp.drawText(buildings.get(selectedBuilding).description, 210, 120, 24);
        //Production info
        SaxionApp.drawText("Produces " + buildings.get(selectedBuilding).baseProduction + " cookies per second", 210, 160, 14);
        //buy button
        if (buildings.get(selectedBuilding).getPrice(1) <= mainCookie.currentCookies) {
            SaxionApp.drawImage("Assets/buybutton.png",580, 115, 135, 50);
        } else {
            SaxionApp.drawImage("Assets/buyinactive.png",580, 115, 135, 50);
        }

        //price
        SaxionApp.drawImage("Assets/icon.png",585,78,30,30);
        SaxionApp.drawText(FormatAmount(buildings.get(selectedBuilding).getPrice(1)), 615, 82, 24);
        //buildings owned
        SaxionApp.drawText("Owned: " + buildings.get(selectedBuilding).amount, 615, 190, 22);


        //draw buildings
        int xPos;
        int yPos = 180;
        for (int i = 0; i < buildings.size(); i++) {
            if (i % 2 == 0) {
                xPos = SaxionApp.getWidth() / 2 - 225;
                yPos += 70;
            } else {
                xPos = SaxionApp.getWidth() / 2 + 10;
            }
            if (buildings.get(i).getPrice(1) <= mainCookie.currentCookies) {
                emptyButton = "Assets/emptybutton.png";
            } else {
                emptyButton = "Assets/inactivebutton.png";
            }
            //SaxionApp.drawRectangle(xPos, yPos, 200, 50);
            SaxionApp.drawImage(emptyButton,xPos, yPos, 225, 60);
            SaxionApp.drawImage(buildings.get(i).fileName,xPos+7,yPos+7,46,46);
            SaxionApp.drawText(buildings.get(i).name, xPos+65, yPos+20, 25);

            SaxionApp.drawText(String.format("%15s", buildings.get(i).amount), xPos + 85, yPos + 18, 30);
        }
    }
    private void drawSettingScreen(){
        //Main screen
        drawMainScreen();

        //Text color
        SaxionApp.setTextDrawingColor(Color.white);

        //Set opacity
        SaxionApp.setFill(new Color(0, 0, 0, 200));
        SaxionApp.drawRectangle(0, 0, 750, 750);
        SaxionApp.drawImage("Assets/verticalbackground.png",200,200,350,350);

        SaxionApp.drawText("Options", 315,230,40);

        // Buttons
        SaxionApp.drawImage("Assets/emptybutton.png",250,300,250,50);
        SaxionApp.drawText("Save Game",300,312,30);
        SaxionApp.drawImage("Assets/emptybutton.png",250,375,250,50);
        SaxionApp.drawText("Load Game",300,387,30);
        SaxionApp.drawImage("Assets/emptybutton.png",250,450,250,50);
        SaxionApp.drawText("Exit Game",300,462,30);

        //Text for buttons





    }
    private void drawAchievementsScreen(){
        //Main screen
        drawMainScreen();
        //Set opacity
        SaxionApp.setFill(new Color(0, 0, 0, 200));
        SaxionApp.drawRectangle(0, 0, 1000, SaxionApp.getHeight());
        // Button color
        SaxionApp.setFill(new Color(244,164,96));
        // Buttons
        SaxionApp.drawRectangle(SaxionApp.getWidth()/2-300,50,600,75);
        SaxionApp.drawRectangle(SaxionApp.getWidth()/2-300,150,600,75);
        SaxionApp.drawRectangle(SaxionApp.getWidth()/2-300,250,600,75);
        SaxionApp.drawRectangle(SaxionApp.getWidth()/2-300,350,600,75);
        SaxionApp.drawRectangle(SaxionApp.getWidth()/2-300,450,600,75);
        //Text color
        SaxionApp.setTextDrawingColor(Color.black);
        //Text for buttons
        SaxionApp.drawText("Click 100 times",SaxionApp.getWidth()/2-200,75,30);
        SaxionApp.drawText("Click 250 times", SaxionApp.getWidth()/2-200,175,30);
        SaxionApp.drawText("Click 1000 times",SaxionApp.getWidth()/2-200,275,30);
        SaxionApp.drawText("Click 2500 times",SaxionApp.getWidth()/2-200,375,30);
        SaxionApp.drawText("Click 10000 times",SaxionApp.getWidth()/2-200,475,30);
        if (mainCookie.clickCount >= 100){
            SaxionApp.drawImage("Assets/Cursor.png",SaxionApp.getWidth()/2-275,75,50,50);
        }
        if (mainCookie.clickCount >= 250){
            SaxionApp.drawImage("Assets/Cursor.png",SaxionApp.getWidth()/2-275,175,50,50);
        }
        if (mainCookie.clickCount >= 1000){
            SaxionApp.drawImage("Assets/Cursor.png",SaxionApp.getWidth()/2-275,275,50,50);
        }
        if (mainCookie.clickCount >= 2500){
            SaxionApp.drawImage("Assets/Cursor.png",SaxionApp.getWidth()/2-275,375,50,50);
        }
        if (mainCookie.clickCount >= 10000){
            SaxionApp.drawImage("Assets/Cursor.png",SaxionApp.getWidth()/2-275,475,50,50);
        }
    }
    @Override
    public void mouseEvent(MouseEvent mouseEvent) {
        //print mouse location if debug mode is active
        if (debug & mouseEvent.isMouseDown() & mouseEvent.isLeftMouseButton()) {
            System.out.println("X: " + mouseEvent.getX() + " Y: " + mouseEvent.getY());
        }
        if (mouseEvent.isMouseDown() & mouseEvent.isLeftMouseButton()){
            switch (currentScreen) {
                case Game -> gameMouseEvent(mouseEvent);
                case Shop -> shopMouseEvent(mouseEvent);
                case Start -> {
                    if (new Rectangle(100,620,250,75).contains(mouseEvent.getX(), mouseEvent.getY()))
                    {
                        //new game button
                        setNewGame();
                        currentScreen = Screen.Game;
                    } else if (new Rectangle(400,620,250,75).contains(mouseEvent.getX(), mouseEvent.getY())) {
                        //load game button
                        SaveFile saveData = new SaveFile().LoadData();
                        mainCookie = saveData.mainCookie;
                        buildings = saveData.buildings;
                        updateProduction();
                        currentScreen = Screen.Game;
                    }
                }
                case Settings -> {
                    //Save game button
                    if (new Rectangle(250,300,250,50).contains(mouseEvent.getX(),mouseEvent.getY())) {
                        SaveFile data = new SaveFile();
                        data.buildings = buildings;
                        data.mainCookie = mainCookie;
                        data.SaveData(data);
                        text.add(new Text(360,725,"Game saved", 40));
                    }
                    //Load game button
                    if (new Rectangle(250,375,250,50).contains(mouseEvent.getX(),mouseEvent.getY())) {
                        SaveFile loadData = new SaveFile().LoadData();
                        mainCookie = loadData.mainCookie;
                        buildings = loadData.buildings;
                        updateProduction();
                        currentScreen = Screen.Game;
                    }
                    //Return to main menu button
                    if (new Rectangle(250,450,250,50).contains(mouseEvent.getX(),mouseEvent.getY())) {
                        currentScreen = Screen.Start;
                    }
                    if (new Rectangle(580,665,140,55).contains(mouseEvent.getX(),mouseEvent.getY())){
                        currentScreen = Screen.Game;
                    }
                }
                case Achievements -> {
                    if (new Rectangle(580,665,140,55).contains(mouseEvent.getX(),mouseEvent.getY())){
                        currentScreen = Screen.Game;
                    }
                }
            }
        }
    }

    private void gameMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.isMouseDown() & mouseEvent.isLeftMouseButton()) {
            if (mainCookie.boundingCircle.contains(mouseEvent.getX(), mouseEvent.getY())) {
                mainCookie.currentCookies += mainCookie.cookiesPerClick * clickMultiplier;
                mainCookie.clickCount += 1;
                clicksInMinute.add(stopwatch);
                text.add(new Text(mouseEvent.getX(), mouseEvent.getY(), "+" + FormatAmount(mainCookie.cookiesPerClick * clickMultiplier)));
            }
            //mainscreen buttons
            if (new Rectangle(564,584,153,32).contains(mouseEvent.getX(), mouseEvent.getY())) {
                //shop button
                currentScreen = Screen.Shop;
            }
            if (new Rectangle(564,628,153,35).contains(mouseEvent.getX(), mouseEvent.getY())) {
                //achievement button
                currentScreen = Screen.Achievements;
            }
            if (new Rectangle(564,674,153,35).contains(mouseEvent.getX(), mouseEvent.getY())) {
                //options button
                currentScreen = Screen.Settings;
            }
            if (goldenCookie.active) {
                if (new Ellipse2D.Double(goldenCookie.x - goldenCookie.size / 2, goldenCookie.y - goldenCookie.size / 2, goldenCookie.size, goldenCookie.size).contains(mouseEvent.getX(), mouseEvent.getY())) {
                    goldenCookie.active = false;
                    int goldCookieValue = (int) (mainCookie.currentCookies * 0.15 + 13);
                    goldCookieValue += mainCookie.cookiesPerSecond * 900 + 13;

                    //15% of the current amount of banked (i.e. unspent) cookies + 13, or
                    //15 minutes worth of cookies (which is CpS * 900) + 13, whichever is less.
                    text.add(new Text(mouseEvent.getX(), mouseEvent.getY(), "+" + FormatAmount(goldCookieValue)));
                    mainCookie.currentCookies += goldCookieValue;
                }
            }
        }
    }

    private void shopMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.isMouseDown() & mouseEvent.isLeftMouseButton()) {
            //this for-loop checks for each building if it has been clicked
            int xPos;
            int yPos = SaxionApp.getWidth() / 4 + 20;
            for (int i = 0; i < buildings.size(); i++) {
                if (i % 2 == 0) {
                    xPos = SaxionApp.getWidth() / 2 - 200;
                    yPos += 60;
                } else {
                    xPos = SaxionApp.getWidth() / 2 + 10;
                }
                Rectangle currentRectangle = new Rectangle(xPos, yPos, 200, 50);
                if (currentRectangle.contains(mouseEvent.getX(), mouseEvent.getY())) {
                    selectedBuilding = i;
                }
            }
            //buy 1 button
            if (new Rectangle(620, 115, 100, 30).contains(mouseEvent.getX(), mouseEvent.getY())) {
                if (buildings.get(selectedBuilding).getPrice(1) <= mainCookie.currentCookies) {
                    UpdateCookieCount(-buildings.get(selectedBuilding).getPrice(1));
                    buildings.get(selectedBuilding).amount++;
                    updateProduction();
                }
            }
            if (new Rectangle(580,665,140,55).contains(mouseEvent.getX(),mouseEvent.getY())){
                currentScreen = Screen.Game;
            }
        }
    }
    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {

        if (currentScreen == Screen.Shop | currentScreen == Screen.Settings | currentScreen ==  Screen.Achievements) {
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_ESCAPE ) {
                //leave shop/Settings menu with Escape Key
                currentScreen = Screen.Game;
                updateProduction();
            }
        }
        if (currentScreen == Screen.Game | currentScreen == Screen.Shop){
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_A){
                currentScreen = Screen.Achievements;
            }
        }
        if (currentScreen == Screen.Game){
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_S){
                currentScreen = Screen.Shop;
                //Enter shop menu with the "S" Button
            }
        }
        if (currentScreen == Screen.Game | currentScreen == Screen.Shop){
            if (keyboardEvent.getKeyCode() == KeyboardEvent.VK_M){
                currentScreen = Screen.Settings;
                //Enter Settings menu with the "M" button
            }
        }
        //toggle debug mode with spawn commands and extra information
        if (keyboardEvent.isKeyPressed() & keyboardEvent.getKeyCode() == KeyboardEvent.VK_ENTER) {
            debug = !debug;
        }
        //debug commands
        if (debug) {
            if (keyboardEvent.isKeyPressed() & keyboardEvent.getKeyCode() == KeyboardEvent.VK_G) {
                goldenCookie.spawnGoldenCookie();
            }
            if (keyboardEvent.isKeyPressed() & keyboardEvent.getKeyCode() == KeyboardEvent.VK_SPACE) {
                mainCookie.currentCookies += 1000;
            }
            if (keyboardEvent.isKeyPressed() & keyboardEvent.getKeyCode() == KeyboardEvent.VK_BACK_SPACE) {
                mainCookie.currentCookies = 0;
            }
        }
    }

    private void setNewGame(){
//initialize cookie variables
        timer = 0;
        //initialize main cookie
        mainCookie = new MainCookie();
        mainCookie.cookiesPerClick = 1;
        mainCookie.cookiesPerSecond = 0;
        //initialize SaxionApp settings
        SaxionApp.setFill(Color.black);
        SaxionApp.setTextDrawingColor(Color.white);
        SaxionApp.turnBorderOff();
    }

    private void UpdateCookieCount(double amount) {
        mainCookie.currentCookies += amount;
    }

    private void updateProduction() {
        double production = 0;
        for (Building building : buildings) {
            production += (building.amount * building.baseProduction);
        }
        cookiesPerSecond = production;
    }

    private String FormatAmount(int amount) {
        //overload method when int is used
        return FormatAmount((double) amount);
    }

    private String FormatAmount(double amount) {
        amount = (int) (amount * 10);
        amount = amount / 10;
        if (amount > 100) {
            amount = (int) amount;
        }
        //formats numbers over 1 million to "1.1 million"
        if (amount >= 1000000) {
            return String.format(Locale.US, "%,.1f", amount / 1000000) + " million";
        } else if (amount >= 1000) {
            //formats numbers over 1000 to 1,312
            //DecimalFormat formatter = new DecimalFormat("#,###");
            //return formatter.format(amount);
            return String.format(Locale.US, "%,.0f", amount);

        } else if (amount == Math.floor(amount)) {
            //removes decimals if number is whole
            return String.format(Locale.US, "%.0f", amount);
        } else {
            //show 1 decimal if number is not whole
            return String.format(Locale.US, "%.1f", amount);
        }
    }
}

