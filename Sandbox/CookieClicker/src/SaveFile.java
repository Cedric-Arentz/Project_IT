import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SaveFile {

    MainCookie mainCookie;
    List<Building> buildings;

    public SaveFile LoadData() {
        SaveFile progress = new SaveFile();
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader("SaveFile.json"));
            JSONObject jsonObject = (JSONObject) obj;

            progress.mainCookie = new MainCookie();
            progress.mainCookie.currentCookies = Integer.parseInt(jsonObject.get("currentCookies").toString());
            progress.mainCookie.clickCount = Integer.parseInt(jsonObject.get("clickCount").toString());

            JSONArray jsonArray = (JSONArray) jsonObject.get("buildings");
            progress.buildings = Building.setBuildingList();

            for (int i = 0; i < progress.buildings.size(); i++) {
                progress.buildings.get(i).amount = Integer.parseInt(jsonArray.get(i).toString());
            }
            System.out.print(progress.buildings.get(0).amount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return progress;
    }


    @SuppressWarnings("unchecked")
    public void SaveData(SaveFile data){

        JSONObject saveFile = new JSONObject();
        List<Integer> amounts = new ArrayList<>();

        for (int i = 0; i < data.buildings.size(); i++) {
            amounts.add(data.buildings.get(i).amount);
        }
        saveFile.put("currentCookies", data.mainCookie.currentCookies);
        saveFile.put("clickCount", data.mainCookie.clickCount);
        saveFile.put("buildings", amounts);


        try(FileWriter file = new FileWriter("SaveFile.json")) {
            file.write(saveFile.toJSONString());
            file.flush();
        }

        catch (IOException e){
            e.printStackTrace();
        }
    }
}
