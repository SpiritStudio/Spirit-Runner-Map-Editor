package kiloboltgame;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Level {
    private ArrayList<Tile> tilearray;
    private ArrayList<CollidableObject> objectsarray;
    private char tiles[][];
    private int width, height;


    public Level(int width) {
        this.width = width;
        this.height = 12;
        tilearray = new ArrayList<Tile>();
        objectsarray = new ArrayList<CollidableObject>();
    }
    public Level() {
        this.width = 0;
        this.height = 12;
        tilearray = new ArrayList<Tile>();
        objectsarray = new ArrayList<CollidableObject>();
    }

    public void start(String map) {

        try {
            loadMap(map);
            System.out.println("Mapa zaladowana!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void update() {

    }

    private void loadMap(String filename) throws IOException {
        ArrayList lines = new ArrayList();

        String filePath = new File("").getAbsolutePath();
        filePath = filePath.replace('\\', '/');
        filePath = filePath + "/src/kiloboltgame/" + filename;
        System.out.println(filePath);

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("#")) {
                lines.add(line);
                width = Math.max(width, line.length());
            } else {
                break;
            }
        }
        height = lines.size()-1;

        for (int j = 0; j < height; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {
                //System.out.println(i + "is i ");

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    if (Character.isDigit(ch) || Character.isLetter(ch))
                        if (Character.getNumericValue(ch) != 0)
                            placeTile( ch, i,j );
                }

            }
        }
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }
            double posX, posY;
            char chosenObject;
            String linesTemp[] = line.split(",");

            chosenObject = (char)(Character.getNumericValue(linesTemp[0].charAt(0))+StartingClass.getNoTiles() + 48);
            posX = Double.parseDouble(linesTemp[1]);
            posY = Double.parseDouble(linesTemp[2]);
            objectsarray.add(new CollidableObject(posX, posY, chosenObject));
        }
    }

    public void placeTile(char chosenTile, int posX, int posY) {
        // Sprawdzanie, czy na tym miejscu jest już jakiś element
        boolean placeable = true;
        if (tilearray.size() > 0) {
            for (int i = 0; i < tilearray.size(); i++) {
                if (tilearray.get(i).getTileX()/Tile.getWidth() == posX) {
                    if (tilearray.get(i).getTileY()/Tile.getWidth() == posY) {
                        placeable = false;
                        //tilearray.remove(i); // To dam do usuniecia
                        break;
                    }
                }
            }
        }
        if (placeable) {
            Tile t = new Tile(posX, posY, chosenTile);
            tilearray.add(t);

            System.out.println("placed");
        }
    }

    public void removeTile(int posX, int posY) {
        // Sprawdzanie, czy na tym miejscu jest już jakiś element
        if (tilearray.size() > 0) {
            for (int i = 0; i < tilearray.size(); i++) {
                if (tilearray.get(i).getTileX()/Tile.getWidth() == posX) {
                    if (tilearray.get(i).getTileY()/Tile.getWidth() == posY) {
                        tilearray.remove(i);
                        System.out.println("removed");
                        break;
                    }
                }
            }
        }
    }

    public void placeObject(char chosenObject, int posX, int posY) {
        // Sprawdzanie, czy na tym miejscu jest już jakiś element
        boolean placeable = true;
        if (objectsarray.size() > 0) {
            for (int i = 0; i < objectsarray.size(); i++) {
                if (objectsarray.get(i).getPosX() == posX) {
                    if (objectsarray.get(i).getPosY() == posY) {
                        placeable = false;
                        //tilearray.remove(i); // To dam do usuniecia
                        break;
                    }
                }
            }
        }
        if (placeable) {
            CollidableObject o = new CollidableObject(posX, posY, chosenObject);
            objectsarray.add(o);
            System.out.println("placed");
        }
    }
    //TODO REMOVE OBJECT
    public void removeObject(int posX, int posY) {
        // Sprawdzanie, czy na tym miejscu jest już jakiś element
        if (objectsarray.size() > 0) {
            for (int i = 0; i < objectsarray.size(); i++) {
                if (objectsarray.get(i).getPosX() == posX) {
                    if (objectsarray.get(i).getPosY() == posY) {
                        objectsarray.remove(i);
                        System.out.println("removed");
                        break;
                    }
                }
            }
        }
    }

    public void saveMap(String mapSrc) throws IOException {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.replace('\\', '/');
        filePath = filePath + "/src/kiloboltgame/" + mapSrc;
        System.out.println(filePath);
        tiles = new char [height+1][width+1];
        for (int i = 0; i <= height; i++) {
            for (int j = 0; j <= width; j++) tiles[i][j] = '0';
        }

        FileWriter writer = new FileWriter(filePath);

        for (int i = 0; i < tilearray.size(); i++) {
            Tile t = tilearray.get(i);
            tiles[t.getTileY()/Tile.getWidth()][t.getTileX()/Tile.getWidth()] =  t.getTileType();
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                    writer.write(String.valueOf(tiles[i][j]));
            }
            writer.write(System.lineSeparator());
        }
        writer.write("###");
        for (int i = 0; i < objectsarray.size(); i++) {
            writer.write(System.lineSeparator());
            if (objectsarray.get(i).getObjectType() == CollidableObject.ObjectType.BEER) writer.write(Integer.toString(1));
            if (objectsarray.get(i).getObjectType() == CollidableObject.ObjectType.COIN) writer.write(Integer.toString(2));
            if (objectsarray.get(i).getObjectType() == CollidableObject.ObjectType.ENEMY) writer.write(Integer.toString(3));

            writer.write("," + Double.toString(objectsarray.get(i).getPosX()) + "," + Double.toString(objectsarray.get(i).getPosY()));
        }

        writer.close();

    }

    public ArrayList<Tile> getTilearray() {
        return tilearray;
    }
    public int getWidth() {
        return width;
    }


    public ArrayList<CollidableObject> getObjectsarray() {
        return objectsarray;
    }
}
