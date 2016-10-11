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

public class ControlPanel extends JPanel
{
	private JButton drawButton;
	private JSpinner componentSelector;
	
	public ControlPanel(final GraphPanel graphPanel)
	{
		drawButton = new JButton("Rysuj");
		drawButton.setPreferredSize(new Dimension(100, 30));
		componentSelector = new JSpinner(new SpinnerNumberModel());
		componentSelector.setPreferredSize(new Dimension(200, 30));
		
		setLayout(new FlowLayout());
		add(componentSelector);
		add(drawButton);
		
		drawButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				graphPanel.paintGraph((int)componentSelector.getValue());
			}
		});

	}
}
