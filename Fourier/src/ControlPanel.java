import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlPanel extends JPanel
{
	private JButton drawButton;
	private JSpinner componentSelector;
	private JButton cleanButton;
	
	public ControlPanel(final GraphPanel graphPanel, final SpectrogrmPanel spectrogrmPanel)
	{
		drawButton = new JButton("Rysuj");
		cleanButton = new JButton("wyczyœæ");
		drawButton.setPreferredSize(new Dimension(100, 30));
		cleanButton.setPreferredSize(new Dimension(100, 30));
		componentSelector = new JSpinner(new SpinnerNumberModel());
		componentSelector.setPreferredSize(new Dimension(200, 30));
		
		setLayout(new FlowLayout());
		add(componentSelector);
		add(drawButton);
		add(cleanButton);
		
		componentSelector.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				graphPanel.paintGraph((int)componentSelector.getValue());	
				spectrogrmPanel.paintGraph((int)componentSelector.getValue());
			}
		});
		
		drawButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Function f = new FunctionCreator(graphPanel.getNockPoints());
				FourierSeries fourierSeries = new FourierSeries(f, 2);
				graphPanel.drawFunction(f, fourierSeries);
				graphPanel.paintGraph((int)componentSelector.getValue());
				spectrogrmPanel.setFourierSeries(fourierSeries);
				spectrogrmPanel.paintGraph((int)componentSelector.getValue());
			}
		});
		
		cleanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graphPanel.cleanNockPoints();
				spectrogrmPanel.clean();
			}
		});
	}
}
