package ua.mamamusic.accountancy;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	
	static{
		try{
			Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration
                .getProperties());
			sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Session beginTransaction(){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		return session;
	}
	
	public static void commitTransaction(){
		HibernateUtil.getSession().getTransaction().commit();
	}
	
	public static void rollbackTransaction(){
		HibernateUtil.getSession().getTransaction().rollback();
	}
	
	public static Session getSession(){
		Session session = sessionFactory.getCurrentSession();
		return session;
	}
	
	public static void closeSession(){
		HibernateUtil.getSession().close();
	}

	public static void shutdown(){
		getSessionFactory().close();
	}
}
