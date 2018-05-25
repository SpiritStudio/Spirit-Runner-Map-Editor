package kiloboltgame;

public class Player extends Object {

    private boolean inAir = true;

    public Player() {
        posX = speedY = 0;
        posY = 200;
        speedX = 4;
        width = 61;
        height = 63;
    }

    public void jump() {
        if (!inAir) speedY = -20;
        inAir = true;
    }

    public void update(){
        if(inAir) speedY += 1;
        else speedY = 0;
        posX += speedX;
        posY += speedY;
    }

    public boolean isInAir() { return inAir; }
    public void setInAir(boolean inAir) { this.inAir = inAir; }

}
