public class Text {
    String text;
    int xPos;
    int yPos;
    int duration = 1500;
    int size = 20;

    public Text(int xPos, int yPos, String text)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
    }
    public Text(int xPos, int yPos, String text,int size)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        this.size = size;
    }
}
