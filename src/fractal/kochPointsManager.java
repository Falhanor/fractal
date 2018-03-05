package fractal;

import java.util.List;

public interface kochPointsManager {
	void initSample(int width,int height);
	
	boolean addPoint(int x, int y);
	boolean addPoint(Point2D p);
	boolean addBlank();
	void clear();
	
	void computeNextStep() throws Exception;
	void computeXSteps(int n) throws Exception;
	
	List<Point2D[]> getLstSegments() throws Exception;
}
