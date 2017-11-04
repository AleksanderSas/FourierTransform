package FourierMath;

import GUI.Main;

public class FunctionEncapsulator{
	
	
	public FunctionEncapsulator(String name, Function f, boolean drawFunction, boolean drawNockPoints) {
		super();
		this.fourierSeries = new FourierSeries(f, Main.T);;
		this.f = f;
		this.drawFunction = drawFunction;
		this.drawNockPoints = drawNockPoints;
		this.name = name;
	}
	public void setFunction(Function f)
	{
		this.f = f;
		fourierSeries = new FourierSeries(f, Main.T);
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public String name;
	public FourierSeries fourierSeries;
	public Function f;
	public boolean drawFunction;
	public boolean drawNockPoints;
}
