package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FractalManager extends JPanel {
	// Constant screen dimensions
	// public final int SCREEN_WIDTH = 800;
	// public final int SCREEN_HEIGHT = 600;

	// Fractal image
	public BufferedImage fractal;

	// Number of max iterations
	public int maxIter = 500;

	// Zoom factor
	public double zoom = 150;
	
	// Color scheme variables
	private int redShift, greenShift, blueShift;

	// Coordinates
	public double xPos, yPos;

	// Algorithm variables
	//public double zx, zy, cX, cY, tmp;
	
	// Real and imaginary part of the constant c, determinate shape of the Julia Set
	double realConst, imgConst;
	
	// Degree of the Julia Set
	private int degree = 2;
	
	// Just for reference
	public static final double STARTING_IMG_CONSTANT = .8, STARTING_REAL_CONSTANT = -.1;

	// Dictates what fractal is to be drawn
	private int currentFractal;
	public static final int MANDELBROT_SET = 0, JULIA_SET = 1;

	public FractalManager() {
		setLayout(new BorderLayout());
		setSize(800, 600);

		// starts of the Mandelbrot set
		currentFractal = MANDELBROT_SET;
		
		// presets color scheme
		redShift = 2;
		greenShift = 4;
		blueShift = 8;
		
		// set the constants
		realConst = STARTING_REAL_CONSTANT;// -0.7
		imgConst = STARTING_IMG_CONSTANT;// 27015

		// Compute the fractal to start
		// ComputeJuliaSetSquared();
		this.setFocusable(true);
		computeCurrentFractal();
	}

	public void computeCurrentFractal() {
		switch (currentFractal) {
			case (MANDELBROT_SET): {
				computeMandelbrotSet();
				break;
			}
			case (JULIA_SET): {
				computeJuliaSet();
				break;
			}
		}
	}
	
	public void changeFractal(int fractal){
		currentFractal = fractal;
		computeCurrentFractal();
	}
	
	public boolean changeRealConstant(double x){
		if(!(x <= 2d && x >= -2d)) 
			return false;
		realConst = x;
		
		computeCurrentFractal();
		return true;
	}
	
	public boolean changeImaginaryConstant(double x){
		if(!(x <= 2d && x >= -2d)) 
			return false;
		imgConst = x;
		
		computeCurrentFractal();
		return true;
	}
	
	public void zoomOut(){
		zoom = 150; // starting zoom value
		xPos = 0d;
		yPos = 0d;
	}
	
	private int calculateIters(ComplexNumber z, int degree){
		int iter;
		
		// escape algor
		// if zx * zx + zy * zy < 4 then you escape for loop
		// otherwise iter will hit max iter
		for (iter = 0; iter < maxIter && z.addSquares() < 4; iter++)
			z.pow(degree);
		
		return iter;
	}

	private void computeMandelbrotSet() {
		final int SCREEN_WIDTH = getWidth();
		final int SCREEN_HEIGHT = getHeight();
		fractal = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		
		double max = 0;
		double min = 0;
		for (int y = 0; y < fractal.getHeight(); y++) {
			for (int x = 0; x < fractal.getWidth(); x++) {
				//ComplexNumber z = new ComplexNumber(0, 0);
				
				double zReal = xPos / SCREEN_WIDTH + (x - (fractal.getWidth() >> 1))
						/ zoom;
				double zImg = yPos / SCREEN_HEIGHT + (y - (fractal.getHeight() >> 1))
						/ zoom;
				if(zReal<min) min = zReal;
				if(zReal>max) max = zReal;
				
				//System.out.println(zReal);
				//ComplexNumber zConst = new ComplexNumber(zReal, zImg);
				ComplexNumber z = new ComplexNumber(0, 0, zReal, zImg);
				
				// System.out.println(yPos / SCREEN_HEIGHT);
				int iter = calculateIters(z, 2);

				// If the point is in the set. To black color
				if (iter == maxIter) {
					Color color = new Color(0, 0, 0);
					fractal.setRGB(x, y, color.getRGB());
				}
				// If the point is not in the set. Make it colored
				else {
					fractal.setRGB(x, y, getColor(z, iter).getRGB());
				}
			}
		}
		System.out.println("Max: "+max+" Min: "+min);
		repaint();
	}

	private void computeJuliaSet() {
		final int SCREEN_WIDTH = getWidth();
		final int SCREEN_HEIGHT = getHeight();

		double realUnit, imgUnit;

		// creates empty buffered image to house fractal
		fractal = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);

		// goes through every pixel on the screen
		for (int y = 0; y < fractal.getHeight(); y++) {
			for (int x = 0; x < fractal.getWidth(); x++) {
				realUnit = xPos / SCREEN_WIDTH
						+ (x - (fractal.getWidth() >> 1)) / zoom;
				imgUnit = yPos / SCREEN_HEIGHT
						+ (y - (fractal.getHeight() >> 1)) / zoom;

				// determines escape velocity
				ComplexNumber z = new ComplexNumber(realUnit, imgUnit, realConst, imgConst);
				int iter = calculateIters(z, degree);
				
				// if point is in the set (doesn't escape) color it black
				if (iter == maxIter) {
					Color color = new Color(0, 0, 0);
					fractal.setRGB(x, y, color.getRGB());
				}

				// if the point is not in the set make it colored
				else {
					fractal.setRGB(x, y, getColor(z, iter).getRGB());
				}
			}
		}

		repaint();
	}
	
	public void changeDegree(int degree){
		if(degree <= 5 && degree >= 2)
			this.degree = degree;
	}
	
	private Color getColor(ComplexNumber z, int iter){
//		iter = iter - Math.log(Math.log((z.addSquares())))/Math.log(2);
		//System.out.println(iter);
		
		//iter = iter + 1 - Math.log(Math.log(Math.sqrt(z.addSquares())))/Math.log(2);
		//System.out.println(Math.log(Math.log(Math.sqrt(z.addSquares())))/Math.log(2));
//		double mu = iter - Math.log(z.addSquares()) / Math.log(2);
//		float sin = (float)Math.sin(mu / .1) / 2 + 0.5f;
//		float cos = (float)Math.cos(mu / .1) / 2 + 0.5f;
//		
//		return new Color(cos, cos, sin);
//		iter = iter + 1 - Math.log(Math.log(z.addSquares()))/Math.log(2);
//		return Color.getHSBColor((float)((iter/maxIter)), 255, 255);
		
		//iter = iter + 1 - Math.log(Math.log(Math.sqrt(z.addSquares())))/Math.log(2);
//		return new Color(Color.HSBtoRGB((float) (0.95f + 10 * smoother), 0.6f, 1.0f));
		
		return new Color((int)(255d * (1d*iter/maxIter)), 0, 0);
		
//		double red = iter | (iter << redShift);
//		red %= 256;
//		while (red > 255)
//			red -= 255;
//
//		double green = iter | (iter << greenShift);
//		green %= 256;
//		while (green > 255)
//			green -= 255;
//
//		double blue = iter | (iter << blueShift);
//		blue %= 256;
//		while (blue > 255)
//			blue -= 255;
//
//		return new Color((int) red, (int) green, (int) blue);
	}
	
	public void changeColorScheme(Color c){
		redShift = Integer.bitCount(c.getRed());
		greenShift = Integer.bitCount(c.getBlue());
		blueShift = Integer.bitCount(c.getGreen());
		computeCurrentFractal();
	}

	// public void ComputeFractal() {
	// fractal = new BufferedImage(getWidth(), getHeight(),
	// BufferedImage.TYPE_INT_RGB);
	//
	// for (int y = 0; y < fractal.getHeight(); y++) {
	// for (int x = 0; x < fractal.getWidth(); x++) {
	// newRe = xPos / SCREEN_WIDTH + (x - (fractal.getWidth() >> 1))
	// / zoom;
	// newIm = yPos / SCREEN_HEIGHT + (y - (fractal.getHeight() >> 1))
	// / zoom;
	// // System.out.println(yPos / SCREEN_HEIGHT);
	//
	// // escape algor
	// // if zx * zx + zy * zy < 4 then you escape for loop
	// // otherwise iter will hit max iter
	// int i;
	// for (i = 0; i < maxIter; i++) {
	// // remember value of previous iteration
	// // a = newRe;
	// // b = newIm;
	// // //the actual iteration, the real and imaginary part are
	// // calculated
	// // newRe = a*a*a*a - (6*a*a*b*b) + b*b*b*b + cRe;
	// // //newRe = oldRe * oldRe - oldIm * oldIm + cRe;
	// // newIm = (4*a*a*a*b) - (4*a*b*b*b) + cIm;
	// // //if the point is outside the circle with radius 2: stop
	// // if((newRe * newRe + newIm * newIm) > 4) break;
	//
	// a = newRe;
	// b = newIm;
	// // the actual iteration, the real and imaginary part are
	// // calculated
	// newRe = a * a - b * b + cRe;
	// newIm = 2 * a * b + cIm;
	// // if the point is outside the circle with radius 2: stop
	// if ((newRe * newRe + newIm * newIm) > 4)
	// break;
	// }
	//
	// // If the point is in the set. To black color
	// if (i == maxIter) {
	// Color color = new Color(0, 0, 0);
	// fractal.setRGB(x, y, color.getRGB());
	// }
	// // If the point is not in the set. Make it colored
	// else {
	// // gets the RGB pixels
	// double r = i | (i << 2);
	// r %= 256;
	// while (r > 255)
	// r -= 255;
	//
	// double g = i | (i << 4);
	// g %= 256;
	// while (g > 255)
	// g -= 255;
	//
	// double b = i | (i << 8);
	// b %= 256;
	// while (b > 255)
	// b -= 255;
	//
	// Color color = new Color((int) r, (int) g, (int) b);
	// fractal.setRGB(x, y, color.getRGB());
	// }
	// }
	// }
	//
	// repaint();
	// }

	public void paint(Graphics g) {
		// Draw the fractal
		g.drawImage(fractal, 0, 0, this);
	}

}