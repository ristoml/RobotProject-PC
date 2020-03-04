package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import io.DataIn;
import view.IRobotUI;
import utils.Constants;

public class RobotController implements IRobotController {

    private IRobotUI ui;
    private Socket socket;
    private DataIn in;
    private DataOutputStream out;

    public RobotController(IRobotUI ui) {
	this.ui = ui;
	this.socket = null;
	this.in = null;
	this.out = null;
    }

    @Override
    public boolean connect(String host, int port) {
	if (isConnected())
	    return false;

	try {
	    socket = new Socket(host, port);
	    out = new DataOutputStream(socket.getOutputStream());
	    DataIn in = new DataIn(socket, ui);

	    in.start();
	    return true;
	} catch (IOException ex) {
	    ui.setErrorMessage("Connection failed.");
	    socket = null;
	    return false;
	} catch (Exception ex) {
	    ui.setErrorMessage(ex.getMessage());
	    socket = null;
	    return false;
	}
    }

    @Override
    public boolean connect(String host, int port, int timeout) {
	if (isConnected())
	    return false;

	try {
	    socket = new Socket();
	    socket.connect(new InetSocketAddress(host, port), timeout); 
	    out = new DataOutputStream(socket.getOutputStream());
	    DataIn in = new DataIn(socket, ui);

	    in.start();
	    return true;
	} catch (IOException ex) {
	    ui.setErrorMessage("Connection failed.");
	    socket = null;
	    return false;
	} catch (Exception ex) {
	    ui.setErrorMessage(ex.getMessage());
	    socket = null;
	    return false;
	}
    }

    @Override
    public void disconnect() {
	if (!isConnected())
	    return;

	try {
	    socket.close();
	} catch (IOException ex) {
	    ui.setErrorMessage(ex.getMessage());
	}
	try {
	    in.exit();
	} catch (IOException ex) {
	    ui.setErrorMessage(ex.getMessage());
	} catch (Exception ex) {
	}
	socket = null;
    }

    @Override
    public boolean isConnected() {
	return socket != null;
    }

    @Override
    public void moveRobot(int direction) {
	if (!isConnected())
	    return;

	try {
	    out.writeInt(direction);
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	    ui.setErrorMessage(ex.getMessage());
	}
    }


    @Override
    public void stopRobot() {
	if (!isConnected())
	    return;

	try {
	    out.writeInt(Constants.STOP);
	} catch (IOException ex) {
	    ui.setErrorMessage(ex.getMessage());
	}
    }

}
