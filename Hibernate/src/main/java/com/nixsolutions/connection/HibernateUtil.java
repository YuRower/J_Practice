package com.nixsolutions.connection;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.nixsolutions.connection.util.DBResourceManager;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	static Logger LOGGER = Logger.getLogger(HibernateUtil.class);

	static {

		try {
			sessionFactory = new Configuration().configure().addProperties(DBResourceManager.getProperties())
					.buildSessionFactory();
		} catch (Throwable e) {
			LOGGER.error("Initial SessionFactory creation failed.", e);
			throw new ExceptionInInitializerError(e);
		}

	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void closeSessionFactory() {
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}
}