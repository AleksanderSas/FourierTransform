import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {
	
	private static int width = 1200;
	private static int height = 700;
	private static GraphPanel graphPanel = null;
	public static void main(String args[])
	{	
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setTitle("Transformata Fouriera");
		
		Function f = new Function1();
		FourierSeries F = new FourierSeries(f, 50, 2.0);
		//FourierSeries F = new FourierSeries(x -> Math.cos(x), 5, Math.PI * 2);
		
		graphPanel = new GraphPanel(F, f);
		frame.setLayout(new BorderLayout());
		frame.add(graphPanel, BorderLayout.CENTER);
		frame.add(new ControlPanel(graphPanel), BorderLayout.NORTH);
		frame.setVisible(true);
	}
}
