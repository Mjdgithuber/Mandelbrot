package main;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class GUIFrame extends JFrame implements KeyListener, MouseListener {

	// starting size of screen
	public static final int INITIAL_WIDTH = 1000, INITIAL_HEIGHT = 625;

	// sub JPanels of the parent panel (this class)
	public FractalManager fractal = new FractalManager();
	public OptionPanel optionPanel;

	// how many pixels to move by
	private double moveBy = 50;

	/**
	 * Creates the frame for the application and sets up the necessary classes
	 * 
	 * @param title
	 */
	public GUIFrame(String title) {
		super(title);
		setMinimumSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));

		// sets the layout manager
		setLayout(new BorderLayout());

		// creates the panels
		optionPanel = new OptionPanel(this, fractal);
		optionPanel.setPreferredSize(new Dimension(200, 500));

		// gets the parent container and adds the components to it
		Container c = getContentPane();
		c.add(optionPanel, BorderLayout.WEST);
		c.add(fractal, BorderLayout.CENTER);

		addKeyListener(this);
		this.addMouseListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// c.add(tp, BorderLayout.NORTH);
		this.setVisible(true);
		this.requestFocus();
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				fractal.computeCurrentFractal();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void main(String[] args0) {
		new GUIFrame("Fractal Explorer Jones Klarlund Dennerlein");
	}
	
	public void zoomOut(){
		moveBy = 50; // starting amount
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent e) {
		fractal.requestFocus();
		// double mov = (double)optionPanel.spinner.getValue();
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// man.zoom += (double)optionPanel.xSpinner.getValue();
			fractal.zoom *= 1.2;// 5
			moveBy *= (5d / 6d);
			fractal.computeCurrentFractal();
		} else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			fractal.zoom /= 1.2;// 5
			moveBy /= (5d / 6d);
			fractal.computeCurrentFractal();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			fractal.xPos += moveBy;
			fractal.computeCurrentFractal();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			fractal.xPos -= moveBy;
			fractal.computeCurrentFractal();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			fractal.yPos += moveBy;
			fractal.computeCurrentFractal();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			fractal.yPos -= moveBy;
			fractal.computeCurrentFractal();
		}
		requestFocus();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {
		requestFocus();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
