package FourierMath;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionCreator implements Function
{
	private List<Pair<Double>> nockPOints;
	double T = 2;
	
	public FunctionCreator(List<Pair<Double>> nockPOints)
	{
		this.nockPOints = nockPOints;//.stream().sorted((x , y) -> x.a.compareTo(y.a)).collect(Collectors.toList());
	}
	
	public void updateNockPoints(){
		nockPOints.sort((x , y) -> x.a.compareTo(y.a));
	}
	
	private double approx(double x, Pair<Double> n1, Pair<Double> n2)
	{
		double d = n2.a - n1.a;
		return -((n2.a - x) / d * n1.b + (x -n1.a) / d * n2.b);
	}

	@Override
	public double f(double x) {
		
		if(nockPOints.size() == 0)
			return 0;
		while(x > T)
			x -= T;
		while(x < 0)
			x += T;
		
		if(x < nockPOints.get(0).a)
		{
			Pair<Double> lastNock = nockPOints.get(nockPOints.size() - 1);
			Pair<Double> n1 = new Pair<Double>(lastNock.a - T, lastNock.b);
			return approx(x, n1, nockPOints.get(0));
		}
		else
		{
			for(int i = 1; i < nockPOints.size(); i++)
			{
				if(x < nockPOints.get(i).a)
				{
					return approx(x, nockPOints.get(i-1), nockPOints.get(i));
				}
			}
		}
		Pair<Double> first = nockPOints.get(0);
		Pair<Double> n2 = new Pair<Double>(T + first.a, first.b);
		return approx(x, nockPOints.get(nockPOints.size() - 1), n2);
	}
}
