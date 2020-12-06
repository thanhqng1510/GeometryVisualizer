import java.awt.*;


public enum ShapeType {

    LINE(0, "./res/line.png"),
    RECT(1, "./res/rect.png"),
    OVAL(2, "./res/oval.png"),
    NUM_TYPE(3, "");

    public static ShapeType[] getAllTypes() {
        ShapeType[] types = new ShapeType[NUM_TYPE.value];
        types[LINE.value] = LINE;
        types[RECT.value] = RECT;
        types[OVAL.value] = OVAL;
        return types;
    }

    ShapeType(int value, String iconPath) {
        this.value = value;
        this.iconPath = iconPath;
    }

    public int getValue() {
        return value;
    }

    public String getIconPath() { return iconPath; }

    public Shape getInstance(Paint paintColor) {
        if (value == LINE.value)
            return new Line(0, 0, 0, 0, paintColor);
        else if (value == RECT.value)
            return new Rect(0, 0, 0, 0, paintColor);
        else if (value == OVAL.value)
            return new Oval(0, 0, 0, 0, paintColor);
        return null;
    }

    @Override
    public String toString() {
        if (value == LINE.value)
            return "Line";
        else if (value == RECT.value)
            return "Rectangle";
        else if (value == OVAL.value)
            return "Oval";
        return "";
    }

    private final int value;
    private final String iconPath;

}
