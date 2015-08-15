package grow;

import javax.swing.SwingUtilities;

import jssc.SerialPortList;

public class Main {
	public static void main(String[] args){
/*		String[] ports = SerialPortList.getPortNames();
		System.out.println(ports.length);*/
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Gui();
			}
		});
	}
}
