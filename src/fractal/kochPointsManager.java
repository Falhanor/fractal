package fractal;

import java.util.List;

public interface kochPointsManager {
	void initLine();
	void initTriangle();
	void computeNextStep() throws Exception;
	void computeXSteps(int n) throws Exception;
	List<Point2D[]> getLstSegments() throws Exception;
}
