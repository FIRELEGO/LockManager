package arduinoControl;

import java.io.*;
import java.util.*;

import com.itextpdf.text.log.SysoCounter;

import gnu.io.*;

public class Open {
	static Enumeration portList;
	static CommPortIdentifier portId;
	static SerialPort serialPort;
	static OutputStream outputStream;

	public static void open(String combo) {
		combo.replaceAll("-", "");
		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {

			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

				if (portId.getName().equals("COM4")) {

					try {
						serialPort = (SerialPort)
								portId.open("SimpleWriteApp", 2000);
						try {
							Thread.sleep(7000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (PortInUseException e) {System.out.println("err");}
					try {
						outputStream = serialPort.getOutputStream();
					} catch (IOException e) {System.out.println("err1");}
					try {
						serialPort.setSerialPortParams(9600,
								SerialPort.DATABITS_8,
								SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
					} catch (UnsupportedCommOperationException e) {System.out.println("err2");}
					try {
						outputStream.write( combo.getBytes());

						outputStream.close();
						serialPort.close();

					} catch (IOException e) {System.out.println("err3");}
				}
			}
		}
	}
}