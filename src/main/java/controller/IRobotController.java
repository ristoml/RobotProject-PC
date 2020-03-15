package controller;

import java.util.List;

import lejos.robotics.pathfinding.Path;
import model.RobotConfig;

/**
 * 
 */
public interface IRobotController {
    /**
     * Creates a socket and connects it to specified host and port.
     * @param host
     * @param port
     * @return true if connected successfully, false otherwise.
     */
    boolean connect(String host, int port);

    /**
     * Creates a socket and connects it to specified host and port, and waits until specified timeout.
     * @param host
     * @param port
     * @param timeout
     * @return true if connected successfully, false otherwise.
     */
    boolean connect(String host, int port, int timeout);
    /**
     * Disconnects the socket initiliazed with connect-method.
     */
    void disconnect();
    /**
     * Check if a socket connection is open.
     * @return true if socket connection is open, false otherwise.
     */
    boolean isConnected();
    /**
     * Move Lejos Mindstorms -robot to specified direction.
     * See ${Constants} for constants to use as a parameter with this method.
     * @param direction See {@link utils.Constants} for constants to use with this method.
     */
    void moveRobot(int direction);
    /**
     * Stops the Lejos Mindstorms -robot.
     */
    void stopRobot();
    /**
     * Sends waypoints to Lejos Mindstorms -robot.
     * @param wps
     */
    void sendWaypoints(Path wps);
    /**
     * Get a list of {@link model.RobotConfig}s from {@link model.IRobotConfigDAO}.
     * @return
     */
    List<RobotConfig> getConfigs();
    /**
     * Save a {@link RobotConfig} to a database.
     * @param config
     */
    void saveConfig(RobotConfig config);
    /**
     * Sends a {@link RobotConfig} to a Lejos Mindstorms -robot.
     * @param config
     */
    void sendConfig(RobotConfig config);
}
