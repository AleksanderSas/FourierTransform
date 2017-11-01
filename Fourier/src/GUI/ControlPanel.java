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
	private JComboBox<ComboBoxObject> FSelector;
	private ComboBoxObject userFunction;
	public ControlPanel(final GraphPanel graphPanel, final SpectrogrmPanel spectrogrmPanel, FunctionEncapsulator functionEncapsulator)
	{
		drawButton = new JButton("Rysuj");
		cleanButton = new JButton("wyczyœæ");
		userFunction = new ComboBoxObject("w³asna", x -> 0);
		ComboBoxObject[] functions = {
				new ComboBoxObject("Prostok¹t", new Function1()), 
				new ComboBoxObject("Pi³a" ,new Function2()), 
				userFunction
			};
		FSelector = new JComboBox<ComboBoxObject>(functions);
		drawGraphCheckBox = new JCheckBox("Rysuj wykres", true);
		drawButton.setPreferredSize(new Dimension(100, 30));
		cleanButton.setPreferredSize(new Dimension(100, 30));
		componentSelector = new JSpinner(new SpinnerNumberModel());
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
				graphPanel.cleanNockPoints();
				spectrogrmPanel.clean();
				Function f = ((ComboBoxObject)FSelector.getSelectedItem()).f;
				functionEncapsulator.fourierSeries = new FourierSeries(f, 2);
				functionEncapsulator.f = f;
				boolean draw = drawGraphCheckBox.isSelected();
				graphPanel.drawFunction(draw? functionEncapsulator.f : null, functionEncapsulator.fourierSeries);
				graphPanel.paintGraph((int)componentSelector.getValue());
				spectrogrmPanel.setFourierSeries(functionEncapsulator.fourierSeries);
				spectrogrmPanel.paintGraph((int)componentSelector.getValue());
			}
		});
		
		drawGraphCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean draw = ((JCheckBox)e.getSource()).isSelected();
				graphPanel.drawFunction(draw? functionEncapsulator.f : null, functionEncapsulator.fourierSeries);
			}
		});
		
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
				userFunction.f = f;
				functionEncapsulator.fourierSeries = new FourierSeries(f, 2);
				functionEncapsulator.f = f;
				boolean draw = drawGraphCheckBox.isSelected();
				graphPanel.drawFunction(draw? functionEncapsulator.f : null, functionEncapsulator.fourierSeries);
				graphPanel.paintGraph((int)componentSelector.getValue());
				spectrogrmPanel.setFourierSeries(functionEncapsulator.fourierSeries);
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
	
	private class ComboBoxObject{
		
		public ComboBoxObject(String name, Function f){
			this.name = name;
			this.f = f;
		}
		
		private String name;
		public Function f;
		
		@Override
		public String toString(){
			return name;
		}
	}
}
