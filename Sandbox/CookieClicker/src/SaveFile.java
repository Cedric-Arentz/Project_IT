import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("SaveFile.json")){
            Object obj = jsonParser.parse(reader);

            JSONArray SaveFileList = (JSONArray) obj;
            System.out.println(SaveFileList);
        }

        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void SaveData(BasicGame basicGame){
        JSONObject saveFile = new JSONObject();
        saveFile.put("currentCookies", basicGame.currentCookies);
        saveFile.put("cookiesPerSecond", basicGame.cookiesPerSecond);

        try(FileWriter file = new FileWriter("SaveFile.json")) {
            file.write(saveFile.toJSONString());
            file.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
