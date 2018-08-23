package main;

public class ComplexNumber {
	
	private double a, b;
	
	public ComplexNumber(double a, double b){
		this.a = a;
		this.b = b;
	}
	
	public double getRealPart(){
		return a;
	}
	
	public double getImaginaryPart(){
		return b;
	}

}
