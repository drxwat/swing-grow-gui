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

import jssc.SerialPortException;

/**
 * Class that manipulating only GUI without any logic of working with SerialPort
 * abstraction
 * 
 * @author drxwat
 *
 */
public class Gui implements MessageListener{

	private JComboBox<String> comPortsBox;
	private Conector conector;
	private JLabel portStatusLabel;

	public Gui() {
		this.initGui();
	}

	public void initGui() {

		
		/*
		 * First block COM select input & label
		 */

		JPanel comSelectPanel = new JPanel();
		comSelectPanel.setLayout(new BoxLayout(comSelectPanel, BoxLayout.X_AXIS));
		comSelectPanel.setMaximumSize(new Dimension(300, 45));
		comSelectPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel comLabel = new JLabel("Порт: ");
		comLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		comSelectPanel.add(comLabel);

		this.comPortsBox = new JComboBox<String>(this.getPorts()); // ports list
		this.comPortsBox.setBorder(new EmptyBorder(0, 0, 0, 10));
		this.comPortsBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO: Creating connection to the chosen port
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				String itemValue = (String) cb.getSelectedItem();
				if (itemValue == "---" || itemValue == null) {
					conector = null;
				} else {
					try {
						// COnecting to chosen serial port
						conector = new Conector((String) cb.getSelectedItem(), Gui.this);

						JOptionPane.showMessageDialog(null, "Подключение успешно установлено", "Успех",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (SerialPortException spe) {
						JOptionPane.showMessageDialog(null, "Ошибка при подключении к порту "
								+ (String) cb.getSelectedItem() + "\n" + spe.getMessage(), "Ошибка",
								JOptionPane.ERROR_MESSAGE);
						conector = null;
						cb.setSelectedItem("---");
					}
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
				for (int i = 0; i < newPorts.length; i++) {
					comPortsBox.addItem(newPorts[i]);
				}
			}
		});

		comSelectPanel.add(updateButton);

		/*
		 * Second block Setters
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
		 * Status area
		 */

		this.portStatusLabel = new JLabel(
				"<html>Выберите порт к которому подключено устройство для отображения информации о последнем</html>");
		this.portStatusLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.portStatusLabel.setPreferredSize(new Dimension(300, 50));

		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));
		statusPanel.add(this.portStatusLabel);

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
		panel.add(statusPanel);
		/*
		 * Menu
		 */
		JMenuItem about = new JMenuItem("О программе");
		about.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Клиент для устройства RTC-Termometer\nУстанавливает значения параметров устройсва",
						"О программе", JOptionPane.INFORMATION_MESSAGE);

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
		JFrame frame = new JFrame("Grow client");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(300, 200));
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(menu);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	public String[] getPorts() {
		String[] ports = { "---", "COM1", "COM2" };
		return ports;
	}

	/**
	 * Text field component has index 1
	 * 
	 * @param labelText
	 * @param prefix
	 * @return
	 */
	public JPanel getInputPanel(String labelText) {

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
	 * TODO: Parse received 
	 */
	public void messageReceived(MessageEvent messageEvent) {
		this.portStatusLabel.setText(messageEvent.getMessage());
	}
	
	/**
	 * TODO: Move to config file
	 */
	public static class PanelsData {
		public static LinkedHashMap<String, String> panelDataMap = new LinkedHashMap<String, String>();

		static {
			panelDataMap.put("setsystime", "Время системы");
			panelDataMap.put("setontime", "Включение нагрузки");
			panelDataMap.put("setoffstime", "Выключение нагрузки");
			panelDataMap.put("settemperature", "Пороговая температура");
		}
	}

}
