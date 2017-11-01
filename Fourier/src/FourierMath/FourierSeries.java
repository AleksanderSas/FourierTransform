package FourierMath;
import java.util.ArrayList;

public class FourierSeries 
{
	private double a_0;
	private ArrayList<Pair<Double>> coefficients;
	private int n = 0;
	private double T;
	private Function f;
	
	public FourierSeries(Function f, double T)
	{
		this.T = T;
		this.f = f;
		coefficients = new ArrayList<>();
		a_0 = integrate2(f, -T/2, T/2) * 2 / T;
	}
	
	private Pair<Double> computeCoefficient(final int n)
	{
		double a = integrate2(x -> f.f(x) * Math.cos(2 * Math.PI / T * n * x), -T/2, T/2) * 2 / T;
		double b = integrate2(x -> f.f(x) * Math.sin(2 * Math.PI / T * n * x), -T/2, T/2) * 2 / T;
		return new Pair<Double>(a, b);
	}
	
	private double integrate(Function f, double from, double to)
	{
		double sum = 0;
		double step = (to - from) / 10000;
		while(from < to)
		{
			sum += f.f(from) * step;
			from += step;
		}
		return sum;
	}
	
	//Stimpson method
	private double integrate2(Function f, double from, double to)
	{
		double sum = 0;
		int N = 10000;
		double step = (to - from) / N;
		sum += f.f(from);
		sum += f.f(to);
		from += step;
		while(from < to)
		{
			sum += f.f(from) * 4;
			from += step;
			sum += f.f(from) * 2;
			from += step;
		}
		return sum * step / 3;
	}
	
	public Pair<Double> getCoefficient(int _n)
	{
		for(; this.n < _n; this.n++)
			coefficients.add(computeCoefficient(this.n+1));
		return coefficients.get(_n-1);
	}
	
	public double getAmplitude(int _n)
	{
		for(; this.n < _n; this.n++)
			coefficients.add(computeCoefficient(this.n+1));
		Pair<Double> coef = coefficients.get(_n-1);
		return Math.sqrt(coef.a * coef.a + coef.b * coef.b);
	}
	
	public double get_a_0()
	{
		return a_0;
	}
	
	public double get_T()
	{
		return T;
	}
	
	private double drawComponent(Pair<Double> c, double x, int i)
	{
		return c.a * Math.cos(2 * Math.PI / T * i * x) + c.b * Math.sin(2 * Math.PI / T * i * x);
	}
	
	public double draw(double x, int degree)
	{
		double sum = 0;
		for(int i = 1; i <= degree; i++)
			sum += drawComponent(getCoefficient(i), x, i);
		return sum + a_0 / 2;
	}
}
