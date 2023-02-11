package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SerialPort.Port;

// More robust serial port: allows for port to be disconnected.
public class SerialPortDevice {
    private final int speed;
    private final Port port;
    private Timer resetTimer;
    private String buffer = "";
    private boolean inRead = false;

    private static final double RetryTimeout = 5.0;
    private SerialPort openedPort = null;

    public SerialPortDevice(int speed, Port port) {
        this.speed = speed;
        this.port = port;
    }

    // Checks if port is opened, or tries to connect to it.
    // Returns null if not connected.
    private SerialPort ensurePort() {
        // Is opened?
        if (openedPort != null) return openedPort;
        // Still timeout?
        if (resetTimer != null && !resetTimer.hasElapsed(RetryTimeout)) return null;
        if (resetTimer != null) {
            resetTimer.stop();
            resetTimer = null;
        }
        if (inRead) {
            resetPort();
            return null;
        }
        try {
            System.out.println("Trying to connect to serial port!!!");
            openedPort = new SerialPort(speed, port);
            // Success, reset timer.
        } catch (Throwable ex) {
            resetPort();
        }
        return openedPort;
    }

    // Resets port and sets timer.
    private void resetPort() {
        openedPort = null;
        inRead = false;
        resetTimer = new Timer();
        resetTimer.start();
    }

    // Returns read string/line, or null if nothing is read.
    public String readLine() {
        // Is port available?
        SerialPort serialPort = ensurePort();
        if (serialPort == null) return null;
        // Data available?
        try {
            inRead = true;
            if (serialPort.getBytesReceived() == 0) return null;
            buffer += serialPort.readString();
            inRead = false;
        } catch (Throwable ex) {
            resetPort();
            return null;
        }
        // Return everything before first line break, if there is one.
        int i = buffer.indexOf('\n');
        if (i < 0) return null;
        String result = buffer.substring(0, i);
        // Store tail for future.
        buffer = buffer.substring(i + 1);
        return result; 
    }
}
