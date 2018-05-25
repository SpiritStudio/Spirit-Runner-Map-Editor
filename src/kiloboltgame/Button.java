package kiloboltgame;

public class Button {
    private int width;
    private int height;
    private int posX;
    private int posY;
    private String text;

    public Button(int posX, int posY, int width, int height, String text) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public boolean pressButton(int mousePosX, int mousePosY) {
        if (mousePosX > posX && mousePosX < posX+width && mousePosY > posY && mousePosY < posY+height) return true;
        else return false;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
