import java.util.Random;

//Sinan Hacisoyu
//119200060

public class Droplet {
	private Random ran;
	private int x;
	private int y;
	private int speed;
	public static int speedChangeQuantity=0;
	
	public Droplet() {
		ran = new Random();
		x=ran.nextInt(550);
		y=ran.nextInt(200);
		speed=ran.nextInt(4) + 1 +speedChangeQuantity; // adding 1 in case of get 0
		if(speed<=0) // checking that the speed is not become 0
			speed=1;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
