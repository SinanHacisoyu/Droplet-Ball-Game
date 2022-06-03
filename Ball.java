//Sinan Hacisoyu

public class Ball {
	int xBall;
	int yBall;
	int velocityBallX;
	int velocityBallY;
	
	//Constructor
	public Ball(int xBall, int yBall, int velocityBallX, int velocityBallY) {
		super();
		this.xBall = xBall;
		this.yBall = yBall;
		this.velocityBallX = velocityBallX;
		this.velocityBallY = velocityBallY;
	}

	//Getter and Setters
	public int getxBall() {
		return xBall;
	}

	public void setxBall(int xBall) {
		this.xBall = xBall;
	}

	public int getyBall() {
		return yBall;
	}

	public void setyBall(int yBall) {
		this.yBall = yBall;
	}

	public int getVelocityBallX() {
		return velocityBallX;
	}

	public void setVelocityBallX(int velocityBallX) {
		this.velocityBallX = velocityBallX;
	}

	public int getVelocityBallY() {
		return velocityBallY;
	}

	public void setVelocityBallY(int velocityBallY) {
		this.velocityBallY = velocityBallY;
	}
	
	
}
