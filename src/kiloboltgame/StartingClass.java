package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import kiloboltgame.framework.Animation;

import javax.swing.*;

public class StartingClass extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private Image image, background;

	public static int noTiles = 6, noObjects = 3, noDecorations = 2;
    public static Image tiles[], objects[];

    private Graphics second;
	private String baseString;
	private static Background bg1, bg2;
	private static int scroll = 0, scrollM = 0, scrollSpeed = 0;
	private static int mousePosX, mousePosY;

	private static char chosenTile;
	private static boolean tileOrObject;

	private Level level;

	private Tile mouseTile;
	private MapObject mouseObject;

	private static int gameWidth = 800, gameHeight = 480;

	private Button saveButton;

	static Scanner scanner = new Scanner (System.in);
	static String inVariable;

	@Override
	public void init() {
        chosenTile = '1';
        tileOrObject = true;
		setSize(gameWidth, gameHeight);
		scroll = 0; scrollM = 0; scrollSpeed = 0;
		tiles = new Image[noTiles];
		objects = new Image[noObjects + noDecorations];

		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Map Editor");
		//baseString = new File("").getAbsolutePath();
		baseString = "X:/MapEditor/Spirit-Runner-Map-Editor";//TODO Issue
		baseString = baseString.replace('\\', '/');
		baseString = "file:/" + baseString + "/src/kiloboltgame/";
		try {

			background = getImage(new URL(baseString + "/data/background.png"));
			for (int i = 0; i < noTiles; i++) { // TODO MAYBE BUG WITH noTiles
				tiles[i] = getImage(new URL(baseString + "/data/tile"+Integer.toString(i+1)+".png"));
			}
			for (int i = 0; i < noObjects + noDecorations; i++) {
				objects[i] = getImage(new URL(baseString + "/data/object"+Integer.toString(i+1)+".png"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(Background.getWidth(), 0);
        mouseTile = new Tile (0, 0, chosenTile);
        mouseObject = new MapObject (0, 0, '7');
        saveButton = new Button (10, 10, 100, 20, "Zapisz mape");

		System.out.println("\"nowa\" mapa czy \"edycja\"?");
		inVariable = scanner.nextLine();
		if (inVariable.equals("nowa")) {
			System.out.println("Podaj długość mapy (liczba kafelków): ");
			inVariable = scanner.nextLine();
			System.out.println("Tworze nowa mape");
			level = new Level(Integer.parseInt(inVariable));
		}
		else if (inVariable.equals("edycja")) {
			System.out.println("Wczytaj mape, podaj sciezke: ");
			inVariable = scanner.nextLine();
			level = new Level();
			level.start(inVariable);
		}
		else {
			System.out.println("Naucz sie czytac. Robie nowa mape...");
			System.out.println("Podaj długość mapy (liczba kafelków): ");
			inVariable = scanner.nextLine();
			System.out.println("Tworze nowa mape");
			level = new Level(Integer.parseInt(inVariable));
		}



		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		while (true) {
			if (Character.getNumericValue(chosenTile) > noTiles) tileOrObject = false;
			else tileOrObject = true;
		    scrollM += scrollSpeed;

		    if (scrollM % 40 == 0) scroll = scrollM;
            if (scroll < 0) scroll = 0;
            if (scroll > level.getWidth()*Tile.getWidth() - gameWidth) scroll = Tile.getWidth()*level.getWidth() - gameWidth;
		    level.update();
			bg1.update();
			bg2.update();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {
	    g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		paintTiles(g);

		if (tileOrObject)
        	g.drawImage(mouseTile.getTileImage(),  mouseTile.getTileX()/40 * 40, mouseTile.getTileY()/40 * 40, this);

		paintObjects(g);

		if (!tileOrObject)
			g.drawImage(mouseObject.getObjectImage(),  (int)mouseObject.getPosX()/40 * 40, (int)mouseObject.getPosY()/40 * 40, this); //TODO WHY THE FUCK IS MOUSEOBJECT NOT WORKING

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(saveButton.getPosX(), saveButton.getPosY(), saveButton.getWidth(), saveButton.getHeight());
		g.setColor(Color.BLACK);
		g.drawString(saveButton.getText(), saveButton.getPosX() + 15, saveButton.getPosY() + 15);
	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < level.getTilearray().size(); i++) {
			Tile t = (Tile) level.getTilearray().get(i);
			g.drawImage(t.getTileImage(), t.getTileX() - scroll, t.getTileY(), this);
		}
	}

	private void paintObjects(Graphics g) {
		for (int i = 0; i < level.getObjectsarray().size(); i++) {
			MapObject o = (MapObject) level.getObjectsarray().get(i);
			g.drawImage(o.getObjectImage(), (int)o.getPosX() - scroll, (int)o.getPosY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                    scrollSpeed = -8;
                break;

            case KeyEvent.VK_RIGHT:
                    scrollSpeed = 8;
                break;

            case KeyEvent.VK_Q:
                chosenTile = '1';
                break;
            case KeyEvent.VK_W:
                chosenTile = '2';
                break;
            case KeyEvent.VK_E:
                chosenTile = '3';
                break;
            case KeyEvent.VK_R:
                chosenTile = '4';
                break;
			case KeyEvent.VK_T:
				chosenTile = '5';
				break;
			case KeyEvent.VK_Y:
				chosenTile = '6';
				break;
			case KeyEvent.VK_U:
				chosenTile = '7';
				break;
			case KeyEvent.VK_I:
				chosenTile = '8';
				break;
			case KeyEvent.VK_O:
				chosenTile = '9';
				break;
			case KeyEvent.VK_P:
				chosenTile = 'A';
				break;
			case KeyEvent.VK_A:
				chosenTile = 'B';
				break;
        }
        if (Character.getNumericValue(chosenTile) <= noTiles)
			mouseTile.setTileImage(tiles[Character.getNumericValue(chosenTile)-1]);
		else
			mouseObject.setObjectImage(objects[Character.getNumericValue(chosenTile)-1-noTiles]);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
                    scrollSpeed = 0;
			    break;

			case KeyEvent.VK_RIGHT:
                    scrollSpeed = 0;
			    break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("mouseEntered");

	}

    @Override
    public void mouseMoved(MouseEvent e) {

		mouseTile.setTileX(e.getX());
		mouseTile.setTileY(e.getY());
		mouseObject.setPosX(e.getX());
		mouseObject.setPosY(e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("mouseExited");
    }

    @Override
    public void mousePressed(MouseEvent e) {
		if (!saveButton.pressButton(e.getX(), e.getY())) {

			mousePosX = (e.getX() + scroll) / 40;
			mousePosY = e.getY() / 40;
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (tileOrObject)
					level.placeTile(chosenTile, mousePosX, mousePosY);
				else
					level.placeObject(chosenTile, mousePosX * 40, mousePosY * 40);
			}
			else if (SwingUtilities.isRightMouseButton(e)) {
				if (tileOrObject)
					level.removeTile(mousePosX, mousePosY);
				else
					level.removeObject(mousePosX * 40, mousePosY * 40);
			}

		}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
		if (saveButton.pressButton(e.getX(), e.getY())) {
			System.out.println("Podaj sciezke do pliku do zapisu: ");
			inVariable = scanner.nextLine();

			try {
				level.saveMap(inVariable);
				System.out.println("Mapa zapisana!");
			} catch (IOException exc) {
				// TODO Auto-generated catch block
				exc.printStackTrace();
			}
		}
    }


    public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}
    public static int getScroll() {
        return scroll;
    }
    public static int getScrollSpeed() {
        return scrollSpeed;
    }
    public static int getNoTiles() {
		return noTiles;
	}

	public static int getNoObjects() {
		return noObjects;
	}
	public static int getNoDecorations() {
		return noDecorations;
	}

}