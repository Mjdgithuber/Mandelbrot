package main;

public class ComplexNumber {
	
	private double a, b, realConst, imgConst;
	
	public ComplexNumber(double a, double b){
		update(a, b, 0, 0);
	}
	
	public ComplexNumber(double a, double b,
			double realConst, double imgConst){
		update(a, b, realConst, imgConst);
	}
	
	public ComplexNumber update(double a, double b){
		this.a = a;
		this.b = b;
		return this;
	}
	
	public ComplexNumber update(double a, double b,
			double realConst, double imgConst){
		update(a, b);
		this.realConst = realConst;
		this.imgConst = imgConst;
		return this;
	}
	
	public ComplexNumber pow(int degree){
		return pow(degree, a, b);
	}
	
	private ComplexNumber pow(int degree, double a, double b){
		double real = this.a * a - this.b * b + realConst;
		double img = this.b * a + this.a * b + imgConst;
		
		//update(real, img);
		
		if(degree == 2){
			update(real, img);
			return this;
		}else
			return pow(degree - 1, real, img);
	}
	
	public double addSquares(){
		return a*a + b*b;
	}
	
	public double realSquare(){
		return a*a;
	}
	
	public double imgSquare(){
		return b*b;
	}
	
	public double getRealPart(){
		return a + realConst;
	}
	
	public double getImaginaryPart(){
		return b + imgConst;
	}

}
