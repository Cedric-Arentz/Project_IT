import com.sun.tools.javac.Main;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BasicGame implements GameLoop {

    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1000, 1000, 20);
    }

    MainCookie mainCookie;
    double currentCookies;
    int cookiesPerClick;
    double cookiesPerSecond;
    int cursorCost;
    int timer;
    List<Building> buildings = Building.SetBuildingList();

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
        mainCookie.boundingCircle = new Ellipse2D.Double(SaxionApp.getWidth()/2-mainCookie.size/2,SaxionApp.getHeight()/2-mainCookie.size/2,mainCookie.size, mainCookie.size);

        //initialize SaxionApp settings
        SaxionApp.setFill(Color.black);
        SaxionApp.setTextDrawingColor(Color.white);
        SaxionApp.turnBorderOff();



    }

    @Override
    public void loop() {
        SaxionApp.clear();
        SaxionApp.drawImage("Sandbox/CookieClicker/background.jpg", 0, 0, 1000, 1000);
        SaxionApp.drawImage("Sandbox/CookieClicker/ShopButton.png", 700,750,90,40);

        SaxionApp.drawRectangle(0,200,1000,75);


        SaxionApp.drawImage(mainCookie.imageFile, SaxionApp.getWidth()/2-mainCookie.size/2,SaxionApp.getHeight()/2-mainCookie.size/2,mainCookie.size, mainCookie.size);
        SaxionApp.drawText(FormatAmount(currentCookies) + " cookies", SaxionApp.getWidth()/2-50, 215, 28);
        SaxionApp.drawText(FormatAmount(cookiesPerSecond) + " cookies per second", SaxionApp.getWidth()/2-60, 240, 24);
        SaxionApp.drawText("Time: "+timer, SaxionApp.getWidth()/2-60, 10, 24);

        timer+=20;
        if (timer>=1000)
        {
            UpdateCookieCount();
            timer = 0;
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

    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.isMouseDown() & mouseEvent.isLeftMouseButton())
        {
            if (mainCookie.boundingCircle.contains(mouseEvent.getX(),mouseEvent.getY()))
            {
                currentCookies+=cookiesPerClick;
                mainCookie.clickCount+=1.0;
            }
            if (new Rectangle(700,750,90,40).contains(mouseEvent.getX(),mouseEvent.getY()))
            {
                /*if (currentCookies >= cursorCost)
                {
                    currentCookies-=cursorCost;
                    cookiesPerSecond = cookiesPerSecond + 0.1;
                }*/
                SaveFile saveFile = new SaveFile();
                saveFile.SaveData(currentCookies, mainCookie);
                /*ArrayList<Double> loadedData;
               loadedData = saveFile.LoadData();
               mainCookie.clickCount = loadedData.get(0);
               currentCookies = loadedData.get(1);
               buildings.get(0).amount = loadedData.get(2).intValue();
               buildings.get(1).amount = loadedData.get(3).intValue();*/
            }
        }
    }
}
