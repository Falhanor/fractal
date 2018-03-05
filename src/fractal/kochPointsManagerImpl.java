package fractal;

import java.util.List;
import java.util.LinkedList;


public class kochPointsManagerImpl implements kochPointsManager {
	protected List<Point2D> lstPoints;
	protected Point2D neutral = null;

	public kochPointsManagerImpl(){
		lstPoints = new LinkedList<Point2D>();
	}

	@Override
	public void initSample(int w,int h) {
		/*
		 * cross
		 *
		Point2D A = new Point2DImpl(0,h/2);
		Point2D B = new Point2DImpl(w,h/2);
		Point2D A2 = new Point2DImpl(w/2,0);
		Point2D B2 = new Point2DImpl(w/2,h);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(A);
		lstPoints.add(neutral);
		lstPoints.add(A2);
		lstPoints.add(B2);
		lstPoints.add(A2);	
		*/
		/*
		 * diagonals
		 *
		Point2D A = new Point2DImpl(0,0);
		Point2D B = new Point2DImpl(w,h);
		Point2D A2 = new Point2DImpl(w,0);
		Point2D B2 = new Point2DImpl(0,h);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(A);
		lstPoints.add(neutral);
		lstPoints.add(A2);
		lstPoints.add(B2);
		lstPoints.add(A2);	
		*/
		/*
		 * rectangle complet
		 *
		Point2D A = new Point2DImpl(w/4,h/4);
		Point2D B = new Point2DImpl(w/4 + 2*w/4,h/4);
		Point2D C = new Point2DImpl(w/4 + 2*w/4,h/4 + 2*h/4);
		Point2D D = new Point2DImpl(w/4 ,h/4 + 2*h/4);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(D);
		lstPoints.add(A);
		lstPoints.add(D);
		lstPoints.add(C);
		lstPoints.add(B);
		lstPoints.add(A);
		*/
		
		/* rectangle int
		 *
		 *
		Point2D A = new Point2DImpl(20,20);
		Point2D B = new Point2DImpl(20+w-40,20);
		Point2D C = new Point2DImpl(20+w-40,20+h-40);
		Point2D D = new Point2DImpl(20 ,20+h-40);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(D);
		lstPoints.add(A);
		*/
		/*
		 * line
		 *
		Point2D A = new Point2DImpl(20,h/2);
		Point2D B = new Point2DImpl(20+w-40,h/2);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(A);
		*/
		/*
		 * flocon
		 */
		Point2D A = new Point2DImpl(w/2,10);
		Point2D B = new Point2DImpl(w/5,3*h/4-10);
		Point2D C = new Point2DImpl(4*w/5,3*h/4-10);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(A);
		
		/*
		 * triangle interne
		 *
		Point2D B = new Point2DImpl(w/2,10);
		Point2D A = new Point2DImpl(w/2-(h-20)/2,h-10);
		Point2D C = new Point2DImpl(w/2+(h-20)/2,h-10);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(A);
		*/
		
		/*
		 * triangle complet
		 * 
		Point2D A = new Point2DImpl(w/2,10);
		Point2D B = new Point2DImpl(w/5,3*h/4-10);
		Point2D C = new Point2DImpl(4*w/5,3*h/4-10);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(A);
		lstPoints.add(C);
		lstPoints.add(B);
		lstPoints.add(A); 
		 */
		 
	}

	@Override
	public boolean addPoint(int x, int y) {
		return lstPoints.add(new Point2DImpl(x,y));
	}
	@Override
	public boolean addPoint(Point2D p) {
		return lstPoints.add(p.clone());
	}
	
	@Override
	public boolean addBlank() {
		return lstPoints.add(neutral);
	}
	
	@Override
	public void clear() {
		lstPoints.clear();
	}
	
	
	@Override
	public void computeNextStep() throws Exception {
		if(lstPoints.isEmpty() || lstPoints.size()<2)
			throw new Exception("There is not enought points declared.");
		
		List<Point2D> newLstPoints = new LinkedList<Point2D>();
		Point2D A = lstPoints.get(0);
		newLstPoints.add(A);
		Point2D previousPoint = A;
		for(int i=1; i<lstPoints.size(); ++i){
			Point2D thisPoint = lstPoints.get(i);
			if(previousPoint != null && thisPoint != null){
				newLstPoints.addAll(computeSegment(previousPoint,thisPoint));
			}
			newLstPoints.add(thisPoint);
			previousPoint = thisPoint;
		}
		lstPoints = newLstPoints;
	}
	
	private List<Point2D> computeSegment(Point2D A, Point2D B){
		Point2D t1 = new Point2DImpl(A.x()+(B.x()-A.x())/3 , A.y()+(B.y()-A.y())/3);
		Point2D t2 = new Point2DImpl(A.x()+2*(B.x()-A.x())/3 , A.y()+2*(B.y()-A.y())/3);
		Point2D s = new Point2DImpl((int)((t1.x()+t2.x())*Math.cos(Math.toRadians(60))-(t2.y()-t1.y())*Math.sin(Math.toRadians(60))),(int)((t1.y()+t2.y())*Math.cos(Math.toRadians(60))+(t2.x()-t1.x())*Math.sin(Math.toRadians(60))));
		List<Point2D> lstNewPoints = new LinkedList<Point2D>();
		lstNewPoints.add(t1);
		lstNewPoints.add(s);
		lstNewPoints.add(t2);
		return lstNewPoints;
	}

	@Override
	public void computeXSteps(int n) throws Exception {
		for(int step=0; step<n; ++step)
			computeNextStep();
	}
	
	@Override
	public String toString(){
		StringBuffer str = new StringBuffer();
		for(Point2D p : lstPoints)
			if(p!=null)
				str.append(p.toString() + ";");
			else
				str.append("neutral;");
		
		return str.toString();
	}

	@Override
	public List<Point2D[]> getLstSegments() throws Exception  {
		if(lstPoints.isEmpty() || lstPoints.size()<2)
			throw new Exception("There is not enought points declared.");
		
		List<Point2D[]> newLstSegment = new LinkedList<Point2D[]>();
		Point2D A = lstPoints.get(0).clone();
		Point2D previousPoint = A;
		for(int i=1; i<lstPoints.size(); ++i){
			Point2D thisPoint = lstPoints.get(i);
			if(previousPoint != null && thisPoint != null){
				thisPoint = lstPoints.get(i).clone();
				Point2D[] segment = {previousPoint,thisPoint};
				newLstSegment.add(segment);
			}
			previousPoint = thisPoint;
		}
		
		return newLstSegment;
	}

}
