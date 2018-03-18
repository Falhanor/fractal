package fractal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class IS_Koch extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static Color RANDOMCOLOR = new Color(new Random().nextInt());
	private static final int DISPLAY_DELAY = 800;
	private static boolean fillSurfacesMode = false;
	private static boolean xorMode = false;
	private static boolean stepsMode = false;
	
	protected JPanel drawPanel;
	protected kochPointsManager kochPM;
	protected int sampleId;
	
	public IS_Koch(){
		kochPM = new kochPointsManagerImpl();
		
		initialize();
	}
	
	
	private void initialize(){
		this.setTitle("Koch");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		Container mainPan = this.getContentPane();
		
		//add button
		JPanel panButtons = new JPanel();
		panButtons.setLayout(new GridLayout(6,1));
		mainPan.add(panButtons, BorderLayout.EAST);
		
		JButton btnRebuild = new JButton("Show construction");
		btnRebuild.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				drawKochCurve(sampleId, true);
			}
		});	
		panButtons.add(btnRebuild);
		JCheckBox chkFillSurfaces = new JCheckBox("Fill Surfaces");
		chkFillSurfaces.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				fillSurfacesMode = chkFillSurfaces.isSelected();
				drawPanel.repaint();
			}
		});	
		panButtons.add(chkFillSurfaces);
		JCheckBox chkXorMode = new JCheckBox("Color Layers");
		chkXorMode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				xorMode = chkXorMode.isSelected();
				drawPanel.repaint();
			}
		});	
		panButtons.add(chkXorMode);
		JButton btnChangeColor = new JButton("Change Color");
		btnChangeColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				RANDOMCOLOR = new Color(new Random().nextInt());
				drawPanel.repaint();
			}
		});	
		panButtons.add(btnChangeColor);
		JButton btnOtherSample = new JButton("Other sample");
		btnOtherSample.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				initKochCurve();
			}
		});	
		panButtons.add(btnOtherSample);
		
		drawPanel = new DrawingPanel();
		mainPan.add(drawPanel, BorderLayout.CENTER);
		this.setVisible(true);
		
		initKochCurve();
	}
	
	private void initKochCurve(){
		drawKochCurve(-1, stepsMode);
	}
	
	private void drawKochCurve(int sampleId, boolean stepsMode){
		this.sampleId = kochPM.initSample(drawPanel.getWidth(), drawPanel.getHeight(), sampleId);
		
		new Thread(){
			public void run(){
				drawPanel.repaint();
				if (stepsMode){
					try {
						Thread.sleep(DISPLAY_DELAY);
						for(int i=0;i<5;i++){				
							kochPM.computeNextStep();
							drawPanel.repaint();
							Thread.sleep(DISPLAY_DELAY);
						}
							
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						kochPM.computeXSteps(5);
						drawPanel.repaint();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private class DrawingPanel extends JPanel {
	    
		private static final long serialVersionUID = 1L;

		public DrawingPanel() {
			this.applyColor();
		}
		
		public void applyColor(){
			this.setForeground(RANDOMCOLOR);
			this.setBackground(new Color(255-RANDOMCOLOR.getRed(),255-RANDOMCOLOR.getGreen(),255-RANDOMCOLOR.getBlue()));
		}
		
	    @Override
	    public void paintComponent(Graphics g) {
	    	this.applyColor();
	    	super.paintComponent(g);
	        try {
	        	kochPM.GetPolygons().forEach((shape)->{
	        		if (fillSurfacesMode)
	        			g.fillPolygon(shape);
	        		else
	        			g.drawPolygon(shape);
	        		
	        		if(xorMode)
	        			g.setXORMode(RANDOMCOLOR.brighter());
	        	});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    @Override
	    public void setBounds(int x, int y, int w, int h){
	    	super.setBounds(x, y, w, h);
	    	drawKochCurve(sampleId, false);
	    }
	}
	
	public static void main (String[] args){
		printUsage();
		argsManager(args);
		new IS_Koch();
	}
	
	private static void printUsage() {
		System.out.println("\n              =======================================================================");
		System.out.println("              |===========================| Koch Curves |===========================|");
		System.out.println("              |=====================================================================|");
		System.out.println("              |                                                                     |");
		System.out.println("              | usage   : program surfaceMode xorMode                               |");
		System.out.println("              | default : fractal.IS_Koch                                           |");
		System.out.println("              |                                                                     |");
		System.out.println("              |=====================================================================|");
		System.out.println("              | Parameters details :                                                |");
		System.out.println("              |  >>> fill mode    : -f   -> to fill surfaces                        |");
		System.out.println("              |  >>> xor mode     : -xor -> to change color with layers             |");
		System.out.println("              |  >>> step mode    : -s   -> to show construction step by step       |");
		System.out.println("              |                                                                     |");
		System.out.println("              |=====================================================================|");
		System.out.println("              |=============================================| By : Timothée SOLLAUD |");
		System.out.println("              =======================================================================\n");
	}
	
	private static boolean argsManager(String[] args) {
		boolean argsFound = false;
		if (args.length>0){
			for(int i=0; i<args.length; ++i)
				if (args[i].compareToIgnoreCase("-f")==0){
					fillSurfacesMode = true;
					argsFound = true;
				}else if (args[i].compareToIgnoreCase("-xor")==0){
					xorMode = true;
					argsFound = true;
				}else if (args[i].compareToIgnoreCase("-s")==0){
					stepsMode = true;
					argsFound = true;
				}
		}
		return argsFound;
	}
}
