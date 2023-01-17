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
        buildings.add(new Building("Cursor" ,"Auto-clicks every 10 seconds" ,"Assets/Cursor.png" ,15     , 0.1));
        buildings.add(new Building("Grandma","Bakes delicious cookies"      ,"Assets/Grandma.png",100    , 1));
        buildings.add(new Building("Farm"   ,"Lorem ipsum blahblah"         ,"Assets/Farm.png",1100   , 8));
        buildings.add(new Building("Mine"   ,"Lorem ipsum blahblah"         ,"Assets/Mine.png",12000  , 47));
        buildings.add(new Building("Factory","Lorem ipsum blahblah"         ,"Assets/Factory.png",130000 , 260));
        return buildings;
    }

    public void UpdateAmount(int amount){

        this.amount += amount;
    }
}

