package grow;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private Conector conector;
	
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
		this.comPortsBox.setBorder(new EmptyBorder(0, 0, 0, 10));
		this.comPortsBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO: Creating connection to the chosen port
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				String itemValue = (String)cb.getSelectedItem();
				if(itemValue == "---" || itemValue == null){
					conector = null;
				}else{
					conector = new Conector((String)cb.getSelectedItem());
				}
			}
		});
		comSelectPanel.add(this.comPortsBox);
		
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
		HashMap<String, JPanel> panelMap = new HashMap<String, JPanel>();
		Iterator<String> iterator = PanelsData.panelDataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String prefix = (String) iterator.next();
			panelMap.put(prefix, this.getInputPanel(PanelsData.panelDataMap.get(prefix)));
		}
		
	
		JButton button = new JButton("Установить");
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// Sending info to device
				System.out.println(conector);
				
			}
		});
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(button);
		
		/*
		 * Main panel
		 */
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(comSelectPanel);
		Iterator<JPanel> panelIterator = panelMap.values().iterator();
		while (panelIterator.hasNext()) {
			JPanel Inputpanel = (JPanel) panelIterator.next();
			panel.add(Inputpanel);
		}
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

		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.setMinimumSize(new Dimension(300, 200));
		this.frame.setLocationRelativeTo(null);
		this.frame.setJMenuBar(menu);
		this.frame.add(panel);
		this.frame.pack();
		this.frame.setVisible(true);
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
	
	/**
	 * TODO: Move to config file
	 */
	public static class PanelsData{
		public static LinkedHashMap<String, String> panelDataMap = new LinkedHashMap<String, String>();
		static{
			panelDataMap.put("setsystime", "Время системы");
			panelDataMap.put("setontime", "Включение нагрузки");
			panelDataMap.put("setoffstime", "Выключение нагрузки");
			panelDataMap.put("settemperature", "Пороговая температура");
		}
	}

}
