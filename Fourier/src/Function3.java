
public class Function3 implements Function{

	@Override
	public double f(double x) {
		return x - ((int)(x / 1.0)) * 1.0; 
	}

}
