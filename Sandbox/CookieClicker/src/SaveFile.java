import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SaveFile {
    public void LoadData(){

        JSONParser parser = new JSONParser();

        try{

            Object obj = parser.parse(new FileReader("SaveFile.json"));
            JSONObject jsonObject = (JSONObject)obj;

            long clickCount = (long) jsonObject.get("clickCount");
            double currentCookies = (double)jsonObject.get("currentCookies");
            JSONArray buildings =(JSONArray)jsonObject.get("buildingAmounts");
            MainCookie cookie = new MainCookie();
            BasicGame game = new BasicGame();
            cookie.clickCount = (int) clickCount;
            game.currentCookies = currentCookies;

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public void SaveData(double currentCookies, MainCookie mainCookie){

        JSONObject saveFile = new JSONObject();
        List<Integer> amounts = new ArrayList<>();

        for (int i = 0; i < Building.SetBuildingList().size();i++) {
            amounts.add(Building.SetBuildingList().get(i).amount);
        }

        saveFile.put("buildingAmounts",amounts);
        saveFile.put("currentCookies", currentCookies);
        saveFile.put("clickCount", mainCookie.clickCount);
        //saveFile.put("upgradeCount", upgradeCount);

        try(FileWriter file = new FileWriter("SaveFile.json")) {
            file.write(saveFile.toJSONString());
            file.flush();
        }

        catch (IOException e){
            e.printStackTrace();
        }
    }
}
