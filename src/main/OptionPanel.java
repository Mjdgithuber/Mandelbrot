package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptionPanel extends JPanel {
	
	public OptionPanel(GUIFrame frame, FractalManager fractal){
		
		setBorder(BorderFactory.createTitledBorder("Selector"));
		
		String[] setStrings = { "Mandelbrot Set", "Julia Set" };
		JComboBox<String> setChooser = new JComboBox<String>(setStrings);
		setChooser.setPreferredSize(new Dimension(150, 30));

		add(setChooser);
		
		add(new JLabel("Precision"));
		JSlider slideOpt = new JSlider(JSlider.HORIZONTAL, 50, 750, 500);
		slideOpt.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				fractal.maxIter = slideOpt.getValue();
				fractal.computeCurrentFractal();
			}
		});
		
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer( 100 ), new JLabel("Fast") );
		labelTable.put( new Integer( 650 ), new JLabel("Slow") );
		slideOpt.setLabelTable( labelTable );
		slideOpt.setMajorTickSpacing(100);
		slideOpt.setMinorTickSpacing(25);
		slideOpt.setPaintTicks(true);
		
		slideOpt.setPaintLabels(true);
		
		add(slideOpt);
		
		JLabel realConstLabel;
		realConstLabel = new JLabel("Real Constant          ");
		add(realConstLabel);
		SpinnerNumberModel realSpinnerModel = new SpinnerNumberModel(FractalManager.STARTING_REAL_CONSTANT, -2, 2, .1);
		JSpinner realSpinner = new JSpinner(realSpinnerModel);
		realSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				fractal.changeRealConstant((double) realSpinner.getValue());
			}
		});
		realSpinner.setPreferredSize(new Dimension(70,20));
		add(realSpinner);
		realConstLabel.setVisible(false);
		realSpinner.setVisible(false);
		
		JLabel imgConstantLabel;
		imgConstantLabel = new JLabel("Imaginary Constant");
		add(imgConstantLabel);
		SpinnerNumberModel imgSpinnerModel = new SpinnerNumberModel(FractalManager.STARTING_IMG_CONSTANT, -2, 2, .1);
		JSpinner imgSpinner = new JSpinner(imgSpinnerModel);
		imgSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				fractal.changeImaginaryConstant((double) imgSpinner.getValue());
			}
		});
		imgSpinner.setPreferredSize(new Dimension(70,20));
		add(imgSpinner);
		imgConstantLabel.setVisible(false);
		imgSpinner.setVisible(false);
		
		JLabel setDegLabel = new JLabel("Degree                        ");
		add(setDegLabel);
		setDegLabel.setVisible(false);
		
		String[] setDegStrings = { "2", "3", "4", "5" };
		JComboBox<String> setDegChooser = new JComboBox<String>(setDegStrings);
		setDegChooser.setPreferredSize(new Dimension(65, 20));
		setDegChooser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				fractal.changeDegree(setDegChooser.getSelectedIndex()+2);
				fractal.computeCurrentFractal();
			}
		});
		add(setDegChooser);
		setDegChooser.setVisible(false);
		
		JButton zoomButton = new JButton("Zoom Out");
		zoomButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.zoomOut();
				fractal.zoomOut();
				fractal.changeFractal(setChooser.getSelectedIndex());
			}
		});
		add(zoomButton);
		
		JButton colorButton = new JButton("Change Color Scheme");
		colorButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				chooser.setPreviewPanel(new JPanel());
				ActionListener okListener = new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						fractal.changeColorScheme(chooser.getColor());
					}
				};
				JDialog d = JColorChooser.createDialog(null,"",true,chooser,okListener,null);
				d.setVisible(true);
			}
		});
		add(colorButton);
		
		setChooser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = setChooser.getSelectedIndex();
				
				boolean show;
				if(index == FractalManager.MANDELBROT_SET)
					show = false;
				else
					show = true;
	
				realConstLabel.setVisible(show);
				realSpinner.setVisible(show);
				imgConstantLabel.setVisible(show);
				imgSpinner.setVisible(show);
				setDegChooser.setVisible(show);
				setDegLabel.setVisible(show);
				
				frame.zoomOut();
				fractal.zoomOut();
				fractal.changeFractal(index);
			}
		});
		
		setFocusable(false);
	}
}
