package lk.ijse.config;

import lk.ijse.entity.Customer;
import lk.ijse.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;
public class SessionFactoryConfig {
    /*private static SessionFactoryConfig sessionFactoryConfig;
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
    }*/
    private static SessionFactoryConfig sessionFactoryConfig;

    private final SessionFactory sessionFactory;

    private SessionFactoryConfig(){
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().build();
        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .getMetadataBuilder().build();
        sessionFactory = metadata.buildSessionFactory();
    }

    public static SessionFactoryConfig getInstance(){
        return (sessionFactoryConfig==null)?sessionFactoryConfig=new SessionFactoryConfig():sessionFactoryConfig;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
