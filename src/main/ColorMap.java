package main;

import java.awt.Color;

public class ColorMap {

	private static Color[] colorMap;
	
	static {
		colorMap = new Color[16];
		
		colorMap[0] = new Color(66, 30, 15);
		colorMap[1] = new Color(25, 7, 26);
		colorMap[2] = new Color(9, 1, 47);
		colorMap[3] = new Color(4, 4, 73);
		colorMap[4] = new Color(0, 7, 100);
		colorMap[5] = new Color(12, 44, 138);
		colorMap[6] = new Color(24, 82, 177);
		colorMap[7] = new Color(57, 125, 209);
		colorMap[8] = new Color(134, 181, 229);
		colorMap[9] = new Color(211, 236, 248);
		colorMap[10] = new Color(241, 233, 191);
		colorMap[11] = new Color(248, 201, 95);
		colorMap[12] = new Color(255, 170, 0);
		colorMap[13] = new Color(204, 128, 0);
		colorMap[14] = new Color(153, 87, 0);
		colorMap[15] = new Color(106, 52, 3);
	}
	
	private ColorMap(){}
	
	public static Color getColor(int iters, int maxIters){
		double scale = (iters * 1d)/(maxIters);
		return colorMap[(int)(scale*16)];
	}
	
}
