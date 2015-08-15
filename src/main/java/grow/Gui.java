package grow;

import java.awt.Color;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Gui {
	
	private JComboBox<String> comPortsBox;
	private JFrame frame;
	
	public Gui() {
		this.initGui();
	}
	
	public void initGui(){
		
		this.frame = new JFrame("Grow client");
		/*
		 * First block
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
		
		/*
		 * Second block
		 * Setters  
		 */
		
		JPanel sysTymePanel = this.getInputPanel("Время системы");
		JPanel onTimePanel = this.getInputPanel("Включение нагрузки");
		JPanel offTimePanel = this.getInputPanel("Выключение нагрузки");
		JPanel limitTempPanel = this.getInputPanel("Пороговая температура");
		
		JButton button = new JButton("Установить");
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(button);
		
		/*
		 * Main panel
		 */
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(comSelectPanel);
		panel.add(sysTymePanel);
		panel.add(onTimePanel);
		panel.add(offTimePanel);
		panel.add(limitTempPanel);
		panel.add(btnPanel);
	
		/*
		 * Menu
		 */
		JMenuItem about = new JMenuItem("О программе");
		about.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Клиент для устройства RTC-Termometer\nУстанавливает значения параметров устройсва");
				
			}
		});
		
        JMenu help = new JMenu("Помощь");
		help.add(about);
        
		JMenuBar menu = new JMenuBar();
		menu.setOpaque(true);
        menu.setPreferredSize(new Dimension(300, 30));
        menu.add(help);

		
        /*
         * Main frame
         */

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(300, 200));
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(menu);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public String[] getPorts(){
		String[] ports = {"---", "COM1", "COM2"};
		return ports;
	}
	
	/**
	 * Text field component has index 1
	 * @param labelText
	 * @param prefix
	 * @return
	 */
	public JPanel getInputPanel(String labelText){
		
		JLabel label = new JLabel("<html>" + labelText + "</html>");
		label.setPreferredSize(new Dimension(150, 50));
		
		JTextField textField = new JTextField();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setMaximumSize(new Dimension(250, 30));
		panel.add(label, 0);
		panel.add(textField, 1);
		return panel;
	}

}
