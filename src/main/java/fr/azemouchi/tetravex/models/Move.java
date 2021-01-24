package fr.azemouchi.tetravex.models;

public class Move {

    private final int index;
    private final int width;
    private final int height;

    public Move(int index, int width, int height) {
        this.index = index;
        this.width = width;
        this.height = height;
    }

    public int getIndex() {
        return index;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
