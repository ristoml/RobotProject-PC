package io;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import utils.OpenCVutils;
import view.IRobotUI;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.image.Image;
import lejos.robotics.navigation.Pose;

/**
 * A thread which continously listens to a socket input stream.
 * Reads {@link Pose} and video frames (as a byte array) sent by a the robot.
 *
 */
public class DataIn extends Thread {

    /*
     * Integer expected from robot, before the robot sends its position.
     */
    public final int POS = 1;
    /*
     * Integer expected from robot, before it sends a video frame.
     */
    public final int PIC = 2;

    private volatile boolean done;
    private DataInputStream in;
    private IRobotUI ui;

    /**
     * Constructor. Takes a socket and {@link IRobotUI} as arguments.<br>
     * Uses the socket for input stream, and {@link IRobotUI} to update video and robot's position to the map.
     * @param socket
     * @param ui
     * @throws Exception
     */
    public DataIn(Socket socket, IRobotUI ui) throws Exception {
	this.in = new DataInputStream(socket.getInputStream());
	this.ui = ui;
	this.done = false;
    }

    @Override
    public void run() {
	while (!done) {
	    try {
		int code = in.readInt();

		switch (code) {
		    case PIC:
			int len = in.readInt();
			byte[] imageBytes = new byte[len];

			int offset = 0;
			while (offset < len) {
			    offset += in.read(imageBytes, offset, len - offset);
			}

			MatOfByte mb = new MatOfByte();
			mb.fromArray(imageBytes);
			Mat image = Imgcodecs.imdecode(mb.clone(), Imgcodecs.CV_LOAD_IMAGE_ANYCOLOR);

			Image javafxImage = OpenCVutils.mat2Image(image);
			ui.updateVideo(javafxImage);
			break;

		    case POS:
			Pose pose = new Pose();
			pose.loadObject(in);
			ui.updateMap(pose);
			break;
		}
	    } catch (SocketException e) {
		try {
		    exit();
		} catch (Exception ex) {
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * Closes the socket given to a DataIn-instance and ends its life.
     * @throws IOException if fails to close the socket
     */
    public void exit() throws IOException {
	this.done = true;
	in.close();
    }
}
