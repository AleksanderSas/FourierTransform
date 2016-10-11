import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	FourierSeries fourierSeries;
	Function f = null;
	int max_x = 4;
	int max_y = 2;
	int n = 0;
	
	public GraphPanel(FourierSeries fourierSeries, Function f)
	{
		this.fourierSeries = fourierSeries;
		this.f = f;
	}
	
	private double drawComponent(Pair c, double x, int i)
	{
		double T = fourierSeries.get_T();
		return c.a * Math.cos(2 * Math.PI / T * i * x) + c.b * Math.sin(2 * Math.PI / T * i * x);
	}
	
	private double drawComponent(double x)
	{
		double sum = 0;
		for(int i = 1; i <= n; i++)
			sum += drawComponent(fourierSeries.getCoefficient(i), x, i);
		return sum + fourierSeries.get_a_0() / 2;
	}
	
	private void drawComponent(Graphics g)
	{
		drawGrapf(g, x -> drawComponent(x));
	}
	
	private void drawGrapf(Graphics g, Function f)
	{
		double x = 0;
		double y_scale = getHeight()/ 4;
		double step = 2 * getWidth() / 1000;
		double x_local = -max_x;
		double y_prev = -f.f(x_local)* y_scale + getHeight() / 2;
		while(x < getWidth())
		{
			x_local = x * 2 * max_x / getWidth() - max_x;
			double y = -f.f(x_local) * y_scale + getHeight() / 2;				
			g.drawLine((int)(x - step), (int)y_prev, (int)x, (int)y);
			x += step; 
			y_prev = y;
		}
		setBackground(Color.WHITE);
	}
	
	public void paintGraph(int n)
	{
		this.n = n;
		this.repaint();
	}
	
	private void drawScale(Graphics2D g2, int n)
	{
		int x = getWidth() / 2 / max_x * (n + max_x);
		g2.drawLine(x, getHeight() / 2 - 10, x, getHeight() / 2 + 10);
		g2.drawString("" + n, x,getHeight() / 2 + 30);
	}
	
	private void drawScale(Graphics2D g2)
	{
		for(int i = -max_x; i <= max_x; i++)
		{
			if(i == 0 || i == -max_x || i == max_x)
				continue;
			drawScale(g2, i);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int width = getWidth();
		int height = getHeight();
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(10, height / 2, width - 10, height/2);
		g2.drawLine(width / 2, 10, width / 2, height - 10);
		drawScale(g2);
		
		g2.setStroke(new BasicStroke(1));
		
		drawComponent(g);
		
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(2));
		drawGrapf(g, f);	
	}
}
