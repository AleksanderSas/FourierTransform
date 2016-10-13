import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	FourierSeries fourierSeries;
	Function f = null;
	double max_x = 2;
	int max_y = 2;
	int n = 0;
	ArrayList<Pair<Double>> nockPoints;
	
	public GraphPanel(FourierSeries fourierSeries, Function f, double T)
	{
		this.fourierSeries = fourierSeries;
		this.f = f;
		nockPoints = new ArrayList<>();
		addMouseListener(this);
		max_x = T;
	}
	
	private void drawComponent(Graphics g)
	{
		if(fourierSeries != null)
			drawGrapf(g, x -> fourierSeries.draw(x, n));
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
		int x = (int)(getWidth() / 2 / max_x * (n + max_x));
		g2.drawLine(x, getHeight() / 2 - 10, x, getHeight() / 2 + 10);
		g2.drawString("" + n, x,getHeight() / 2 + 30);
	}
	
	private void drawScale(Graphics2D g2)
	{
		for(int i = -(int)max_x; i <= max_x; i++)
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
		
		//draw background (axes)
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
		for(Pair<Double> p: nockPoints)
		{
			g2.fillOval(coordinateX(p.a) - 5, coordinateY(p.b) - 5, 10, 10);
			g2.fillOval(coordinateX(p.a) - 5 - getWidth() / 2, coordinateY(p.b) - 5, 10, 10);
		}
		if(f != null)
			drawGrapf(g, f);	
	}
	
	public void cleanNockPoints()
	{
		nockPoints.clear();
		f = null;
		fourierSeries = null;
		repaint();
	}
	
	public void drawFunction(Function f, FourierSeries F)
	{
		this.f = f;
		this.fourierSeries = F;
		repaint();
	}
	
	public ArrayList<Pair<Double>> getNockPoints()
	{
		return nockPoints;
	}
	
	double scaleX(int x)
	{
		return (1.0 * x / getWidth() - 0.5) * max_x * 2;
	}
	
	int coordinateX(double x)
	{
		return (int)((x / max_x + 1.0) / 2 * getWidth());
	}
	
	int coordinateY(double y)
	{
		return (int)((y / max_y + 1.0) / 2 * getHeight());
	}
	
	double scaleY(int y)
	{
		return (1.0 * y / getHeight() - 0.5) * max_y * 2;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x_int = e.getX();
		if(x_int < getWidth() / 2)
			x_int += getWidth() / 2;
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			nockPoints.add(new Pair<Double>(scaleX(x_int), scaleY(e.getY())));
		}
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			for(Pair<Double> p : nockPoints)
			{
				double x = scaleX(x_int);
				double y = scaleY(e.getY());
				double dist = (p.a - x) * (p.a - x) + (p.b - y) * (p.b - y);
				if(dist < 0.008)
				{
					nockPoints.remove(p);
					break;
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
