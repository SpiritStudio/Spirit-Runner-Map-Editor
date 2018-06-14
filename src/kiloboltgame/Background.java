package kiloboltgame;


public class Background {
	
	private int bgX, bgY, speedX;
	static int width = 800;
	
	public Background(int x, int y){
		bgX = x;
		bgY = y;
		speedX = 0;
	}

	public static int getWidth() {
		return width;
	}

	public void update() {
		/*if (bgX - StartingClass.getScroll() <= -width){
			bgX = width + StartingClass.getScroll();
		}*/
	}

	public int getBgX() {
		return bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	
	
	
}
