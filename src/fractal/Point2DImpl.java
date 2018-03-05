package fractal;

public class Point2DImpl implements Point2D {
	protected int x, y;

	public Point2DImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void x(int x) {
		this.x = x;
	}

	@Override
	public int x() {
		return this.x;
	}
	
	@Override
	public void y(int y) {
		this.y = y;
	}
	
	@Override
	public int y() {
		return this.y;
	}
	
	@Override
	public Point2D clone(){
		return new Point2DImpl(x,y);
	}
	
	@Override
	public String toString(){
		return "Point2D(" + this.x + ";" + this.y + ")";
	}

}
