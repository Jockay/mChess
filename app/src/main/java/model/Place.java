package model;

public class Place {
	
	private int x;
	private int y;
	
	public Place(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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
		
	/**
	 * Decides if the parameter object equals to this object.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Place))
				return false;
		Place c = (Place) obj;
		return (this.x == c.getX() && this.y == c.getY());
	}
	
	/**
	 * Prints the object's state.
	 */
	@Override
	public String toString() {
		return "Place [x=" + x + ", y=" + y + "]";
	}
	
}

