package zadanie.gui.paintableComponents;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Static {
    public static final Map<Integer, Color> indexAndColorMap = new HashMap<>();
    static {
        indexAndColorMap.put(1,Color.green);
        indexAndColorMap.put(2,Color.MAGENTA);
        indexAndColorMap.put(3,Color.ORANGE);
        indexAndColorMap.put(4,Color.BLACK);
        indexAndColorMap.put(5,Color.cyan);
        indexAndColorMap.put(6,Color.blue);
        indexAndColorMap.put(7,Color.RED);
        indexAndColorMap.put(8,Color.pink);
        indexAndColorMap.put(9,Color.yellow);
        indexAndColorMap.put(10,Color.BLUE);
    }
}
