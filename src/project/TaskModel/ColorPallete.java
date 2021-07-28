package project.TaskModel;

import java.util.HashMap;
import java.util.Map;

public class ColorPallete {

    static Map<Integer, String> mapOfColor = new HashMap<>();

    public static void initColorMap() {

        mapOfColor.put(0, "yellow");
        mapOfColor.put(1, "blue");
        mapOfColor.put(2, "pink");
        mapOfColor.put(3, "red");
        mapOfColor.put(4, "black");
        mapOfColor.put(5, "red");
        mapOfColor.put(6, "purple");
        mapOfColor.put(7, "indigo");
        mapOfColor.put(8, "brown");
        mapOfColor.put(9, "green");
        mapOfColor.put(10, "violet");
        mapOfColor.put(11, "orange");
        mapOfColor.put(12, "ash");
    }

    public static String getColor(int colorIndex) {
        return mapOfColor.get(colorIndex);
    }
}
