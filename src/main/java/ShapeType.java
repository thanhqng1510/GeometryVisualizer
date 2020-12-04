import java.awt.*;


public enum ShapeType {

    LINE(0, "./res/line.png"),
    RECT(1, "./res/rect.png"),
    NUM_TYPE(2, "");

    public static ShapeType[] getAllTypes() {
        ShapeType[] types = new ShapeType[NUM_TYPE.value];
        types[LINE.value] = LINE;
        types[RECT.value] = RECT;
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
            return new Line(new Point(), new Point(), paintColor);
        else if (value == RECT.value)
            return new Rect(new Point(), new Point(), paintColor);
        return null;
    }

    @Override
    public String toString() {
        if (value == LINE.value)
            return "Line";
        else if (value == RECT.value)
            return "Rectangle";
        return "";
    }

    private final int value;
    private final String iconPath;

}
