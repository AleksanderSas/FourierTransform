package GUI;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import FourierMath.FourierSeries;
import FourierMath.Function;
import FourierMath.Function1;
import FourierMath.Function2;
import FourierMath.FunctionCreator;
import FourierMath.FunctionEncapsulator;

public class ControlPanel extends JPanel
{
	private JButton drawButton;
	private JSpinner componentSelector;
	private JButton cleanButton;
	private JCheckBox drawGraphCheckBox;
	private JComboBox<FunctionEncapsulator> FSelector;
	private FunctionEncapsulator userFunction;
	FunctionEncapsulator currentFunction;
	public ControlPanel(final GraphPanel graphPanel, final SpectrogrmPanel spectrogrmPanel, FunctionEncapsulator functionEncapsulator)
	{
		drawButton = new JButton("Rysuj");
		cleanButton = new JButton("wyczyœæ");
		userFunction = new FunctionEncapsulator("w³asna", graphPanel.functionCreator, true, true);
		FunctionEncapsulator[] functions = {
				new FunctionEncapsulator("Prostok¹t", new Function1(), true, false), 
				new FunctionEncapsulator("Pi³a" ,new Function2(), true, false), 
				userFunction
			};
		currentFunction = functionEncapsulator;
		FSelector = new JComboBox<FunctionEncapsulator>(functions);
		drawGraphCheckBox = new JCheckBox("Rysuj wykres", true);
		drawButton.setPreferredSize(new Dimension(100, 30));
		cleanButton.setPreferredSize(new Dimension(100, 30));
		componentSelector = new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
		componentSelector.setPreferredSize(new Dimension(200, 30));
		
		setLayout(new FlowLayout());
		add(FSelector);
		add(componentSelector);
		add(drawButton);
		add(cleanButton);
		add(drawGraphCheckBox);
		
		FSelector.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFunction(spectrogrmPanel, graphPanel);
			}
		});
		
		drawGraphCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentFunction.drawFunction = ((JCheckBox)e.getSource()).isSelected();
				graphPanel.drawFunction(currentFunction, (int)componentSelector.getValue());
			}
		});
		
		componentSelector.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setFunction(spectrogrmPanel, graphPanel);
				//graphPanel.paintGraph((int)componentSelector.getValue());	
				//spectrogrmPanel.paintGraph((int)componentSelector.getValue());
			}
		});
		
		drawButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentFunction.fourierSeries = new FourierSeries(currentFunction.f, 2);
				graphPanel.drawFunction(currentFunction, (int)componentSelector.getValue());
				spectrogrmPanel.setFourierSeries(currentFunction.fourierSeries);
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
	
	private void setFunction(SpectrogrmPanel spectrogrmPanel, GraphPanel graphPanel)
	{
		spectrogrmPanel.clean();
		currentFunction = ((FunctionEncapsulator)FSelector.getSelectedItem());
		currentFunction.drawFunction = drawGraphCheckBox.isSelected();
		graphPanel.drawFunction(currentFunction, (int)componentSelector.getValue());
		spectrogrmPanel.setFourierSeries(currentFunction.fourierSeries);
		spectrogrmPanel.paintGraph((int)componentSelector.getValue());
	}
}
