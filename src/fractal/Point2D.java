package fractal;

public interface Point2D extends Cloneable{
	int x();
	void x(int x);
	int y();
	void y(int y);
	Point2D clone();
}
