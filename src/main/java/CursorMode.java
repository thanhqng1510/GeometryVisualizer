public enum CursorMode {

    SELECT(0),
    DRAW(1);

    public int getValue() {
        return value;
    }

    CursorMode(int value) {
        this.value = value;
    }

    private final int value;

}
