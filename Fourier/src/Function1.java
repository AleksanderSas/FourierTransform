
public class Function1 implements Function{

	@Override
	public double f(double x) {
		x += 0.3;
		if( x < 0)
			x = -x + 1;
		return ((int)x) % 2 * 2 - 1; 
	}

}
