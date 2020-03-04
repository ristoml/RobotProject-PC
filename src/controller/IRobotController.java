package controller;

import java.io.IOException;

public interface IRobotController {
    boolean connect(String host, int port);
    boolean connect(String host, int port, int timeout);
    void disconnect();
    boolean isConnected();
    void moveRobot(int direction);
    void stopRobot();
}
