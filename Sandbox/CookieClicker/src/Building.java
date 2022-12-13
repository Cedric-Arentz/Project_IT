import java.util.ArrayList;
import java.util.List;

public class Building {
    String name;
    String description;
    String fileName;
    int baseCost;
    double baseProduction;
    int amount;

    public Building (String name, String description, String fileName, int baseCost, double baseProduction)
    {
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.baseCost = baseCost;
        this.baseProduction = baseProduction;
    }

    public int getPrice(int buyAmount){
        double cost;
        if (this.amount < 1){
            cost = this.baseCost;
        } else if (buyAmount == 1) {
            cost = Math.round(baseCost * Math.pow(1.15,amount));
        }else
        {
            cost = 0;
            for (int i = 0; i < buyAmount; i++) {
                cost += Math.round(baseCost * Math.pow(1.15,amount+i));
            }
        }
        return (int) cost;
    }





    public static List<Building> setBuildingList()
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

