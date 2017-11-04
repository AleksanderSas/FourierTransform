package FourierMath;

public class Function4 implements Function{

	@Override
	public double f(double x) {
		if( x < 0)
			x += 2;
		if(x > 1)
			x = 2 - x;
		return Math.sqrt(1 - x * x) * 3 - 1.5;
	}

}
