package arduinoControl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JSlider;

import com.fazecast.jSerialComm.SerialPort;
import com.itextpdf.text.log.SysoCounter;

public class Test {
	public static void main(String[] args) throws InterruptedException, IOException {
		JFrame window = new JFrame();
		JSlider slider = new JSlider();
		slider.setMaximum(2);
		window.add(slider);
		window.pack();
		window.setVisible(true);

		SerialPort ports[] = SerialPort.getCommPorts();

		System.out.println("Select a port:");

		int i = 1;
		for(SerialPort temp : ports) {
			System.out.println(i++ + ". " + temp.getSystemPortName());
		}

		Scanner scan = new Scanner(System.in);
		int portNum = scan.nextInt();

		SerialPort port = ports[portNum - 1];
		if(port.openPort()) {
			System.out.println("Succesful port opening");
		} else {
			System.out.println("Port failed to open");
			System.exit(1);
		}

		port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

		Scanner data = new Scanner(port.getInputStream());
		PrintWriter print = new PrintWriter(port.getOutputStream());

//		while(true) {
//			if(scan.hasNextLine()) {
//		Thread.sleep(4000);
		System.out.println("" + 0x02);
				port.writeBytes(new byte[] {0x11}, 1);
//			}
//		}
	}
}
