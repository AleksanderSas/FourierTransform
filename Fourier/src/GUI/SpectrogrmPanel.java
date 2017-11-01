package GUI;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import FourierMath.FourierSeries;

public class SpectrogrmPanel extends JPanel{

	private FourierSeries fourierSeries = null;
	private int n = 0;
	private int max_x = 20;
	private double max_y = 0.001;
	
	public SpectrogrmPanel(FourierSeries F)
	{
		fourierSeries = F;
		scale();
	}
	
	public void setFourierSeries(FourierSeries fourierSeries)
	{
		this.fourierSeries = fourierSeries;
	}
	
	public void paintGraph(int n)
	{
		this.n = n;
		scale();
		this.repaint();
	}
	
	public void clean()
	{
		n = 0;
		repaint();
	}
	
	private void scale()
	{
		max_y = Math.abs(fourierSeries.get_a_0());
		max_x = n + 1;
		for(int i = 1; i < n; i++)
		{
			if(max_y < fourierSeries.getAmplitude(i))
				max_y = fourierSeries.getAmplitude(i);
		}
		
		max_y *= 1.2;
	}
	
	private int get_x_forComponent(int degree)
	{
		return (getWidth() - 20) / max_x * degree + 20;  
	}
	
	private void drawScale(int degree, Graphics g)
	{
		int x = get_x_forComponent(degree);
		g.drawLine(x, getHeight() - 15, x, getHeight() - 35);
		g.drawString("" + degree, x + 5, getHeight() - 10);
	}
	
	private void drawScale(Graphics g)
	{
		for(int i = 5; i <= max_x; i+= 5)
			drawScale(i, g);
	}
	
	private int getCoordinate(double y)
	{
		return getHeight() - 25 - (int)(y / max_y * (getHeight() - 25));
	}
	
	private int getPointHeigth(double y)
	{
		return (int)(y / max_y * (getHeight() - 25));
	}
	
	private void drawPoin(Graphics g, int degree, double amplitude)
	{
		int y = getCoordinate(amplitude);
		int x = get_x_forComponent(degree);
		//g.fillOval(x - 6, y - 6, 12, 12);
		int h = getPointHeigth(amplitude);
		g.fillRect(x-7, getHeight() - 25 - h, 14,  h);
		//g.drawLine(x, y-2, x, getHeight() - 25);
	}
	
	private void drawSpectrogram(Graphics g)
	{
		drawPoin(g, 0, Math.abs(fourierSeries.get_a_0()));	
		for(int i = 1; i <= n; i++)
		{
			drawPoin(g, i, fourierSeries.getAmplitude(i));
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawLine(20, 5, 20, getHeight() - 5);
		g.drawLine(20, getHeight() - 25, getWidth() - 20, getHeight() - 25);
		drawScale(g);
		g.setColor(Color.RED);
		drawSpectrogram(g);
	}

}
