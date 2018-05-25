package kiloboltgame;

import java.awt.*;

public class CollidableObject extends Object {

    public enum ObjectType {
        BEER, COIN, ENEMY
    }

    ObjectType objectType;
    Image objectImage;

    public CollidableObject(double posX, double posY, char chosenObject ) {
           this.posX = posX;
           this.posY = posY;

           if (Character.getNumericValue(chosenObject) - StartingClass.getNoTiles() <= StartingClass.getNoObjects()) {
               int objectNumber = Character.getNumericValue(chosenObject) - StartingClass.getNoTiles();

               objectImage = StartingClass.objects[objectNumber - 1];
               switch (objectNumber) {
                   case 1:
                       objectType = ObjectType.BEER;
                       break;
                   case 2:
                       objectType = ObjectType.COIN;
                       break;
                   case 3:
                       objectType = ObjectType.ENEMY;
                       break;
               }
           }
    }

    public boolean update(Player player) {
        if (checkPlayerCollision(player)) {
            //do something
            return true;
        }
        return false;
    }

    public boolean checkPlayerCollision(Player player) {
        if (player.getPosX() + player.getWidth() > this.posX && player.getPosX() < this.posX + this.width) {
            if (player.getPosY() + player.getHeight() > this.posY && player.getPosY() < this.posY + this.height) {
                return true;
            }
        }
        return false;
    }
    public Image getObjectImage() {
        return objectImage;
    }

    public void setObjectImage(Image object) {
        objectImage = object;
    }
    public ObjectType getObjectType() {  
        return objectType;               
    }                                    
}


