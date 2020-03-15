package model;

import java.util.List;

/**
 * DataAccessObject for handling database-related tasks related to @{@link RobotConfig}.
 */
public interface IRobotConfigDAO {
    /**
     * Add a configuration to the database.
     * @param conf
     * @return
     */
    boolean createConfig(RobotConfig conf);
    /**
     * Read a configuration from the database, configuration name as the key.
     * @param name
     * @return
     */
    RobotConfig readConfig(String name);
    /**
     * Read all configurations from the database.
     * @return A {@link List} of {@link RobotConfig}s.
     */
    List<RobotConfig> readConfigs();
    /**
     * Update a configuration from the database.
     * @param conf
     * @return
     */
    boolean updateConfig(RobotConfig conf);
    /**
     * Delete a configuration from the database.
     * @param name
     * @return
     */
    boolean deleteConfig(String name);
    /**
     * Destructor.<br>Close the database connection.
     */
    void finalize();
}
