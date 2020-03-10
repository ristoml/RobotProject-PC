package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class RobotConfigDAOHibernate implements IRobotConfigDAO {
    
    private SessionFactory senssions;

    public RobotConfigDAOHibernate() throws Exception {
	senssions = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public boolean createConfig(RobotConfig conf) {
	boolean ok = false;

        Transaction transaction = null;
        try (Session session = senssions.openSession()) {
            transaction = session.beginTransaction();
            session.save(conf);
            transaction.commit();
            ok = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
	return ok;
    }

    @Override
    public RobotConfig readConfig(String name) {
	return null;
    }

    @Override
    public List<RobotConfig> readConfigs() {
        List<RobotConfig> configs = null;
        try (Session session = senssions.openSession()) {
            @SuppressWarnings("unchecked")
            List<RobotConfig> results =
		session.createQuery("from RobotConfig").getResultList();

	    configs = new ArrayList<>();
	    for (RobotConfig c : results) configs.add(c);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return configs;
    }

    @Override
    public boolean updateConfig(RobotConfig conf) {
	return false;
    }

    @Override
    public boolean deleteConfig(String name) {
	return false;
    }

    @Override
    public void finalize() {
	try {
	    senssions.close();
	} catch (Exception ex) {
	    System.out.println("failed to close SessionFactory: "+ex.getMessage());
	}
    }
}
