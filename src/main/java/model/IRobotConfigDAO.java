package model;

import java.util.List;

public interface IRobotConfigDAO {
    boolean createConfig(RobotConfig conf);
    RobotConfig readConfig(String name);
    List<RobotConfig> readConfigs();
    boolean updateConfig(RobotConfig conf);
    boolean deleteConfig(String name);
    void finalize();
}
