package fractal;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class IS_Koch extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JPanel drawPanel;
	protected Container mainPan;
	protected kochPointsManager kochPM;
	
	public IS_Koch(){
		kochPM = new kochPointsManagerImpl();
		mainPan = getContentPane();
		drawPanel = new DrawingPanel();
		mainPan.add(drawPanel);
		initialize();
	}
	
	
	private void initialize(){
		this.setTitle("Koch");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		kochPM.initLine();
		
		try {
			for(int i=0;i<7;++i){
				drawPanel.repaint();
				Thread.sleep(800);
				kochPM.computeNextStep();
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class DrawingPanel extends JPanel {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DrawingPanel() {
	        setBackground(Color.white);
	    }
		
	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        try {
				kochPM.getLstSegments().forEach(p->{
					g.drawLine((int)p[0].x(),(int)p[0].y(),(int)p[1].x(),(int)p[1].y());
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	 
	}
	
	public static void main (String[] args){
		new IS_Koch();
	}
}
