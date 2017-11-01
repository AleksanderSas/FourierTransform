package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

import FourierMath.FourierSeries;
import FourierMath.Function;
import FourierMath.Function1;
import FourierMath.FunctionEncapsulator;

public class Main {
	
	private static int width = 1200;
	private static int height = 700;
	private static GraphPanel graphPanel = null;
	private static SpectrogrmPanel spectrogrmPanel = null;
	public static double T = 2;
	public static void main(String args[])
	{	
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setTitle("Transformata Fouriera");
		
		Function f = new Function1();
		FourierSeries F = new FourierSeries(f, T);
		
		graphPanel = new GraphPanel(F, f, T);
		spectrogrmPanel = new SpectrogrmPanel(F);
		spectrogrmPanel.setPreferredSize(new Dimension(graphPanel.getWidth(), 150));
		frame.setLayout(new BorderLayout());
		frame.add(graphPanel, BorderLayout.CENTER);
		FunctionEncapsulator functionEncapsulator = new FunctionEncapsulator(F, f);
		frame.add(new ControlPanel(graphPanel, spectrogrmPanel, functionEncapsulator), BorderLayout.NORTH);
		frame.add(spectrogrmPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}
