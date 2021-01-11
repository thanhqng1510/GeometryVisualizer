public enum CursorMode {

    SELECT(0),
    MOVE_AROUND(1),
    ZOOM_IN(2),
    ZOOM_OUT(3),
    DRAW(4),
    CHOOSE(5);


    public int getValue() {
        return value;
    }

    CursorMode(int value) {
        this.value = value;
    }

    private final int value;

}
