package FourierMath;

public class Function2 implements Function{

	@Override
	public double f(double x) {
		if( x < 0)
			x += 2;
		return (x - ((int)(x / 1.0)) * 1.0) * 1.5 - 0.75; 
	}

}
