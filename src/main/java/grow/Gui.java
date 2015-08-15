package grow;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Gui {
	
	private JComboBox<String> comPortsBox;
	
	public Gui() {
		this.initGui();
	}
	
	public void initGui(){
		
		/**
		 * FIRST BLOCK
		 * COM select input & label 
		 */
		
		JPanel comSelectPanel = new JPanel();
		comSelectPanel.setLayout(new BoxLayout(comSelectPanel, BoxLayout.X_AXIS));
		comSelectPanel.setMaximumSize(new Dimension(300, 45));
		comSelectPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel comLabel = new JLabel("Порт: ");
		comLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		comSelectPanel.add(comLabel);
		
		this.comPortsBox = new JComboBox<String>(this.getPorts());  // ports list
		comPortsBox.setBorder(new EmptyBorder(0, 0, 0, 10));
		comSelectPanel.add(comPortsBox);
		
		JButton updateButton = new JButton("Обновить");
		// Update ports list event
		updateButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				comPortsBox.removeAllItems();
				String[] newPorts = getPorts();
				for(int i = 0; i < newPorts.length; i++){
					comPortsBox.addItem(newPorts[i]);
				}
			}
		});

		comSelectPanel.add(updateButton);
		
		/**
		 * SECOND BLOCK
		 * CURRENT TIME SETTINGS  
		 */
		JPanel currentTimeLabelBox = this.getTwoInputedPanel("Установка времени системы", "Часы", "Минуты");
		
		
		/**
		 * MAIN PANEL
		 */
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(comSelectPanel);
		panel.add(currentTimeLabelBox);
	
		
		JFrame frame = new JFrame("Grow GUI");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(300, 400));
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public String[] getPorts(){
		String[] ports = {"---", "COM1", "COM2"};
		return ports;
	}
	
	public JPanel getTwoInputedPanel(String commonTitle, String firstTitle, String secondTitle){
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		labelPanel.setMaximumSize(new Dimension(30, 80));
		labelPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		labelPanel.add(new JLabel("<html>" + commonTitle + "</html>"));
		
		JPanel inputsPanel = new JPanel();
		inputsPanel.setMaximumSize(new Dimension(150, 85));
		inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.Y_AXIS));
		inputsPanel.add(new JLabel(firstTitle));
		inputsPanel.add(new JFormattedTextField());
		inputsPanel.add(new JLabel(secondTitle));
		inputsPanel.add(new JFormattedTextField());
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.add(new JButton("Отправить"));
		buttonsPanel.add(new JButton("Отправить"));
		
		/*
		 * Common panel
		 */
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setMaximumSize(new Dimension(300, 85));
		panel.add(labelPanel);
		panel.add(inputsPanel);
		panel.add(buttonsPanel);
		return panel;
	}

}
