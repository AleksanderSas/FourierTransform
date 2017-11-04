package FourierMath;

public class Function3 implements Function{

	@Override
	public double f(double x) {
		if( x < 0)
			x += 2;
		if(x < 1)
		{
			x = x - 0.5;
			return Math.sqrt(0.25 - x * x) * 2;			
		}else
		{
			x = x - 0.5 - 1;
			return -Math.sqrt(0.25 - x * x) * 2;
		}
	}

}
