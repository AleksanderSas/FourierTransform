package GUI;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import FourierMath.FourierSeries;
import FourierMath.Function;
import FourierMath.Pair;
public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	FourierSeries fourierSeries;
	Function f = null;
	double max_x = 2;
	int max_y = 2;
	int n = 0;
	ArrayList<Pair<Double>> nockPoints;
	private int selectedNockPointIdx = -1;
	private double selectionX = 0.0;
	private double selectionY = 0.0;
	private double selectionXbase = 0.0;
	private double selectionYbase = 0.0;
	
	public GraphPanel(FourierSeries fourierSeries, Function f, double T)
	{
		this.fourierSeries = fourierSeries;
		this.f = f;
		nockPoints = new ArrayList<>();
		addMouseListener(this);
		addMouseMotionListener(this);
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
		double step = 2 * getWidth() / 2000;
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
		g2.drawString("" + n, x + 10,getHeight() / 2 + 30);
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
		int width = getWidth();
		int height = getHeight();
		
		Graphics2D g2d = (Graphics2D) g.create();

        //draw dashed lines
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.setColor(new Color(0.1f, 0.1f, 0.0f, 0.2f));
        g2d.drawLine(width / 4, 10, width / 4, height - 10);
        g2d.drawLine(width / 4 * 3, 10, width / 4 * 3, height - 10);
        g2d.drawLine(width / 8, 10, width / 8, height - 10);
        g2d.drawLine(width / 8 * 7, 10, width / 8 * 7, height - 10);
        g2d.drawLine(width / 8 * 3, 10, width / 8 * 3, height - 10);
        g2d.drawLine(width / 8 * 5, 10, width / 8 * 5, height - 10);
        
        g2d.drawLine(10, height / 4, width - 10, height / 4);
        g2d.drawLine(10, height / 4 * 3, width - 10, height / 4 * 3);
        //gets rid of the copy
        g2d.dispose();
		
		//draw background (axes)
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(10, height / 2, width - 10, height/2);
		g2.drawLine(width / 2, 10, width / 2, height - 10);
		drawScale(g2);
		
		//draw Fourier function
		g2.setStroke(new BasicStroke(1));
		drawComponent(g);
		
		//draw knock points
		g2.setStroke(new BasicStroke(1));		
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(2));
		for(Pair<Double> p: nockPoints)
		{
			g2.fillOval(coordinateX(p.a) - 5, coordinateY(p.b) - 5, 10, 10);
			g2.fillOval(coordinateX(p.a) - 5 - getWidth() / 2, coordinateY(p.b) - 5, 10, 10);
		}
		//draw base function
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
		if(x < getWidth() / 2)
			x += getWidth() / 2;
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
	
	private int getKnockPointIdx(double x, double y, boolean createIfnotExists)
	{
		for(int i = 0; i < nockPoints.size(); i++)
		{
			Pair<Double> p = nockPoints.get(i);
			double dist = (p.a - x) * (p.a - x) + (p.b - y) * (p.b - y);
			if(dist < 0.008)
			{
				return i;
			}
		}
		if(createIfnotExists)
		{
			while(x < 0){
				x += 2;
			}
			nockPoints.add(new Pair<Double>(x, y));
			return nockPoints.size() - 1;
		}
		return -1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x_int = e.getX();
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			selectionX = scaleX(x_int);
			selectionY = scaleY(e.getY());
			selectedNockPointIdx = getKnockPointIdx(selectionX, selectionY, true);
			selectionXbase = nockPoints.get(selectedNockPointIdx).a;
			selectionYbase = nockPoints.get(selectedNockPointIdx).b;
		}
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			double x = scaleX(x_int);
			double y = scaleY(e.getY());
			int idx = -1;
			if((idx = getKnockPointIdx(x, y, false)) >= 0)
			{
				nockPoints.remove(idx);					
			}
		}
		repaint();
		System.out.println("HHHH");
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		selectedNockPointIdx = -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(selectedNockPointIdx != -1)
		{
			double x = scaleX(e.getX());
			double y = scaleY(e.getY());
			nockPoints.get(selectedNockPointIdx).a = selectionXbase + x - selectionX;
			nockPoints.get(selectedNockPointIdx).b = selectionYbase + y - selectionY;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
