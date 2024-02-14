package lk.ijse.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;
public class SessionFactoryConfig {
    private static SessionFactoryConfig sessionFactoryConfig;
    private SessionFactory sessionFactory;

    private SessionFactoryConfig(){
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties"));
            Configuration configuration = new Configuration().setProperties(properties);
            sessionFactory= configuration.buildSessionFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SessionFactoryConfig getInstance() {
        return sessionFactoryConfig == null ? sessionFactoryConfig = new SessionFactoryConfig() : sessionFactoryConfig;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }
}
