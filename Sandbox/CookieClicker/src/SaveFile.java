import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SaveFile {
    public ArrayList<Double> LoadData() {
        JSONParser parser = new JSONParser();
        ArrayList<Double> result = new ArrayList<>();
        try {

            Object obj = parser.parse(new FileReader("SaveFile.json"));
            JSONObject jsonObject = (JSONObject) obj;

                double clickCount = (double) jsonObject.get("clickCount");
                double currentCookies = (double) jsonObject.get("currentCookies");
                double Cursor = (long) jsonObject.get("Cursor");
                double Grandma = (long) jsonObject.get("Grandma");
                //JSONArray upgrades = (JSONArray) jsonObject.get("upgradeCount");
                result.add(clickCount);
                result.add(currentCookies);
                result.add(Cursor);
                result.add(Grandma);
                //result.add(buildings);
                //result.add(upgrades);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    public void SaveData(double currentCookies, MainCookie mainCookie){

        JSONObject saveFile = new JSONObject();
        //List<Integer> amounts = new ArrayList<>();
        List<Double> cookieAmounts = new ArrayList<>();

        for (int i = 0; i < Building.SetBuildingList().size();i++) {
            saveFile.put(Building.SetBuildingList().get(i).name,Building.SetBuildingList().get(i).amount);
           // amounts.add(Building.SetBuildingList().get(i).amount);
        }

        cookieAmounts.add(currentCookies);
        cookieAmounts.add(mainCookie.clickCount);

        //saveFile.put("buildingAmounts",amounts);
        saveFile.put("currentCookies", cookieAmounts.get(0));
        saveFile.put("clickCount", cookieAmounts.get(1));
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
