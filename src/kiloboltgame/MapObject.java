package kiloboltgame;

import java.awt.*;

public class MapObject extends Object {

    public enum ObjectType {
        BEER, COIN, ENEMY, DECORATION;
    }

    private ObjectType objectType;
    private Image objectImage;
    private int objectNumber;

    public MapObject(double posX, double posY, char chosenObject ) {
           this.posX = posX;
           this.posY = posY;

           if (Character.getNumericValue(chosenObject) - StartingClass.getNoTiles() <= StartingClass.getNoObjects() + StartingClass.getNoDecorations()) {
               objectNumber = Character.getNumericValue(chosenObject) - StartingClass.getNoTiles();

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
                   default:
                       objectType = ObjectType.DECORATION;
                       break;
               }
           }
    }

    public Image getObjectImage() {
        return objectImage;
    }
    public int getObjectNumber() { return objectNumber; }
    public void setObjectImage(Image object) {
        objectImage = object;
    }
    public ObjectType getObjectType() {  
        return objectType;               
    }                                    
}


