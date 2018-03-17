package fractal;

import java.awt.Polygon;
import java.util.List;

public interface kochPointsManager {
	int initSample(int width,int height);
	int initSample(int width, int height, int sampleId);
	
	boolean addPoint(int x, int y);
	boolean addPoint(Point2D p);
	boolean addBlank();
	void clear();
	
	void computeNextStep() throws Exception;
	void computeXSteps(int n) throws Exception;
	
	List<Point2D[]> getLstSegments() throws Exception;
	List<Polygon> GetPolygons() throws Exception;
}
