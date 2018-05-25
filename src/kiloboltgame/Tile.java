package kiloboltgame;

import java.awt.Image;

public class Tile {

	private int tileX, tileY;
	private char type;
	public Image tileImage;
	private static int width = 40;
	private Background bg = StartingClass.getBg1();


	public Tile(int x, int y, char typeChar) {
		tileX = x * 40;
		tileY = y * 40;

		type = typeChar;

		if (Character.getNumericValue(type) <= StartingClass.getNoTiles()) {
			tileImage = StartingClass.tiles[Character.getNumericValue(type)-1];
		}
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public char getTileType() { return type; }

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

	public static int getWidth() {return width;}
}
