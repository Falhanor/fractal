package fractal;

import java.util.List;
import java.util.LinkedList;


public class kochPointsManagerImpl implements kochPointsManager {
	List<Point2D> lstPoints;

	public kochPointsManagerImpl(){
		lstPoints = new LinkedList<Point2D>();
	}

	@Override
	public void initLine() {
		
		
		/*
		 * rectangle complet
		 * 
		 */
		Point2D A = new Point2DImpl(100,170);
		Point2D B = new Point2DImpl(680,170);
		Point2D C = new Point2DImpl(680,390);
		Point2D D = new Point2DImpl(100,390);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(D);
		lstPoints.add(A);
		lstPoints.add(D);
		lstPoints.add(C);
		lstPoints.add(B);
		lstPoints.add(A);
		
		
		/* rectangle int
		 * 
		Point2D A = new Point2DImpl(25,20);
		Point2D B = new Point2DImpl(755,20);
		Point2D C = new Point2DImpl(755,540);
		Point2D D = new Point2DImpl(25,540);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(D);
		lstPoints.add(A);
		 */
		/***
		 * line
		 *//*
		Point2D A = new Point2DImpl(750,275);
		Point2D B = new Point2DImpl(25,275);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(A);
		*/
	}

	@Override
	public void initTriangle() {
		/*
		 * flocon
		 */
		Point2D A = new Point2DImpl(400,20);
		Point2D B = new Point2DImpl(175,410);
		Point2D C = new Point2DImpl(625,410);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(A);
		/*
		 * triangle interne
		 * 
		Point2D B = new Point2DImpl(400,10);
		Point2D A = new Point2DImpl(100,535);
		Point2D C = new Point2DImpl(700,535);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(A);
		*/
		
		/*
		 * triangle complet
		 * 
		Point2D A = new Point2DImpl(400,20);
		Point2D B = new Point2DImpl(175,410);
		Point2D C = new Point2DImpl(625,410);
		lstPoints.add(A);
		lstPoints.add(B);
		lstPoints.add(C);
		lstPoints.add(A);
		lstPoints.add(C);
		lstPoints.add(B);
		lstPoints.add(A); 
		 * 
		 */
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
			newLstPoints.addAll(computeSegment(previousPoint,thisPoint));
			newLstPoints.add(thisPoint);
			previousPoint = thisPoint;
		}
		//newLstPoints.addAll(computeSegment(previousPoint,A));// à inverser peut-être....
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
			str.append(p.toString() + ";");
		
		return str.toString();
	}

	public static void main(String args[]){
		kochPointsManager kochTest = new kochPointsManagerImpl();
		kochTest.initLine();
		System.out.println(kochTest);
		try {
			kochTest.computeNextStep();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println(kochTest);
	}

	@Override
	public List<Point2D[]> getLstSegments() throws Exception  {
		if(lstPoints.isEmpty() || lstPoints.size()<2)
			throw new Exception("There is not enought points declared.");
		
		List<Point2D[]> newLstSegment = new LinkedList<Point2D[]>();
		Point2D A = lstPoints.get(0).clone();
		Point2D previousPoint = A;
		for(int i=1; i<lstPoints.size(); ++i){
			Point2D thisPoint = lstPoints.get(i).clone();
			Point2D[] segment = {previousPoint,thisPoint};
			newLstSegment.add(segment);
			previousPoint = thisPoint;
		}
		//Point2D[] segment = {previousPoint,A};
		//newLstSegment.add(segment);
		
		return newLstSegment;
	}

}
