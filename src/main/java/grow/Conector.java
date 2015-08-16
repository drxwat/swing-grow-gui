package grow;

import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * Class that encapsulates work with SerialPort through JSSC
 * 
 * @author drxwat
 *
 */
public class Conector {

	private SerialPort port;
	private Gui gui;
	private ArrayList<MessageListener> messageListeners = new ArrayList<MessageListener>();

	public Conector(String portName, Gui gui) throws SerialPortException {
		this.gui = gui;
		this.port = new SerialPort(portName);
		this.port.openPort();
		this.port.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);

		this.addMessageListner(this.gui);

		this.port.addEventListener(new SerialPortEventListener() {

			public void serialEvent(SerialPortEvent serialPortEvent) {

				if (serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0) {
					try {
						String receivedData = port.readString(serialPortEvent.getEventValue());
						Conector.this.fireMessage(receivedData);
						System.out.println(receivedData);
					} catch (SerialPortException e) {
						System.out.println("Error in recieving data from com-port " + e);
					}
				}

			}
		}, SerialPort.MASK_RXCHAR);

	}

	public void sendData(String data) throws SerialPortException {
		this.port.writeString(data);
	}

	public ArrayList<MessageListener> getMessageListeners() {
		return messageListeners;
	}

	public void addMessageListner(MessageListener messageListener) {
		this.messageListeners.add(messageListener);
	}

	public void removeMessageListener(MessageListener messageListener) {
		this.messageListeners.remove(messageListener);
	}

	public void fireMessage(String message) {
		MessageEvent messageEvent = new MessageEvent(this, message);
		for (MessageListener listener : this.getMessageListeners()) {
			listener.messageReceived(messageEvent);
		}
	}

}
