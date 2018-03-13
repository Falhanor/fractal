package fractal;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class IS_Koch extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Color RANDOMCOLOR = new Color(new Random().nextInt(0xFFFFFF));
	
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
        this.getGraphics().setColor(RANDOMCOLOR);
		
		kochPM.initSample(drawPanel.getWidth(), drawPanel.getHeight());
		try {
			Thread.sleep(800);
			for(int i=0;i<6;i++){				
				kochPM.computeNextStep();
				drawPanel.repaint();
				Thread.sleep(800);
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
	        	kochPM.GetPolygons().forEach((shape)->{	
	        		g.fillPolygon(shape);
	        		g.setXORMode(RANDOMCOLOR.brighter());
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
