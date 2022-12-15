import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SaveFile {
    public ArrayList<JSONArray> LoadData() {
        JSONParser parser = new JSONParser();
        ArrayList<JSONArray> result = new ArrayList<>();
        try {

            Object obj = parser.parse(new FileReader("SaveFile.json"));
            JSONObject jsonObject = (JSONObject) obj;

                JSONArray cookieAmounts = (JSONArray) jsonObject.get("cookieAmounts");
                JSONArray buildings = (JSONArray) jsonObject.get("buildingAmounts");
                JSONArray upgrades = (JSONArray) jsonObject.get("upgradeCount");
                result.add(cookieAmounts);
                result.add(buildings);
                //result.add(upgrades);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    public void SaveData(double currentCookies, MainCookie mainCookie){

        JSONObject saveFile = new JSONObject();
        List<Integer> amounts = new ArrayList<>();
        List<Double> cookieAmounts = new ArrayList<>();

        for (int i = 0; i < Building.SetBuildingList().size();i++) {
            amounts.add(Building.SetBuildingList().get(i).amount);
        }

        cookieAmounts.add(currentCookies);
        cookieAmounts.add((double) mainCookie.clickCount);

        saveFile.put("buildingAmounts",amounts);
        saveFile.put("cookieAmounts", cookieAmounts);
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
