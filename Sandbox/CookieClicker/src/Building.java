import java.util.ArrayList;
import java.util.List;

public class Building {
    String name;
    String description;
    String fileName;
    int baseCost;
    double baseProduction;
    int amount;
    int cost = baseCost + (this.amount^2 * this.baseCost);

    public Building (String name, String description, String fileName, int baseCost, double baseProduction)
    {
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.baseCost = baseCost;
        this.baseProduction = baseProduction;
    }

    //public int GetPrice(int buyAmount){
    //    int cost;
    //    if (buyAmount > 2){
    //
    //    }
    //}





    public static List<Building> SetBuildingList()
    {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building("Cursor","Auto-clicks every 10 seconds","Sandbox/BasicGame/cursor.png",15, 0.1));
        buildings.add(new Building("Grandma","Bakes delicious cookies","Sandbox/BasicGame/grandma.png",100, 1));
        buildings.add(new Building("Test1","Lorem ipsum blahblah","Sandbox/BasicGame/grandma.png",1000, 10));
        buildings.add(new Building("Test2","Lorem ipsum blahblah","Sandbox/BasicGame/grandma.png",1000, 10));
        buildings.add(new Building("Test3","Lorem ipsum blahblah","Sandbox/BasicGame/grandma.png",1000, 10));
        return buildings;
    }

    public void UpdateAmount(int amount){

        this.amount += amount;
    }
}

