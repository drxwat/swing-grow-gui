package grow;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Conector {
	
	private SerialPort port;
	
	public Conector(String portName) throws SerialPortException{
		this.port = new SerialPort(portName);
		this.port.openPort();
		this.port.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		
		this.port.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
		
	}
	
	public void sendData(String data) throws SerialPortException{
		this.port.writeString(data);
	}
	
	private class PortReader implements SerialPortEventListener{

		public void serialEvent(SerialPortEvent serialPortEvent) {
			// TODO Auto-generated method stub
			if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0){
				try{
					String receivedData = port.readString(serialPortEvent.getEventValue());
					// TODO: fire event
					System.out.println(receivedData);
				}catch(SerialPortException e){
					System.out.println("Error in recieving data from com-port " + e);
				}
			}
		}
		
	}
}
