package arduinoControl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class Arduino {
	static Enumeration<?> portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;
	static OutputStream outputStream;
	static boolean noArd = false;

	public static void start() {
		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {

			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

				if (portId.getName().equals("COM4")) {

					try {
						serialPort = (SerialPort)portId.open("SimpleWriteApp", 2000);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (PortInUseException e) {
						System.out.println("err");
					}
					try {
						outputStream = serialPort.getOutputStream();
					} catch (IOException e) {
						System.out.println("err1");
					}
					try {
						serialPort.setSerialPortParams(9600,
								SerialPort.DATABITS_8,
								SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
					} catch (UnsupportedCommOperationException e) {System.out.println("err2");}
				}
			}
		}
	}

	public static void open(String combo) {
		if(!noArd) {
			combo = combo.replaceAll("-", "");

			try {
				System.out.println(combo);
				System.out.println(Arrays.toString(combo.getBytes()));
				outputStream.write(combo.getBytes());
			} catch (Exception e) {
				noArd = true;
				System.out.println("Arduino error");
			}	
		}
	}

	public static void close() {
		if(!noArd) {
			try {
				outputStream.close();
				serialPort.close();
			} catch (Exception e) {
				System.out.println("Arduino error");
			}
		}
	}
}