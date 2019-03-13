package cg_models;

public class Point
{
	public Point()
	{
	}

	private double x;
	private double y;
	private double z;

	public Point(double x, double y , double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getZ() {
		return z;
	}
}