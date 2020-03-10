package controller;

import java.util.List;

import lejos.robotics.pathfinding.Path;
import model.RobotConfig;

public interface IRobotController {
    boolean connect(String host, int port);
    boolean connect(String host, int port, int timeout);
    void disconnect();
    boolean isConnected();
    void moveRobot(int direction);
    void stopRobot();
    void sendWaypoints(Path wps);
    List<RobotConfig> getConfigs();
    void saveConfig(RobotConfig config);
    void sendConfig(RobotConfig config);
}
