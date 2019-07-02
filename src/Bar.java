import java.awt.Color;
public class Bar {
    private Color color;
    private int height;
    public Bar(int height) {
        this.height = height;
        color = generateColor();
    }
    public static Color generateColor(){
        int r = (int)(Math.random()*256);
        int g = (int)(Math.random()*256);
        int b = (int)(Math.random()*256);
        Color color = new Color(r,g,b);
        return color;
    }
    public int getHeight(){
        return height;
    }
    public Color getColor(){
        return color;
    }
}

