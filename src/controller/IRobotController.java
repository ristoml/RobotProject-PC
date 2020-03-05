package controller;

import java.io.IOException;
import java.util.List;

import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

public interface IRobotController {
    boolean connect(String host, int port);
    boolean connect(String host, int port, int timeout);
    void disconnect();
    boolean isConnected();
    void moveRobot(int direction);
    void stopRobot();
    void sendWaypoints(Path wps);
}
